package bitmaps;

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

public class TestFacialRecognition {

	List<String> coords;
	List<Integer> toBox;

	public static void main(String[] args) {
		new TestFacialRecognition();
	}

	public TestFacialRecognition() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
				} catch (Exception ex) {
				}

				JFrame frame = new JFrame("Face Recognition");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.add(new TestPane());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);

			}
		});
	}

	public class TestPane extends JPanel {

		private BufferedImage master;
		private BufferedImage toSave;
		private Color skinned;
		private int noseBot;
		private int mouthBot;

		public TestPane() {
			try {
				master = ImageIO.read(new File("./images/test4.jpg"));
				toSave = ImageIO.read(new File("./images/test4.jpg"));
				skinned = Color.YELLOW;
				coords = new ArrayList<String>();
				toBox = new ArrayList<Integer>();

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

				boxFace();
				System.out.println(hasEyes());
				System.out.println(hasNose());
				System.out.println(hasMouth());
				validateFace();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		@Override
		public Dimension getPreferredSize() {
			Dimension size = super.getPreferredSize();
			if (master != null) {
				size = new Dimension(master.getWidth() * 3, master.getHeight());
			}
			return size;
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (master != null) {

				int x = (getWidth() - (master.getWidth() * 3)) / 2;
				int y = (getHeight() - master.getHeight()) / 2;

				BufferedImage image;
				try {
					image = ImageIO.read(new File("./images/AnalyzeMe.png"));
					g.drawImage(image, x, y, this);
					g.setColor(Color.blue);
					g.drawOval(168, 304, 10, 10);
					g.setColor(Color.blue);
					g.drawOval(168, 500, 10, 10);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				x += master.getWidth();

				// Draw original image
				g.drawImage(master, x, y, this);

				// Draw image with identified skin-toned pixels
				x += master.getWidth();
				// g.drawImage(master, x, y, this);
				g.drawImage(master, x, y, this);
				for (String coord : coords) {
					String[] toPaint = coord.split(" ");
					g.setColor(skinned);
					g.drawOval(x + Integer.parseInt(toPaint[0]),
							y + Integer.parseInt(toPaint[1]), 0, 0);
				}
				g.setColor(Color.RED);
				g.drawRect(x + toBox.get(0), toBox.get(1),
						toBox.get(2) - toBox.get(0),
						toBox.get(3) - toBox.get(1));
			}
		}

		public void boxFace() {
			File image = new File("./images/AnalyzeMe.png");

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

			try {
				ImageIO.write(
						toSave.getSubimage(toBox.get(0), toBox.get(1),
								toBox.get(2) - toBox.get(0), toBox.get(3)
										- toBox.get(1)), "png", image);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public boolean hasEyes() {
			boolean hasLeft = false;
			boolean hasRight = false;
			BufferedImage image;
			try {
				image = ImageIO.read(new File("./images/AnalyzeMe.png"));
				int topEye = 0;
				int botEye = 0;
				int leftX = image.getWidth() / 4;
				for (int topY = 0; topY < image.getHeight(); topY++) {
					if (image.getRGB(leftX, topY) == skinned.getRGB()) {
						for (int y = topY; y < image.getHeight(); y++) {
							if (image.getRGB(leftX, y) != skinned.getRGB()) {
								topEye = y;
								for (int i = topEye; i < image.getHeight(); i++) {
									if (image.getRGB(leftX, i) == skinned
											.getRGB()) {
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
				for (int topY = 0; topY < image.getHeight(); topY++) {
					if (image.getRGB(rightX, topY) == skinned.getRGB()) {
						for (int y = topY; y < image.getHeight(); y++) {
							if (image.getRGB(rightX, y) != skinned.getRGB()) {
								topEye = y;
								for (int i = topEye; i < image.getHeight(); i++) {
									if (image.getRGB(rightX - 1, i) == skinned
											.getRGB()) {
										botEye = i;
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
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (hasRight && hasLeft)
				return true;
			else
				return false;
		}

		public boolean hasNose() {
			boolean hasNose = false;

			BufferedImage image;
			try {
				image = ImageIO.read(new File("./images/AnalyzeMe.png"));
				int topNose = 0;
				int botNose = 0;
				int leftX = image.getWidth() / 2;
				for (int topY = 0; topY < image.getHeight() && !hasNose; topY++) {
					if (image.getRGB(leftX, topY) == skinned.getRGB()) {
						for (int y = topY; y < image.getHeight() && !hasNose; y++) {
							if (image.getRGB(leftX, y) != skinned.getRGB()) {
								boolean foundSkin = false;
								for (int temp = y; temp < y + 30 && !foundSkin; temp++) {
									if (image.getRGB(leftX, temp) == skinned
											.getRGB())
										foundSkin = true;
								}
								if (!foundSkin) {
									topNose = y;
									for (int i = topNose; i < image.getHeight()
											&& !hasNose; i++) {
										if (image.getRGB(leftX, i) == skinned
												.getRGB()) {
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
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return hasNose;
		}

		public boolean hasMouth() {
			boolean hasMouth = false;

			BufferedImage image;
			try {
				image = ImageIO.read(new File("./images/AnalyzeMe.png"));
				int topMouth = 0;
				int botMouth = 0;
				int leftX = image.getWidth() / 2;
				for (int topY = noseBot + 1; topY < image.getHeight(); topY++) {
					if (image.getRGB(leftX, topY) == skinned.getRGB()) {
						for (int y = topY; y < image.getHeight(); y++) {
							if (image.getRGB(leftX, y) != skinned.getRGB()) {
								topMouth = y;
								for (int i = topMouth; i < image.getHeight(); i++) {
									if (image.getRGB(leftX, i) == skinned
											.getRGB()) {
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
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return hasMouth;
		}

		public void validateFace()
		{
			boolean isFace = false;
			BufferedImage image;
			try {
				image = ImageIO.read(new File("./images/AnalyzeMe.png"));
				int botFace = 0;
				int leftX = image.getWidth() / 2;
				for (int topY = mouthBot; topY < image.getHeight() && !isFace; topY++) {
					if (image.getRGB(leftX, topY) == skinned.getRGB()) {
						for (int y = topY; y < image.getHeight() && !isFace; y++) {
							if (image.getRGB(leftX, y) != skinned.getRGB()) {
								boolean foundSkin = false;
								for (int temp = y; temp < y + 30 && !foundSkin; temp++) {
									if (image.getRGB(leftX, temp) == skinned
											.getRGB())
										foundSkin = true;
								}
								if (!foundSkin) {
									for (int i = y; i < image.getHeight()
											&& !isFace; i++) {
										if (image.getRGB(leftX, i) == skinned
												.getRGB()) {
											botFace = i;
											isFace = true;
										}
									}
								}
							}

						}
					}
				}
				System.out.println(botFace);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		// r=95 g=40 b=20
		public boolean isSkinRGB(int r, int g, int b) {
			if ((r < 95) | (g < 40) | (b < 20) | (r < g) | (r < b)) {
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
}