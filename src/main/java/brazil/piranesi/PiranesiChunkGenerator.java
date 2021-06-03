package brazil.piranesi;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;


public class PiranesiChunkGenerator extends ChunkGenerator {
	
    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return Arrays.asList((BlockPopulator)new PiranesiTreePopulator());
    }
    
	@Override
	public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome)
	{
		
		Random r = new Random();

		/**
		 * # BIOMEDATA
		 * BiomeData is a noise profile that has information about
		 * WHERE biomes are. It is a large map of Biomes.
		 * It is always the 0th index of the NoiseCache.
		 * 
		 * The BIOMEDATA is consistently within range -1 to 1,
		 * excluding a frequency greater than 1.
		 * 
		 * The default frequency is 0.002.
		 */
		FastNoiseLite biomeData = NoiseCache.loadedNoise.get(0).getNoise();
		
		/**
		 * # NOISE
		 * The following will collect all other instances
		 * of NoiseCache with a total of NoiseCache.size();
		 */
		FastNoiseLite noise = NoiseCache.loadedNoise.get(1).getNoise();
		
		/* Init Defaults */
		
		int currentHeight = 70;
		
		//Chunk Generator
		ChunkData chunk = createChunkData(world);
		
		Bisected topGrassData = (Bisected) Material.TALL_GRASS.createBlockData();
		topGrassData.setHalf(Half.TOP);
		
		for (int X = 0; X < 16; X++)
		{
			for (int Z = 0; Z < 16; Z++)
			{
				boolean isWater = false;
				int sealevel = 85;
				
				float biomeValue = biomeData.GetNoise(chunkX*16+X, chunkZ*16+Z);
				
				float pointValue = noise.GetNoise(chunkX*16+X, chunkZ*16+Z);
				Bukkit.getLogger().info(pointValue + "");

				currentHeight = (int) (Math.abs(pointValue) * 175) - 175;
				
				Material currentMaterial = Material.BLUE_ICE;
				//1 - 1.6
				if (pointValue < -0.6)
				{
					currentMaterial = Material.WATER;
				}
				else if (pointValue < 0)
				{
					currentMaterial = Material.GRAVEL;
					isWater = true;
				}
				else if (pointValue < 0.4)
				{
					currentMaterial = Material.SAND;
					isWater = true;
				}
				else
				{
					currentMaterial = Material.GRASS_BLOCK;
					Double d = r.nextDouble();
					if (d < 0.15)
					{
						chunk.setBlock(X, currentHeight, Z, Material.PINK_TULIP);
					}
					else if (d < 0.25)
					{
						chunk.setBlock(X, currentHeight, Z, Material.FERN);
					}
					else if (d < 0.35)
					{
						
						chunk.setBlock(X, currentHeight, Z, Material.TALL_GRASS);
						chunk.setBlock(X, currentHeight+1, Z, topGrassData);
					}
					else if (d < 0.6)
					{
						chunk.setBlock(X, currentHeight, Z, Material.GRASS);
					}
				}
				
				
				for (int i = 1; i < currentHeight; i++)
				{
					chunk.setBlock(X, i, Z, currentMaterial);
					if (isWater) chunk.setBlock(X, sealevel, Z, Material.WATER);
				}
				
				chunk.setBlock(X, 0, Z, Material.BEDROCK);
			}
		}
		
		return chunk;
	}

	public static int getNoiseTopPosition(int posX, int posZ, World world)
	{
		FastNoiseLite noise = NoiseCache.loadedNoise.get(0).getNoise();
		int height = (int) (Math.abs(noise.GetNoise(posX, posZ) * 175) - 175);
		if (height <= 86)
		{
			return 300;
		}
		return height;
	}
}