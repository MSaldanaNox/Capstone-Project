package currentfacerecognition;

public class Mouth {

	int mouthSpace;
	int lipThickness;

	public Mouth(int space, int thickness)
	{
		mouthSpace = space;
		lipThickness = thickness;
	}

	public int getMouthSpace()
	{
		return this.mouthSpace;
	}
	
	public int getLipThickness()
	{
		return this.lipThickness;
	}
}