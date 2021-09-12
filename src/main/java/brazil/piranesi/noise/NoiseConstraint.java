package brazil.piranesi.noise;

public class NoiseConstraint {
	private float min;
	private float max;
	
	public NoiseConstraint(FastNoiseLite noise, int chunkX, int chunkZ)
	{
		// determine constraints
		max = Integer.MIN_VALUE;
		min = Float.MAX_VALUE;
		
		for (int X = 0; X < 16; X++)
		{
			for (int Z = 0; Z < 16; Z++)
			{
				float point = noise.GetNoise(chunkX*16+X, chunkZ*16+Z);
				if (point < min)
				{
					min = point;
				}
				if (point > max)
				{
					max = point;
				}
			}
		}
	}
	
	public float getMinimum()
	{
		return min;
	}
	
	public float getMaximum()
	{
		return max;
	}
}
