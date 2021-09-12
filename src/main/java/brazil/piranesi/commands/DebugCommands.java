package brazil.piranesi.commands;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import brazil.piranesi.Piranesi;
import brazil.piranesi.noise.NoiseCache;
import brazil.piranesi.noise.NoiseConstraint;

public class DebugCommands implements CommandExecutor {
	private final Piranesi plugin;

	public DebugCommands(Piranesi piranesi) {
		plugin = piranesi;
	}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!command.getName().equalsIgnoreCase("pdebug")) return false;

        if(args.length == 0) {
            sender.sendMessage(ChatColor.AQUA + "Piranesi Debug\n"
            		+ "/pdebug chunk\n"
            		+ "/pdebug data <data>");
            return true;
        }

        if (args[0].equalsIgnoreCase("chunk")) {
        	Player p = (Player) sender;
        	Chunk chunk = p.getLocation().getChunk();
        	
        	int chunkX = chunk.getX();
        	int chunkZ = chunk.getZ();
        	
        	NoiseConstraint debug = new NoiseConstraint(NoiseCache.getNoise(1).getNoise(), chunkX, chunkZ);
        	sender.sendMessage(ChatColor.AQUA + "Chunk Debug Stats"
        			+ "\nMin Point: " + debug.getMinimum()
        			+ "\nMax Point: " + debug.getMaximum()
        			+ "\nChunk Coords: (" + chunkX + ", " + chunkZ + ")\n");
        	
        }
		return false;
    }
}
