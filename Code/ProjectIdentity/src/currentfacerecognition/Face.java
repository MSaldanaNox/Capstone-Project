package currentfacerecognition;

public class Face {
	
	int faceWidth;
	int faceHeight;
	Nose nose;
	Eyes eyes;
	Mouth mouth;
	
	String name;
	
	public Face(String n, int h, int w, Nose nose, Eyes eyes, Mouth mouth)
	{
		this.name = n;
		this.faceHeight = h;
		this.faceWidth = w;
		this.nose = nose;
		this.eyes = eyes;
		this.mouth = mouth;
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
