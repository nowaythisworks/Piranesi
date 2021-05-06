package brazil.piranesi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.SplittableRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;


/*public class PiranesiChunkGenerator extends ChunkGenerator{
	
	JavaPlugin plugin;
	
	public void setPlugin(JavaPlugin p)
	{
		plugin = p;
	}
	
	@Override
	public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome)
	{
		SplittableRandom indexer = new SplittableRandom();
		
		int plainsHeight;
		int offsetHeight;
		
		Biome demoBiome = Biome.FOREST;
		
		//Chunk Generator
		ChunkData chunk = createChunkData(world);
		
		OpenSimplex2F noise = new OpenSimplex2F(world.getSeed());
		
		for (int X = 0; X < 16; X++)
		{
			for (int Z = 0; Z < 16; Z++)
			{
				plainsHeight = (int) ((noise.noise2(chunkX*16+X, chunkZ*16+Z, 275) * 17) + 40);
				offsetHeight = (int) ((noise.noise2(chunkX*16+X, chunkZ*16+Z, 275) * 15) + 40);
				
				plainsHeight -= plainsHeight - offsetHeight;
				
				for (int i = plainsHeight; i > 1; i--)
				{
					chunk.setBlock(X, i, Z, Material.GRASS_BLOCK);
					biome.setBiome(X, i, Z, demoBiome);
				}
				
				chunk.setBlock(X, 0, Z, Material.BEDROCK);
			}
		}
		
		return chunk;
	}
}*/