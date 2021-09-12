package brazil.piranesi; //install does not clean, run clean op before building

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import brazil.piranesi.chunks.PiranesiChunkGenerator;
import brazil.piranesi.commands.DebugCommands;
import brazil.piranesi.noise.Noise;
import brazil.piranesi.noise.NoiseCache;
import brazil.piranesi.noise.FastNoiseLite.CellularDistanceFunction;
import brazil.piranesi.noise.FastNoiseLite.CellularReturnType;
import brazil.piranesi.noise.FastNoiseLite.FractalType;
import brazil.piranesi.noise.FastNoiseLite.NoiseType;
import brazil.piranesi.structures.StructureLoader;

public final class Piranesi extends JavaPlugin implements Listener {

	
	private static File dataFolder;
	private File treeFile;
	private File shortTreeFile;
	private File cabinFile;

	private StructureLoader sl = new StructureLoader();

	// for the time being, leave this static.
	// later, make it so that the structure paster objects are each passed into the chunkGenerator class, 
	// or better yet, have a specific class to hold those and pass that along instead.
	public static File getPluginFolder() {
		return dataFolder;
	}

	public void onEnable() {

		Noise biomeData = new Noise("Biome Definition Layer", NoiseType.Cellular, 0.0002F, FractalType.None, 5, 2.00F, 0.50F, -0.50F,
				CellularDistanceFunction.Hybrid, CellularReturnType.CellValue, 1.00F);
		biomeData.getNoise().SetDomainWarpAmp(100.00F);
		biomeData.getNoise().SetFractalType(FractalType.DomainWarpProgressive);
		
		NoiseCache.addNoiseLayer(biomeData);
		
		Noise testingLayer = new Noise("Base Terrain Layer", NoiseType.Cellular, 0.0015F, FractalType.FBm, 5, 2.00F, 0.50F,
				-0.50F, CellularDistanceFunction.EuclideanSq, CellularReturnType.Distance2Mul, 1.00F);
		NoiseCache.addNoiseLayer(testingLayer);

		dataFolder = getDataFolder();
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
        getCommand("pdebug").setExecutor(new DebugCommands(this));
	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return new PiranesiChunkGenerator();
	}
}
