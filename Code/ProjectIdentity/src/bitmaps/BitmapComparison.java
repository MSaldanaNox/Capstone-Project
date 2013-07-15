package bitmaps;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BitmapComparison {

	File toConvert;
	
	public BitmapComparison(){	
	}
	
	public static void main(String[]args)
	{
		
	}
	
	
	private void convertImage()
	{
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("TestFace-png.bmp"));
		    toConvert = new File("test.bmp");
		    ImageIO.write(img, "bmp", toConvert);
		} catch (IOException e) {
		}
	}
}
