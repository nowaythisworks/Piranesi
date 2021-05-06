package brazil.piranesi;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class RayCast {

	public Location downToEarth(Chunk chunk) {
		int maxHeight = 255;
		for (int i = maxHeight; i > 0; i--)
		{
			Block c = chunk.getBlock(0, i, 0);
			if (!c.getType().equals(Material.AIR))
			{
				//if the block in the iteration is NOT air
				Bukkit.getLogger().info(chunk.getX() + " " + chunk.getZ());
				return c.getLocation();
			}
		}
		return null;
	}
	
}
