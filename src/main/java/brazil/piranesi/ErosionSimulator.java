package brazil.piranesi;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.generator.ChunkGenerator.ChunkData;
import org.bukkit.plugin.java.JavaPlugin;

public class ErosionSimulator {
	
	ChunkData simChunk;
	
	World world;

	Location rdl;
	Location rdlRT;
	
	JavaPlugin plugin;
	
	Block raindrop;
	
	int iterationCount = 0;
	int dropHeight = 0;

	//Passes

	List<Location> pass = new ArrayList<Location>();
	List<Location> passRT = new ArrayList<Location>();
	
	List<Location> erodedBlocks = new ArrayList<Location>();
	
	public ErosionSimulator(ChunkData b, World w, JavaPlugin p, int height)
	{
		dropHeight = height;
		plugin = p;
		world = w;
		simChunk = b;
	}
	
	public ErosionSimulator(Block b, World w, JavaPlugin p)
	{
		raindrop = b;
		world = w;
		plugin = p;
	}
	
	public void GenerateDropPasses_Realtime(boolean beginCycle) {
		
		SplittableRandom indexer = new SplittableRandom();
		
		passRT.clear();
		
		/* 3 x 3 Mappings
			+----------+----------+-----------+
			|          |          |           |
			1 -1, 0, 1 2 -1, 0, 0 3 -1 ,0, -1 |
			|          |          |           |
			+----------+----------+-----------+
			|          |          |           |
			4 0, 0, 1  5 0, 0, 0  6  0, 0, -1 |
			|          |          |           |
			+----------+----------+-----------+
			|          |          |           |
			7 1, 0, 1  8 1, 0, 0  9  1, 0, -1 |
			|          |          |           |
			+----------+----------+-----------+
		 */
		
		rdlRT = raindrop.getLocation();
		
		int X = (int) rdlRT.getX();
		int Z = (int) rdlRT.getZ();
		int Y = (int) rdlRT.getY() - 1;
		
		if (Y <= 0)
		{
			/* TODO Break Iteration */
			Y = 1;
		}

		passRT.add(new Location(world, X - 1, Y, Z + 1));
		passRT.add(new Location(world, X - 1, Y, Z));
		passRT.add(new Location(world, X - 1, Y, Z - 1));

		passRT.add(new Location(world, X, Y, Z + 1));
		passRT.add(new Location(world, X, Y, Z));
		passRT.add(new Location(world, X, Y, Z - 1));

		passRT.add(new Location(world, X + 1, Y, Z + 1));
		passRT.add(new Location(world, X + 1, Y, Z));
		passRT.add(new Location(world, X + 1, Y, Z - 1));
		
		if (beginCycle == true)
		{
			beginIterationCycle();
		}
	}
	
	public List<Location> GenerateDropPasses(boolean beginCycle) {
		
		SplittableRandom indexer = new SplittableRandom();
		
		pass.clear();
		
		/* 3 x 3 Mappings
			+----------+----------+-----------+
			|          |          |           |
			1 -1, 0, 1 2 -1, 0, 0 3 -1 ,0, -1 |
			|          |          |           |
			+----------+----------+-----------+
			|          |          |           |
			4 0, 0, 1  5 0, 0, 0  6  0, 0, -1 |
			|          |          |           |
			+----------+----------+-----------+
			|          |          |           |
			7 1, 0, 1  8 1, 0, 0  9  1, 0, -1 |
			|          |          |           |
			+----------+----------+-----------+
		 */
		
		rdl = new Location(world, indexer.nextInt(0, 16), dropHeight, indexer.nextInt(0,16));
		
		int X = (int) rdl.getX();
		int Z = (int) rdl.getZ();
		int Y = (int) rdl.getY() - 1;
		
		if (Y <= 0)
		{
			/* TODO Break Iteration */
			Y = 1;
		}

		pass.add(new Location(world, X - 1, Y, Z + 1));
		pass.add(new Location(world, X - 1, Y, Z));
		pass.add(new Location(world, X - 1, Y, Z - 1));

		pass.add(new Location(world, X, Y, Z + 1));
		pass.add(new Location(world, X, Y, Z));
		pass.add(new Location(world, X, Y, Z - 1));

		pass.add(new Location(world, X + 1, Y, Z + 1));
		pass.add(new Location(world, X + 1, Y, Z));
		pass.add(new Location(world, X + 1, Y, Z - 1));
		
		if (beginCycle == true)
		{
			return simulateDripEffect();
		}
		return pass;
	}

	private List<Location> simulateDripEffect() {
		erodedBlocks.clear();
		for (int i = 0; i < 30; i++)
		{
			pass.clear();
			
			Location nextIterationPosition = null;
			
			GenerateDropPasses(false);
			
			for (int k = 0; k < pass.size(); k++)
			{
				Location cur = pass.get(k);
				BlockData b = simChunk.getBlockData(cur.getBlockX(), cur.getBlockY(), cur.getBlockZ());
				
				if (!b.getMaterial().equals(Material.AIR))
				{
					nextIterationPosition = cur;
					erodedBlocks.add(cur);
				}
			}
			
			if (nextIterationPosition != null)
			{
				for (Location l : erodedBlocks)
				{
					simChunk.setBlock(l.getBlockX(), l.getBlockY(), l.getBlockZ(), Material.AIR);
					simChunk.setBlock(l.getBlockX(), l.getBlockY()-1, l.getBlockZ(), Material.AIR);
					simChunk.setBlock(l.getBlockX(), l.getBlockY()-2, l.getBlockZ(), Material.STONE);
				}
			}
		}
		return erodedBlocks;
	}

	public ChunkData getChunkData() {
		return simChunk;
	}

	public void beginIterationCycle()
	{
		for (int i = 0; i < 30; i++)
		{
			Bukkit.getLogger().warning("1");
			Location nextIterationPosition = null;
			Bukkit.getLogger().warning("2");
			
			for (int k = 0; k < passRT.size(); k++)
			{
				Location tr = passRT.get(k);
				Location tl = new Location(world, tr.getBlockX(), tr.getBlockY(), tr.getBlockZ());
				Block b = passRT.get(k).getBlock();
				Block t = passRT.get(k).getBlock();
				Bukkit.getLogger().warning(b.getType().toString());
				if (!b.getType().equals(Material.AIR) && tl.getBlock().getType().equals(Material.AIR))
				{
					nextIterationPosition = b.getLocation();
				}
			}
			
			if (nextIterationPosition != null)
			{
				Bukkit.getLogger().warning("6");
				world.getBlockAt(nextIterationPosition).setType(Material.LAPIS_BLOCK);
				raindrop = world.getBlockAt(nextIterationPosition);
				Bukkit.getLogger().warning("7");
			}

			GenerateDropPasses_Realtime(false);
		}
	}
}
