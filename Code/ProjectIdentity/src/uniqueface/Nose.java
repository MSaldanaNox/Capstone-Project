package uniqueface;

import java.awt.Dimension;
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
public class Nose {

	int noseWidth;
	int noseHeight;
	int noseX;
	int noseY;
	
	public void setNoseDimensions(Dimension d)
	{
		this.noseWidth = d.width;
		this.noseHeight = d.height;
	}
	
	public void setNoseLocation(Point p)
	{
		this.noseX = p.x;
		this.noseY = p.y;
	}
	
	public int getNoseWidth()
	{
		return this.noseWidth;
	}
	
	public int getNoseHeight()
	{
		return this.noseHeight;
	}
	
	public int getNoseX()
	{
		return this.noseX;
	}
	
	public int getNoseY()
	{
		return this.noseY;
	}
}
