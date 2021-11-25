package brazil.piranesi.alerts;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import brazil.piranesi.Piranesi;

public class Alert {
	private Piranesi instance;
	private String alertMessage;
	
	public Alert(String msg, Piranesi piranesi)
	{
		this.instance = piranesi;
		msg = ChatColor.translateAlternateColorCodes('&', msg);
		alertMessage = instance.pluginConfig.CHAT_MESSAGE_PREFIX + msg;
	}
	
	public void broadcast()
	{
		Bukkit.getLogger().severe(alertMessage);
		for (Player p : Bukkit.getServer().getOnlinePlayers())
		{
			if (p.hasPermission("piranesi.alerts"))
			{
				p.sendMessage(alertMessage);
			}
		}
	}
}
