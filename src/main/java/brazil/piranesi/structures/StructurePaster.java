package brazil.piranesi.structures;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;

public class StructurePaster {

	public static ArrayList<String> structureData = new ArrayList<String>();
	
	public void loadStructureData(File fileName) throws FileNotFoundException
	{
		if (structureData.size() == 0)
		{
			Bukkit.getLogger().info("Loading Structure File for " + fileName.getName());
			Scanner scan = new Scanner(fileName);
			while (scan.hasNextLine())
			{
				String currentLine = scan.nextLine();
				if (currentLine != "")
				{
					structureData.add(currentLine);
				}
			}
			Bukkit.getLogger().info("Structure " + fileName.getName() + " loaded successfully. Total is " + structureData.size() + " lines.");
			scan.close();
		}
	}

	public boolean pasteOp(Location playerLoc)
	{
		if (structureData != null) {
			BlockData data;
			for (int i = 0; i < structureData.size(); i++)
			{
				String currentLine = structureData.get(i);
				if (currentLine != "")
				{
					String[] lineContents = currentLine.split("\\|");

					String currentBlockStr = lineContents[0];
					int xpos = Integer.parseInt(lineContents[1]);
					int ypos = Integer.parseInt(lineContents[2]);
					int zpos = Integer.parseInt(lineContents[3]);
					Location blockLoc = new Location(playerLoc.getWorld(), xpos, ypos, zpos).add(playerLoc);
					Material currentBlock = getCurrentMaterial(currentBlockStr);
					if (currentBlock != null)
					{

						data = returnBlockData(lineContents[4]);
						setBlockAtLocation(blockLoc, currentBlock, data);
					}
				}
			}
		}
		else
		{
			Bukkit.getLogger().info("Loaded Structure Data is Null, cancelling paste");
			return false;
		}
		return true;
	}

	public void setBlockAtLocation(Location blockLoc, Material currentBlock, BlockData data) {
		blockLoc.getBlock().setType(currentBlock);
		blockLoc.getBlock().setBlockData(data, false);
	}

	public BlockData returnBlockData(String str)
	{
		//if createBlockData IS NOT CREATING BLOCK DATA properly,
		//perhaps the string is poorly formatted?

		String blockDataAsString = str.substring(25, str.length() - 1);

		return Bukkit.createBlockData(blockDataAsString);
	}

	Material getCurrentMaterial(String mat)
	{
		//~~~~~~~~~~~<=: I'm a Snake
		return Material.matchMaterial(mat);
	}

	public ArrayList<String> getStructureData() {
		return structureData;
	}
}
