package bitmaps;

import java.awt.Color;
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
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class TestBlackAndWhite {

	int rgbX, rgbY;
	List<String> coords;
	
	
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
		private BufferedImage depthMap;
		private BufferedImage grayScale;
		private BufferedImage blackWhite;

		public TestPane() {
			try {
				master = ImageIO.read(new File("./images/WP_001078.jpg"));
				blackWhite = ImageIO.read(new File("./images/WP_001078.jpg"));
				grayScale = ImageIO.read(new File("./images/WP_001078.jpg"));
				ColorConvertOp op = new ColorConvertOp(
						ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
				op.filter(grayScale, grayScale);

				if (grayScale == null) {
					File outputfile = new File("./images/saved.png");
					ImageIO.write(grayScale, "png", outputfile);
				} else {
					blackWhite = new BufferedImage(master.getWidth(),
							master.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
					Graphics2D g2d = blackWhite.createGraphics();

					g2d.drawImage(grayScale, 0, 0, (ImageObserver) this);
					
					g2d.dispose();
				}
				
				coords = new ArrayList<String>();

				SkinTones st = new SkinTones();
				
				for (int h = 0; h < blackWhite.getWidth(); h++) {
					for (int v = 0; v < blackWhite.getHeight(); v++) {
						
//						boolean isIn = false;
						
//						for(Color c : st.getSkinTones())
//						{
//							Color e = new Color(master.getRGB(h, v));
//							if(c.getRGB()==e.getRGB())
//							{
//								isIn = true;
//							}
//						}
						Color e = new Color(master.getRGB(h, v));
						if(isSkinRGB(e.getRed(), e.getBlue(), e.getRed()))
						{
							coords.add(h + " " + v);
						}
					}
				}
				
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		@Override
		public Dimension getPreferredSize() {
			Dimension size = super.getPreferredSize();
			if (master != null) {
				size = new Dimension(master.getWidth() * 2, master.getHeight());
			}
			return size;
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (master != null) {

				int x = (getWidth() - (master.getWidth() * 2)) / 2;
				int y = (getHeight() - master.getHeight()) / 2;
				
				g.drawImage(master, x, y, this);
				x += master.getWidth();
				g.drawImage(master, x, y, this);

				for(String coord : coords)
				{
					String[] toPaint = coord.split(" ");
					g.setColor(Color.YELLOW);
					g.drawOval(x+ Integer.parseInt(toPaint[0]), y+Integer.parseInt(toPaint[1]), 1, 1);
				}
			}
		}

		public boolean isSkinRGB(int r, int g, int b) {
		    if ( (r<95) | (g<40) | (b<20) | (r<g) | (r<b) ) {
		        return false;
		    }
		    int d = r-g;
		    if ( -15<d && d<15) {
		        return false; 
		    }

		    int max = Math.max(Math.max(r,g),b);
		    int min = Math.min(Math.min(r,g),b);
		    if ((max-min)<15) {
		        return false;
		    }
		    return true;
		}
	}
}