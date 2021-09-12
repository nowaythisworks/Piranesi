package brazil.piranesi.noise;

import brazil.piranesi.noise.FastNoiseLite.CellularDistanceFunction;
import brazil.piranesi.noise.FastNoiseLite.CellularReturnType;
import brazil.piranesi.noise.FastNoiseLite.FractalType;
import brazil.piranesi.noise.FastNoiseLite.NoiseType;

/**
 * Allows quick initialization of a new FastNoiseLite unit, using params set by
 * the default chunk generator.
 * 
 * @author brazil
 *
 */
public class Noise {

	FastNoiseLite noise = new FastNoiseLite();
	private NoiseIndex index;

	public Noise(String layerName, NoiseType a, float freq, FractalType f, int octaves, float lacunarity, float fractalGain,
			float fractalWeightedStrength, CellularDistanceFunction c, CellularReturnType cr, float jitter) {
		/* Base Terrain Noise Generator */
		noise.SetNoiseType(a);
		noise.SetFrequency(freq);
		noise.SetFractalType(f);
		noise.SetFractalOctaves(octaves);
		noise.SetFractalLacunarity(lacunarity);
		noise.SetFractalGain(fractalGain);
		noise.SetFractalWeightedStrength(fractalWeightedStrength);
		noise.SetCellularDistanceFunction(c);
		noise.SetCellularReturnType(cr);
		noise.SetCellularJitter(jitter);
		
		index = new NoiseIndex(layerName);
	}

	public NoiseIndex getNoiseIndex() {
		return index;
	}
	
	public FastNoiseLite getNoise()
	{
		return noise;
	}
}
