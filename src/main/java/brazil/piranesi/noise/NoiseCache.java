package brazil.piranesi.noise;

import java.util.ArrayList;

/**
 * Stores all active and inactive Noise Generators
 * for easy access from ChunkGenerator and Indexer classes.
 */
public class NoiseCache {

	public static ArrayList<Noise> loadedNoise = new ArrayList<Noise>();

	public static void addNoiseLayer(Noise testingLayer) {
		loadedNoise.add(testingLayer);
	}
	
	public static Noise getNoise(int n)
	{
		return loadedNoise.get(n);
	}
	
}
