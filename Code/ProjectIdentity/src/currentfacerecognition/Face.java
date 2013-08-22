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
	
	public Face(String name, int height, int weight, Forehead fore, Nose nose, Eyes eyes, Mouth mouth)
	{
		this.name = name;
		this.faceHeight = height;
		this.faceWidth = weight;
		this.forehead = fore;
		this.nose = nose;
		this.eyes = eyes;
		this.mouth = mouth;
	}
}
