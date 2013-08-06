package facialrecognition;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class Searcher {
	public static void main(String[] args) {
		ArrayList<File> roots = new ArrayList<File>();
		roots.addAll(Arrays.asList(File.listRoots()));

		new Searcher("./images/TestOut/").search();

	}

	private String root;

	public Searcher(String root) {
		this.root = root;
	}

	public void search() {
		System.out.println(root);
		File folder = new File(root);
		File[] listOfFiles = folder.listFiles();
		if (listOfFiles == null)
			return; // Added condition check
		for (File file : listOfFiles) {
			String path = file.getPath().replace('\\', '/');
			try {
				BufferedImage img = ImageIO.read(new File(path));
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(path);
			if (file.isDirectory()) {
				new Searcher(path + "/").search();
			}
		}
	}
}