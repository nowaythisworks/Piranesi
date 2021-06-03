package brazil.piranesi;

public class ChunkCoords {
	public int X;
	public int Z;
	
	ChunkCoords(int cX, int cZ)
	{
		X = cX;
		Z = cZ;
	}
    
	public ChunkCoords left() {
        return new ChunkCoords(X - 1, Z);
    }
   
	public ChunkCoords right() {
        return new ChunkCoords(X + 1, Z);
    }
   
	public ChunkCoords above() {
        return new ChunkCoords(X, Z - 1);
    }
   
	public ChunkCoords below() {
        return new ChunkCoords(X, Z + 1);
    }
   
	public ChunkCoords upperLeft() {
        return new ChunkCoords(X - 1, Z - 1);
    }
   
	public ChunkCoords upperRight() {
        return new ChunkCoords(X + 1, Z - 1);
    }
   
	public ChunkCoords lowerLeft() {
        return new ChunkCoords(X - 1, Z + 1);
    }
   
	public ChunkCoords lowerRight() {
        return new ChunkCoords(X + 1, Z + 1);
    }
    
    public String toString()
    {
    	return "(" + X + ", " + Z + ")";
    }
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + X;
		result = prime * result + Z;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		ChunkCoords other = (ChunkCoords) obj;
		if (X != other.X)
			return false;
		if (Z != other.Z)
			return false;
		return true;
	}
}
