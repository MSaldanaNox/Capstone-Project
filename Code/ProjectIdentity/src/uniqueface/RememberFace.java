package uniqueface;

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
		forehead = new Forehead();
	}

	public void createNose(Dimension d) {
		nose = new Nose();
	}

	public void createEyes(int space, int length) {
		eyes = new Eyes();
	}

	public void createMouth(int space, int thickness) {
		mouth = new Mouth();
	}

	public void createFace(String name, int height, int width) {
		face = new Face("Test", height, width, forehead, nose, eyes, mouth);
	}
}
