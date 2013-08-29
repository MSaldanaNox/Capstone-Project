package uniqueface;

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

	public void setEyeSpace(int space) {
		this.eyeSpace = space;
	}

	public void setEyeLength(int length) {
		this.eyeLength = length;
	}

	public int getEyeSpace() {
		return this.eyeSpace;
	}

	public int getEyeLength() {
		return this.eyeLength;
	}
}
