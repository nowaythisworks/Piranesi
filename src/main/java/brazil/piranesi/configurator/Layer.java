package brazil.piranesi.configurator;

import brazil.piranesi.noise.FastNoiseLite;
import brazil.piranesi.noise.FastNoiseLite.NoiseType;
import brazil.piranesi.noise.Noise;

public class Layer {
	public Layer(String layerName, Noise layerData) {
		this.layerName = layerName;
		this.layerData = layerData;
	}
	
	private String layerName;
	private Noise layerData;
	
	public Noise getNoiseProfile() {
		return layerData;
	}
	
}
