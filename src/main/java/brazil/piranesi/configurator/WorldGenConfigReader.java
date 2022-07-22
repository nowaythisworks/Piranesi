package brazil.piranesi.configurator;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import brazil.piranesi.Piranesi;
import brazil.piranesi.alerts.Alert;
import brazil.piranesi.noise.FastNoiseLite.CellularDistanceFunction;
import brazil.piranesi.noise.FastNoiseLite.CellularReturnType;
import brazil.piranesi.noise.FastNoiseLite.FractalType;
import brazil.piranesi.noise.FastNoiseLite.NoiseType;
import brazil.piranesi.noise.Noise;

public class WorldGenConfigReader {
	private Piranesi instance;
	private String configName;
	
	private File configFile;
	
	public WorldGenConfigReader(Piranesi p, String fileName)
	{
		instance = p;
		configName = fileName;
		loadUserConfiguration();
	}

	public void loadUserConfiguration() {
		configFile = new File(instance.getDataFolder(), configName);

		String jsonContent = getConfigAsString();
		
		JsonObject configAsJSON = new JsonParser().parse(jsonContent).getAsJsonObject();
		
		// iterate for each layer
		// 0 = biome def layer
		// 1 = world shape layer
		int layerCount = configAsJSON.get("layers").getAsJsonObject().size();
		
		for (int layerIndex = 0; layerIndex < layerCount; layerIndex++)
		{
			JsonObject layerData = configAsJSON.get("layers").getAsJsonObject().get(String.valueOf(layerIndex)).getAsJsonObject();

			String layerName = JsonFormat(layerData.get("LayerName").toString());
			NoiseType layerNoiseType = NoiseType.valueOf(JsonFormat(layerData.get("NoiseType").toString()));
			float layerFrequency = layerData.get("Frequency").getAsFloat();
			FractalType layerFractalType = FractalType.valueOf(JsonFormat(layerData.get("FractalType").toString()));
			int layerOctaves = layerData.get("Octaves").getAsInt();
			float layerLacunarity = layerData.get("Lacunarity").getAsFloat();
			float layerFractalGain = layerData.get("FractalGain").getAsFloat();
			float layerFractalWeightedStrength = layerData.get("FractalWeightedStrength").getAsFloat();
			CellularDistanceFunction layerCellularDistanceFunction = CellularDistanceFunction.valueOf(JsonFormat(layerData.get("CellularDistanceFunction").toString()));
			CellularReturnType layerCellularReturnType = CellularReturnType.valueOf(JsonFormat(layerData.get("CellularReturnType").toString()));
			float layerJitter = layerData.get("Jitter").getAsFloat();
			
			Noise shape = new Noise(layerName, layerNoiseType, layerFrequency, layerFractalType, layerOctaves, layerLacunarity, layerFractalGain, layerFractalWeightedStrength, layerCellularDistanceFunction, layerCellularReturnType, layerJitter);
			
			Layer layer = new Layer(layerName, shape);
			
			WorldGenConfiguration.layerData.add(layer);
		}
		
	}

	private String JsonFormat(String str) {
		while (str.contains("\""))
		{
			str = str.replace("\"", "");
		}
		return str;
	}

	private String getConfigAsString() {
		try {
			return Files.readString(configFile.toPath(), StandardCharsets.US_ASCII);
		} catch (IOException e) {
			new Alert("Failed to access world gen config with name \"" + configFile.getName() + "\". See console for details.", instance).broadcast();
			e.printStackTrace();
		}
		return configName;
	}
}
