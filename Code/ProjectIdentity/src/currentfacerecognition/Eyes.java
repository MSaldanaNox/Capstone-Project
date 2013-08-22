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
