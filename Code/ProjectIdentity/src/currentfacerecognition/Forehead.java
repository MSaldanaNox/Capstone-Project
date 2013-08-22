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
public class Forehead {

	int foreheadHeight;
	int foreheadWidth;
	
	public Forehead(int h, int w)
	{
		foreheadHeight = h;
		foreheadWidth = w;
	}
	
	public int getForeheadHeight()
	{
		return this.foreheadHeight;
	}
	
	public int getForeheadWidth()
	{
		return this.foreheadWidth;
	}
}
