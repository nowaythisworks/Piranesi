package brazil.piranesi.configurator;

/**
 * @author brazil
 * Stores the PLUGIN configuration (gen number, chat prefix, etc...)
 * @see WorldGenConfiguration stores the WORLD GEN configuration (noise tuning, layers, etc...)
 */
public class PluginConfiguration {
	public String GENERATOR_CONFIG;
	public String CHAT_MESSAGE_PREFIX;
	
	public PluginConfiguration(String genConfig, String chatPrefix)
	{
		GENERATOR_CONFIG = genConfig;
		CHAT_MESSAGE_PREFIX = chatPrefix;
	}
}
