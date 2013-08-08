package currentfacerecognition;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

//30 people
//
//15 that work
//7 that dont 
//the rest are of people with heads turned
//
//with 12 celebrities get 5 of each and run the 
//algorithm to scna each and output cropped image file

public class FaceRecognition {

	private String path;
	private String imageType;
	private List<String> coords;
	private List<Integer> toBox;
	private BufferedImage master;
	private BufferedImage toSave;
	private Color skinned;
	private int botEyes;
	private int noseBot;
	private int mouthBot;
	private boolean isFace;

	public FaceRecognition(String p, BufferedImage img, String type) {
		path = p;
		imageType = type;
		master = img;
		toSave = img;
		skinned = Color.YELLOW;
		coords = new ArrayList<String>();
		toBox = new ArrayList<Integer>();
		isFace = false;
	}

	public void runTests() {
		findSkin();
		boxFace();
	}

	public void findSkin() {
		for (int h = 0; h < master.getWidth(); h++) {
			for (int v = 0; v < master.getHeight(); v++) {
				Color e = new Color(master.getRGB(h, v));
				if (isSkinRGB(e.getRed(), e.getBlue(), e.getRed())) {
					coords.add(h + " " + v);
				}
			}
		}

		for (String coord : coords) {
			String[] toPaint = coord.split(" ");
			toSave.setRGB(Integer.parseInt(toPaint[0]),
					Integer.parseInt(toPaint[1]), skinned.getRGB());
		}
	}

