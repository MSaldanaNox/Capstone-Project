package facialrecognition;

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

public class TestBlackAndWhite {

	List<String> coords;
	List<Integer> toBox;
	
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
		private BufferedImage toSave;
		
		
		public TestPane() {
			try {
				master = ImageIO.read(new File("./images/test4.jpg"));
				toSave = ImageIO.read(new File("./images/test4.jpg"));
				coords = new ArrayList<String>();
				toBox = new ArrayList<Integer>();
				
				for (int h = 0; h < master.getWidth(); h++) {
					for (int v = 0; v < master.getHeight(); v++) {
						Color e = new Color(master.getRGB(h, v));
						if(isSkinRGB(e.getRed(), e.getBlue(), e.getRed()))
						{
							coords.add(h + " " + v);
						}
					}
				}
				
				for(String coord : coords)
				{
					String[] toPaint = coord.split(" ");
					toSave.setRGB(Integer.parseInt(toPaint[0]), Integer.parseInt(toPaint[1]), Color.YELLOW.getRGB());
				}
				
				File image = new File("./images/BoxMe.png");
				ImageIO.write(toSave, "png", image);
				
				int lx = toSave.getWidth();
				int rx = 0;
				int ty = toSave.getHeight();
				int by = 0;
				
				for(int leftX = 0; leftX < toSave.getWidth(); leftX++)
				{
					for(int topY = 0; topY < toSave.getHeight(); topY++)
					{
						if(toSave.getRGB(leftX, topY) == Color.YELLOW.getRGB())
						{
							if(leftX<lx)
							{
								lx=leftX;
							}
							if(topY<ty)
							{
								ty=topY;
							}
							if(leftX>rx)
							{
								rx=leftX;
							}
							if(topY>by)
							{
								by=topY;
							}
						}	
					}
				}
				
				toBox.add(lx);
				toBox.add(ty);
				toBox.add(rx);
				toBox.add(by);
				
				System.out.println(lx+" "+rx+" "+ty+" "+by);
				
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
				
				// Draw original image
				g.drawImage(master, x, y, this);
				
				// Draw image with identified skin-toned pixels
				x += master.getWidth();
				g.drawImage(master, x, y, this);
				for(String coord : coords)
				{
					String[] toPaint = coord.split(" ");
					g.setColor(Color.YELLOW);
					g.drawOval(x+ Integer.parseInt(toPaint[0]), y+Integer.parseInt(toPaint[1]), 1, 1);
				}
				g.setColor(Color.RED);
				g.drawRect(toBox.get(0), toBox.get(1), toBox.get(2)-toBox.get(0), toBox.get(3)-toBox.get(1));
				
			}
		}

		// r=95 g=40 b=20
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