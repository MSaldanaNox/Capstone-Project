package currentfacerecognition;

//image
//720x800
//eye
//43x123
//nose
//165x203
//nostril(19 from left, 162 from top)
//50x21
//mouth
//255x116
//forehead
//475x221
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