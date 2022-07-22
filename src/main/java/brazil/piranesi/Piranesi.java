package brazil.piranesi; //install does not clean, run clean op before building

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import brazil.piranesi.Piranesi;
import brazil.piranesi.blockpopulation.PiranesiTreePopulator;
import brazil.piranesi.chunks.PiranesiChunkGenerator;
import brazil.piranesi.commands.DebugCommands;
import brazil.piranesi.configurator.PluginConfiguration;
import brazil.piranesi.configurator.WorldGenConfigReader;
import brazil.piranesi.configurator.WorldGenConfiguration;
import brazil.piranesi.structures.StructureLoader;

public final class Piranesi extends JavaPlugin implements Listener {
	
	private static Piranesi instance;
	
	public PluginConfiguration pluginConfig;
	private WorldGenConfiguration worldGenConfig;

	private StructureLoader sl = new StructureLoader();
	
	public static Piranesi getInstance()
	{
		return instance;
	}

	// for the time being, leave this static.
	// later, make it so that the structure paster objects are each passed into the chunkGenerator class, 
	// or better yet, have a specific class to hold those and pass that along instead.

	public void onEnable() {

		// Load Piranesi PLUGIN CONSTANTS config
		loadConfig();
		pluginConfig = new PluginConfiguration(getConfig().getString("piranesi.generatorConfig"), getConfig().getString("piranesi.chatMessagePrefix"));
		
		// Load Piranesi WORLD GENERATION Config
		new WorldGenConfigReader(this, pluginConfig.GENERATOR_CONFIG);

		/*Noise biomeData = new Noise("Biome Definition Layer", NoiseType.Cellular, 0.0002F, FractalType.None, 5, 2.00F, 0.50F, -0.50F,
				CellularDistanceFunction.Hybrid, CellularReturnType.CellValue, 1.00F);
		biomeData.getNoise().SetDomainWarpAmp(100.00F);
		biomeData.getNoise().SetFractalType(FractalType.DomainWarpProgressive);
		
		NoiseCache.addNoiseLayer(biomeData);
		
		Noise testingLayer = new Noise("Base Terrain Layer", NoiseType.Cellular, 0.0015F, FractalType.FBm, 5, 2.00F, 0.50F,
				-0.50F, CellularDistanceFunction.EuclideanSq, CellularReturnType.Distance2Mul, 1.00F);
		NoiseCache.addNoiseLayer(testingLayer);*/
		
		// Load Structures
		Bukkit.getLogger().info("Loading " + this.getName());
		getServer().getPluginManager().registerEvents(this, this);
		File treeFile = new File(this.getDataFolder(), "Oak Tree.txt");
		Bukkit.getLogger().info(treeFile.toString());

		try {
			sl.loadStructureData(treeFile);
		} catch (FileNotFoundException e) {
			Bukkit.getLogger().info("Failed to load structure for " + treeFile.getName());
		}
        getCommand("pdebug").setExecutor(new DebugCommands(this));
	}
	
	public StructureLoader getStructurePaster()
	{
		return sl;
	}
	
	private void loadConfig()
	{
		getConfig().options().header("Set the value below to the generator settings you wish to use.");
		getConfig().addDefault("piranesi.generatorConfig", "default.gen");
		getConfig().addDefault("piranesi.chatMessagePrefix", "&4&l[Piranesi]&r ");
        getConfig().options().copyDefaults(true);

        saveConfig();
        reloadConfig();
	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return new PiranesiChunkGenerator();
	}

	public WorldGenConfiguration getUserConfiguration() {
		return worldGenConfig;
	}
}
