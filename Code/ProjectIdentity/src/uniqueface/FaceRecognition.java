package uniqueface;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
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
//algorithm to scan each and output cropped image file

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
	private RememberFace face;

	public FaceRecognition(String p, BufferedImage img, String type) {
		path = p;
		imageType = type;
		master = img;
		toSave = img;
		skinned = Color.YELLOW;
		coords = new ArrayList<String>();
		toBox = new ArrayList<Integer>();
		isFace = false;
		face = new RememberFace();
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

		if (hasEyes(toSave))
			if (hasNose(toSave))
				if (hasMouth(toSave)) {
					if (calculatePercentage(toSave) >= 30) {
						test = "passed";

						int w = toBox.get(2) - toBox.get(0);
						int h = toBox.get(3) - toBox.get(1);

						if (h == 0)
							h = toBox.get(3);

						toSave = master.getSubimage(toBox.get(0), toBox.get(1),
								w, h);
					}
				}
		File image = new File(newFolder + "" + test + "-" + imageName);
		System.out.println(image.toString());
		try {
			ImageIO.write(toSave, imageType, image);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedImage resizeImage(BufferedImage originalImage) {
		BufferedImage resizedImage = new BufferedImage(720, 800,
				originalImage.getType());
		Graphics2D g = resizedImage.createGraphics();
		System.out.println(originalImage.getHeight());
		double ratio = 800.0 / ((double) originalImage.getHeight());
		System.out.println(ratio);

		int width = (int) (originalImage.getWidth() * ratio);
		System.out.println(width);
		// g.drawImage(originalImage, 0, 0, width, 800, null);
		g.dispose();
		g.setComposite(AlphaComposite.Src);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		return resizedImage;
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

		face.createEyes(rightX - leftX, rightX - (rightX / 2));

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
						for (int temp = y; temp < image.getHeight()
								&& temp < y + allowedSpace && !foundSkin; temp++) {
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
		face.createMouth(topMouth, botMouth);
		face.createFace("Test Face - last face to do", image.getHeight(),
				image.getWidth());

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
						for (int temp = y; temp < image.getHeight()
								&& temp < y + 10 && !foundSkin; temp++) {
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

	public void recordFace()
	{
		recordFaceDimensions(recordForehead(), recordEyes(), recordNose(), recordMouth());
	}
	
	public void recordFaceDimensions(Forehead fore, Eyes eyes, Nose nose, Mouth mouth)
	{
		Face face = new Face("Name", toSave.getHeight(), toSave.getWidth(), fore, nose, eyes, mouth);
		
	}
	
	public Forehead recordForehead()
	{
		Forehead fore = new Forehead();
		fore.setForeheadDimensions(new Dimension());
		return fore;
	}
	
	public Eyes recordEyes()
	{
		Eyes eyes = new Eyes();
		eyes.setEyeLength(0);
		eyes.setEyeSpace(0);
		return eyes;
	}
	
	public Nose recordNose()
	{
		Nose nose = new Nose();
		nose.setNoseDimensions(new Dimension());
		nose.setNoseLocation(new Point());
		return nose;
	}
	
	public Mouth recordMouth()
	{
		Mouth mouth = new Mouth();
		mouth.setMouthLocation(new Point());
		mouth.setMouthSpace(0);
		return mouth;
	}
	
	public void matchFace()
	{
		
	}
	
	// r=95 g=40 b=20
	// Outlines can be found by switching the upper color bounds to | instead of
	// &
	public boolean isSkinRGB(int r, int g, int b) {
		if ((r > 235) & (g > 180) & (b > 160)) {
			return false;
		}

		if ((r < 125) | (g < 70) | (b < 50) | (r < g) | (r < b)) {
			return false;
		}
		int d = r - g;
		if (-20 < d && d < 20) {
			return false;
		}

		int max = Math.max(Math.max(r, g), b);
		int min = Math.min(Math.min(r, g), b);
		if ((max - min) < 20) {
			return false;
		}
		return true;
	}

}