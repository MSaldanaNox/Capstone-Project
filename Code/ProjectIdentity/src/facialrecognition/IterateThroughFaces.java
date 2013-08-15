package facialrecognition;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import currentfacerecognition.FaceRecognition;

public class IterateThroughFaces {
	String root;
	FaceRecognition fr;

	public IterateThroughFaces()
	{
		root = "./images/TestOut/";
	}

	public static void main(String[]args)
	{
		IterateThroughFaces app = new IterateThroughFaces();
		app.search();
	}

	public void search() {
		File folder = new File(root);
		File[] listOfFiles = folder.listFiles();
		if (listOfFiles == null)
			return; 
		for (File file : listOfFiles) {
			String path = file.getPath().replace('\\', '/');
			String[] pathData = path.split("\\.");
			String imgType = pathData[pathData.length-1];
			try {
				BufferedImage img = ImageIO.read(new File(path));
				fr = new FaceRecognition(path, img, imgType);
				fr.runTests();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (file.isDirectory()) {
				new Searcher(path + "/").search();
			}
		}
	}
}