package bitmaps;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class TestBlackAndWhite {

	public static void main(String[] args) {
		new TestBlackAndWhite();
	}

	public TestBlackAndWhite() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
				} catch (Exception ex) {
				}

				JFrame frame = new JFrame("Test");
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
		private BufferedImage grayScale;
		private BufferedImage blackWhite;

		public TestPane() {
			try {
				master = ImageIO.read(new File("./images/TestFace-png.bmp"));
				grayScale = ImageIO.read(new File("./images/TestFace-png.bmp"));
				ColorConvertOp op = new ColorConvertOp(
						ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
				op.filter(grayScale, grayScale);

				if (grayScale == null) {
					System.out.println("dsssssssssss");
					File outputfile = new File("./images/saved.png");
					ImageIO.write(grayScale, "png", outputfile);
				} else {
					blackWhite = new BufferedImage(master.getWidth(),
							master.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
					Graphics2D g2d = blackWhite.createGraphics();

					g2d.drawImage(grayScale, 0, 0, (ImageObserver) this);

					g2d.dispose();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		private void writeFile() {

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

				g.drawImage(master, x, y, this);
				x += master.getWidth();
				g.drawImage(grayScale, x, y, this);
				x += master.getWidth();
				g.drawImage(blackWhite, x, y, this);

			}
		}

	}
}