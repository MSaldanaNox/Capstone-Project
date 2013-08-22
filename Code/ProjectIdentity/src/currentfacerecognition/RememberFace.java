package currentfacerecognition;

import java.awt.Dimension;

public class RememberFace {

	Forehead forehead;
	Nose nose;
	Eyes eyes;
	Mouth mouth;
	Face face;

	public RememberFace() {
	}

	public void createForehead(Dimension d) {
		forehead = new Forehead(d.height, d.width);
	}

	public void createNose(Dimension d) {
		nose = new Nose(d.height, d.width);
	}

	public void createEyes(int space, int length) {
		eyes = new Eyes(space, length, 0, 0, 0);
	}

	public void createMouth(int space, int thickness) {
		mouth = new Mouth(space, thickness);
	}

	public void createFace(String name, int height, int width) {
		face = new Face("Test", height, width, forehead, nose, eyes, mouth);
	}
}
