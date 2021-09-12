package brazil.piranesi.biome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;
import org.bukkit.generator.ChunkGenerator.ChunkData;
import org.bukkit.generator.WorldInfo;

/**
 * @author brazil
 * 
 * This class overrides the vanilla Biome Provider, the system that defines
 * biomes for any world location.
 * 
 * The Biome Provider will read the 0th index of the noise cache, which is the biome layer.
 * It will use user-defined custom biomes for each index.
 *
 */
public class PiranesiBiomeProvider extends BiomeProvider {

	@Override
	public Biome getBiome(WorldInfo worldInfo, int x, int y, int z)
	{
		return Biome.FOREST;
	}

	@Override
	public List<Biome> getBiomes(WorldInfo worldInfo) {
		List<Biome> userSuppliedBiomes = new ArrayList<Biome>();
		userSuppliedBiomes.add(Biome.FOREST);
		return userSuppliedBiomes;
	}
}
