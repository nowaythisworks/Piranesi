package brazil.piranesi.structures;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.bukkit.Bukkit;

public class StructureLoader {
	public static ArrayList<ArrayList<String>> buildData = new ArrayList<ArrayList<String>>();

	public void loadStructureData(File fileName) throws FileNotFoundException
	{
		ArrayList<String> newData = new ArrayList<String>();
		Bukkit.getLogger().info("Loading Structure File for " + fileName.getName());
		Scanner scan = new Scanner(fileName);
		while (scan.hasNextLine())
		{
			String currentLine = scan.nextLine();
			if (currentLine != "")
			{
				newData.add(currentLine);
			}
		}
		buildData.add(newData);
		Bukkit.getLogger().info("Structure " + fileName.getName() + " loaded successfully. Total is " + newData.size() + " lines.");
		scan.close();
	}
}
