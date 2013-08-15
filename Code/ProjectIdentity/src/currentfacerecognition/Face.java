package currentfacerecognition;

public class Face {
	
	int faceWidth;
	int faceHeight;
	Forehead forehead;
	Nose nose;
	Eyes eyes;
	Mouth mouth;
	
	public int getFaceWidth() {
		return faceWidth;
	}

	public int getFaceHeight() {
		return faceHeight;
	}

	public Forehead getForehead() {
		return forehead;
	}

	public Nose getNose() {
		return nose;
	}

	public Eyes getEyes() {
		return eyes;
	}

	public Mouth getMouth() {
		return mouth;
	}

	public String getName() {
		return name;
	}

	String name;
	
	public Face(String n, int h, int w, Forehead fore, Nose nose, Eyes eyes, Mouth mouth)
	{
		this.name = n;
		this.faceHeight = h;
		this.faceWidth = w;
		this.forehead = fore;
		this.nose = nose;
		this.eyes = eyes;
		this.mouth = mouth;
	}
}
