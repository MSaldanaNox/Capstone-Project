package currentfacerecognition;

public class Eyes {
	
	int eyeSpace;
	int eyeLength;
	int eyeRed;
	int eyeBlue;
	int eyeGreen;
	
	public Eyes(int s, int l, int r, int g, int b)
	{
		eyeSpace = s;
		eyeLength = l;
		eyeRed = r;
		eyeBlue = b;
		eyeGreen = g;
	}

	public int getEyeSpace() {
		return this.eyeSpace;
	}

	public int getEyeLength() {
		return this.eyeLength;
	}

	public int getEyeRed() {
		return this.eyeRed;
	}

	public int getEyeBlue() {
		return this.eyeBlue;
	}

	public int getEyeGreen() {
		return this.eyeGreen;
	}
}
