package brazil.piranesi.blockpopulation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.generator.BlockPopulator;

import brazil.piranesi.chunks.ChunkCoords;
import brazil.piranesi.chunks.PiranesiChunkGenerator;
import brazil.piranesi.structures.StructureLoader;

public class PiranesiTreePopulator extends BlockPopulator {
	
	public ArrayList<String> parsedBlockData = new ArrayList<String>();
	
	private int heightModifier = 0;
	
	private static Set<ChunkCoords> chunks = ConcurrentHashMap.newKeySet();
	private static Set<ChunkCoords> unpopulatedChunks = ConcurrentHashMap.newKeySet();
	
	public void grabStructureData()
	{
		final int r = new Random().nextInt(1000);
		if (r <= 1) {
			parsedBlockData = StructureLoader.buildData.get(2);
			heightModifier = 0;
		}
		else if (r <= 300) {
			parsedBlockData = StructureLoader.buildData.get(1);
			heightModifier = 0;
		}
		else if (r <= 1000)
		{
			parsedBlockData = StructureLoader.buildData.get(0);
			heightModifier = 0;
		}
	}
	
	@Override
	public void populate(final World world, final Random random, final Chunk source)
	{
		/**
		 * thanks to the works of:
		 * mbmorris, MiniDigger, and MD_5,
		 * for proposing solutions to this problem aging almost 10 years.
		 * 
		 * it is not expected to be very version-compatible, but it follows
		 * vanilla population rules and should hopefully not infinitely recurse
		 * chunks.
		 */
		
		final int posX = source.getX();
		final int posZ = source.getZ();
		final ChunkCoords c = new ChunkCoords(posX, posZ);
		
		if (!chunks.contains(c))
		{
			chunks.add(c);
			unpopulatedChunks.add(c);
		}

		/*Bukkit.getLogger().info("Left: " + chunks.contains(c.left()));
		Bukkit.getLogger().info("Right: " + chunks.contains(c.right()));
		Bukkit.getLogger().info("UpperLeft: " + chunks.contains(c.upperLeft()));
		Bukkit.getLogger().info("UpperRight: " + chunks.contains(c.upperRight()));
		Bukkit.getLogger().info("LowerLeft: " + chunks.contains(c.lowerLeft()));
		Bukkit.getLogger().info("LowerRight: " + chunks.contains(c.lowerRight()));
		Bukkit.getLogger().info("Above: " + chunks.contains(c.above()));
		Bukkit.getLogger().info("Below: " + chunks.contains(c.below()));*/
		
		final Iterator<ChunkCoords> iterator = unpopulatedChunks.iterator();
		
		while (iterator.hasNext())
		{
			final ChunkCoords chunk = iterator.next();
			
			//Bukkit.getLogger().info(chunk.toString());
			if (ChunkPopulationReady(chunk))
			{
            	//Bukkit.getLogger().info("Structure Placing Available");
				iterator.remove();
				placeStructures(world,random,world.getChunkAt(chunk.X, chunk.Z));
			}
		}
	}

	private boolean ChunkPopulationReady(final ChunkCoords c) {
		if	(chunks.contains(c.left()) && chunks.contains(c.left().left()) && chunks.contains(c.left().left().left()) && 
			chunks.contains(c.right()) && chunks.contains(c.right().right()) && chunks.contains(c.right().right().right()) && 
			chunks.contains(c.upperLeft()) && chunks.contains(c.upperLeft().upperLeft()) && chunks.contains(c.upperLeft().upperLeft().upperLeft()) && 
			chunks.contains(c.upperRight()) && chunks.contains(c.upperRight().upperRight()) && chunks.contains(c.upperRight().upperRight().upperRight()) && 
			chunks.contains(c.lowerLeft()) && chunks.contains(c.lowerLeft().lowerLeft()) && chunks.contains(c.lowerLeft().lowerLeft().lowerLeft()) && 
			chunks.contains(c.lowerRight()) && chunks.contains(c.lowerRight().lowerRight()) && chunks.contains(c.lowerRight().lowerRight().lowerRight()) && 
			chunks.contains(c.above()) && chunks.contains(c.above().above()) && chunks.contains(c.above().above()) && chunks.contains(c.above().above().above()) && 
			chunks.contains(c.below()) && chunks.contains(c.below().below()) && chunks.contains(c.below().below().below()))
		{
			return true;
		}
		return false;
	}

	private void placeStructures(final World world, final Random r, final Chunk source) {
		int posX = source.getX() * 16;
		int posZ = source.getZ() * 16;
		
		posX += new Random().nextInt(8)-4;
		posZ += new Random().nextInt(8)-4;
		
		final int height = PiranesiChunkGenerator.getNoiseTopPosition(posX, posZ, world);
		
		if (height > 255)
		{
			Bukkit.getLogger().warning("DEBUG: Height Too High");
			return;
		}
		
		final Location heightLocation1 = new Location(world, posX, height, posZ);
		
		grabStructureData();
		
		if (parsedBlockData == null)
		{
			Bukkit.getLogger().warning("Failed to load parsedBlockData");
			return;
		}
		
		for (int i = 0; i < parsedBlockData.size(); i++)
		{
			final String currentLine = parsedBlockData.get(i);
			if (currentLine != "")
			{
				final String[] lineContents = currentLine.split("\\|");

				final String currentBlockStr = lineContents[0];
				final int xpos = Integer.parseInt(lineContents[1]);
				final int ypos = Integer.parseInt(lineContents[2]) + heightModifier;
				final int zpos = Integer.parseInt(lineContents[3]);
				final Material currentBlock = Material.matchMaterial(currentBlockStr);
				if (currentBlock != null)
				{
					final Location blockLoc1 = new Location(world, xpos, ypos, zpos).add(heightLocation1);
					final BlockData data = returnBlockData(lineContents[4]);
					final Block b1 = world.getBlockAt(blockLoc1);
					b1.setBlockData(data);
				}
			}
		}
	}

	public BlockData returnBlockData(final String str)
	{
		final String blockDataAsString = str.substring(25, str.length() - 1);

		return Bukkit.createBlockData(blockDataAsString);
	}

}
