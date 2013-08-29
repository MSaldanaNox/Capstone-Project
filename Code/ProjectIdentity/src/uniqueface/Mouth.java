package uniqueface;

import java.awt.Point;

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

	int mouthX;
	int mouthY;
	int mouthSpace;
	int lipThickness;

	public void setMouthLocation(Point p)
	{
		this.mouthX = p.x;
		this.mouthY = p.y;
	}
	
	public void setMouthSpace(int space)
	{
		this.mouthSpace = space;
	}
	
	public void setLipThickness(int thickness)
	{
		this.lipThickness = thickness;
	}

	public int getMouthX()
	{
		return this.mouthX;
	}
	
	public int getMouthY()
	{
		return this.mouthY;
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