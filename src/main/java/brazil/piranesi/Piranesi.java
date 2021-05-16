package brazil.piranesi; //install does not clean, run clean op before building

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import brazil.piranesi.FastNoiseLite.CellularDistanceFunction;
import brazil.piranesi.FastNoiseLite.CellularReturnType;
import brazil.piranesi.FastNoiseLite.FractalType;
import brazil.piranesi.FastNoiseLite.NoiseType;

public final class Piranesi extends JavaPlugin implements Listener {

	public static Piranesi instance;
	public static JavaPlugin plugin;
	public static File dataFolder;
	public File treeFile;
	public File shortTreeFile;
	public File cabinFile;

	// Debug
	public static int taskCount;

	public StructureLoader sl = new StructureLoader();

	public static Piranesi getInstance() {
		return instance;
	}

	public static File getPluginFolder() {
		return dataFolder;
	}

	public void onEnable() {
		Noise testingLayer = new Noise("Base Terrain Layer", NoiseType.Cellular, 0.0015F, FractalType.FBm, 5, 2.00F, 0.50F,
				-0.50F, CellularDistanceFunction.EuclideanSq, CellularReturnType.Distance2Mul, 1.00F);
		NoiseCache.addNoiseLayer(testingLayer);

		Noise biomeData = new Noise("Biome Definition Layer", NoiseType.Cellular, 0.0002F, FractalType.None, 5, 2.00F, 0.50F, -0.50F,
				CellularDistanceFunction.Hybrid, CellularReturnType.CellValue, 1.00F);
		biomeData.noise.SetDomainWarpAmp(100.00F);
		biomeData.noise.SetFractalType(FractalType.DomainWarpProgressive);
		
		NoiseCache.addNoiseLayer(testingLayer);

		dataFolder = getDataFolder();
		plugin = this;
		Bukkit.getLogger().info("Loading " + this.getName());
		getServer().getPluginManager().registerEvents(this, this);
		cabinFile = new File(Piranesi.getPluginFolder(), "yalikecock.txt");
		treeFile = new File(Piranesi.getPluginFolder(), "forestTreeOne.txt");
		shortTreeFile = new File(Piranesi.getPluginFolder(), "forestSmallTree1.txt");
		Bukkit.getLogger().info(treeFile.toString());

		try {
			sl.loadStructureData(treeFile);
			sl.loadStructureData(shortTreeFile);
			sl.loadStructureData(cabinFile);
		} catch (FileNotFoundException e) {
			Bukkit.getLogger().info("Failed to load structure for " + treeFile.getName());
		}
	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return new PiranesiChunkGenerator();
	}

	public static void broadcast(String msg) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendMessage(msg);
		}
	}
}
