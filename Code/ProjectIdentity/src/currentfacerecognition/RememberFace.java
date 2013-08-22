package currentfacerecognition;

public class RememberFace {

	public RememberFace()
	{
		
	}
	
	public void recordFace()
	{
		Forehead forehead = new Forehead(0, 0);
		Nose nose = new Nose();
		Eyes eyes = new Eyes();
		Mouth mouth = new Mouth();
		Face face = new Face("Test", 0, 0, new Forehead(0, 0), new Nose(0, 0), new Eyes(0, 0, 0, 0, 0), new Mouth(0, 0));
	}
	
}
