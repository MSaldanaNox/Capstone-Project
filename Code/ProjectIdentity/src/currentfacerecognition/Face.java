package currentfacerecognition;

public class Face {
	
	int faceWidth;
	int faceHeight;
	String name;
	
	public Face(String n, int h, int w)
	{
		name = n;
		faceHeight = h;
		faceWidth = w;
	}

	public String getName()
	{
		return name;
	}
	
	public int getFaceWidth() {
		return faceWidth;
	}

	public int getFaceHeight() {
		return faceHeight;
	}
	
	
}
