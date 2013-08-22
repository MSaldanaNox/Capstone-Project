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
public class Nose {

	int noseWidth;
	int noseHeight;
	
	public Nose(int w, int h)
	{
		this.noseWidth = w;
		this.noseHeight = h;
	}
	
	public int getNoseWidth()
	{
		return this.noseWidth;
	}
	
	public int getNoseHeight()
	{
		return this.noseHeight;
	}
	
}