	public void boxFace() {
		int lx = toSave.getWidth();
		int rx = 0;
		int ty = toSave.getHeight();
		int by = 0;

		for (int leftX = 0; leftX < toSave.getWidth(); leftX++) {
			for (int topY = 0; topY < toSave.getHeight(); topY++) {
				if (toSave.getRGB(leftX, topY) == skinned.getRGB()) {
					// scanning left side
					if (leftX < lx) {
						lx = leftX;
					}
					// scanning top side
					if (topY < ty) {
						ty = topY;
					}
					// scanning right side
					if (leftX > rx) {
						rx = leftX;
					}
					// scanning bottom side
					if (topY > by) {
						by = topY;
					}
				}
			}
		}

		toBox.add(lx);
		toBox.add(ty);
		toBox.add(rx);
		toBox.add(by);

		String save = path.substring(0, path.lastIndexOf("/"));
		String newFolder = save.substring(0, save.lastIndexOf("/") - 1)
				+ "/Tests/";

		if (!new File(newFolder).exists())
			new File(newFolder).mkdirs();

		toSave = toSave.getSubimage(toBox.get(0), toBox.get(1), toBox.get(2)
				- toBox.get(0), toBox.get(3) - toBox.get(1));

		String imageName = path.substring(path.lastIndexOf("/") + 1);
		String test = "failed";

		if (calculatePercentage(toSave) >= 30) {
			test = "passed";
			toSave = master.getSubimage(toBox.get(0), toBox.get(1),
					toBox.get(2) - toBox.get(0), toBox.get(3) - toBox.get(1));
		}

		File image = new File(newFolder + "" + test + "-" + imageName);

		try {
			ImageIO.write(toSave, imageType, image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// % >= 30
	public int calculatePercentage(BufferedImage img) {
		int r = 0;
		int colored = 0;
		int total = 0;

		for (int h = 0; h < img.getWidth(); h++) {
			for (int v = 0; v < img.getHeight(); v++) {
				Color e = new Color(img.getRGB(h, v));
				if (toSave.getRGB(h, v) == skinned.getRGB()) {
					colored++;
				}
				total++;
			}
		}
		r = (int) (((double) colored / (double) total) * 100);
		return r;
	}

	public boolean hasEyes(BufferedImage img) {
		boolean hasLeft = false;
		boolean hasRight = false;

		int topEye = 0;
		int botEye = 0;
		int leftX = img.getWidth() / 4;
		for (int topY = 0; topY < img.getHeight(); topY++) {
			if (img.getRGB(leftX, topY) == skinned.getRGB()) {
				for (int y = topY; y < img.getHeight(); y++) {
					if (img.getRGB(leftX, y) != skinned.getRGB()) {
						topEye = y;
						for (int i = topEye; i < img.getHeight(); i++) {
							if (img.getRGB(leftX, i) == skinned.getRGB()) {
								botEye = i;
								hasLeft = true;
								break;
							}
						}
						break;
					}
				}
				break;
			}
		}

		int rightX = leftX * 3;
		for (int topY = 0; topY < img.getHeight(); topY++) {
			if (img.getRGB(rightX, topY) == skinned.getRGB()) {
				for (int y = topY; y < img.getHeight(); y++) {
					if (img.getRGB(rightX, y) != skinned.getRGB()) {
						topEye = y;
						for (int i = topEye; i < img.getHeight(); i++) {
							if (img.getRGB(rightX - 1, i) == skinned.getRGB()) {
								botEye = i;
								botEyes = botEye;
								hasRight = true;
								break;
							}
						}
						break;
					}
				}
				break;
			}
		}
		if (hasRight && hasLeft)
			return true;
		else
			return false;
	}

	public boolean hasNose(BufferedImage image) {
		boolean hasNose = false;

		int topNose = 0;
		int botNose = 0;
		int allowedSpace = (int) (image.getWidth() * .056925996);
		int leftX = image.getWidth() / 2;
		for (int topY = botEyes; topY < image.getHeight() && !hasNose; topY++) {
			if (image.getRGB(leftX, topY) == skinned.getRGB()) {
				for (int y = topY; y < image.getHeight() && !hasNose; y++) {
					if (image.getRGB(leftX, y) != skinned.getRGB()) {
						boolean foundSkin = false;
						for (int temp = y; temp < y + allowedSpace
								&& !foundSkin; temp++) {
							if (image.getRGB(leftX, temp) == skinned.getRGB())
								foundSkin = true;
						}
						if (!foundSkin) {
							topNose = y;
							for (int i = topNose; i < image.getHeight()
									&& !hasNose; i++) {
								if (image.getRGB(leftX, i) == skinned.getRGB()) {
									botNose = i;
									noseBot = botNose;
									hasNose = true;
								}
							}
						}
					}

				}
			}
		}

		return hasNose;
	}

	public boolean hasMouth(BufferedImage image) {
		boolean hasMouth = false;

		int topMouth = 0;
		int botMouth = 0;
		int leftX = image.getWidth() / 2;
		for (int topY = noseBot + 1; topY < image.getHeight(); topY++) {
			if (image.getRGB(leftX, topY) == skinned.getRGB()) {
				for (int y = topY; y < image.getHeight(); y++) {
					if (image.getRGB(leftX, y) != skinned.getRGB()) {
						topMouth = y;
						for (int i = topMouth; i < image.getHeight(); i++) {
							if (image.getRGB(leftX, i) == skinned.getRGB()) {
								botMouth = i;
								mouthBot = botMouth;
								hasMouth = true;
								break;
							}
						}
						break;
					}
				}
				break;
			}
		}

		return hasMouth;
	}

	public void validateFace(BufferedImage image) {
		isFace = false;

		int botFace = 0;
		int allowedSpace = (int) (image.getWidth() * .056925996);
		int leftX = image.getWidth() / 2;
		for (int topY = mouthBot; topY < image.getHeight() && !isFace; topY++) {
			if (image.getRGB(leftX, topY) == skinned.getRGB()) {
				for (int y = topY; y < image.getHeight() && !isFace; y++) {
					if (image.getRGB(leftX, y) != skinned.getRGB()) {
						boolean foundSkin = false;
						for (int temp = y; temp < y + 10 && !foundSkin; temp++) {
							if (image.getRGB(leftX, temp) == skinned.getRGB())
								foundSkin = true;
						}
						if (!foundSkin) {
							botFace = y;
							isFace = true;
						}
					}

				}
			}
		}
		int newBotBox = toBox.get(1) + botFace;
		toBox.set(3, newBotBox);
	}

	// r=95 g=40 b=20
	public boolean isSkinRGB(int r, int g, int b) {
		// if ((r > 220) | (g > 160) | (b > 180) | (r < g) | (r < b)) {
		// return false;
		// }

		if ((r < 125) | (g < 60) | (b < 40) | (r < g) | (r < b)) {
			return false;
		}
		int d = r - g;
		if (-15 < d && d < 15) {
			return false;
		}

		int max = Math.max(Math.max(r, g), b);
		int min = Math.min(Math.min(r, g), b);
		if ((max - min) < 15) {
			return false;
		}
		return true;
	}

}