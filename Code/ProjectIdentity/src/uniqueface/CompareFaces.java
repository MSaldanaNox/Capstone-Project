package uniqueface;

public class CompareFaces {

	Face originalFace;
	
	public CompareFaces(Face original)
	{
		this.originalFace = original;
	}
	
	public double compareFaces(Face toCompare)
	{
		double percent = evaluateDifference(originalFace.getFaceHeight(), toCompare.getFaceHeight()) +
		evaluateDifference(originalFace.getFaceWidth(), toCompare.getFaceWidth()) +
		
		evaluateDifference(originalFace.getBetweenForeAndEyes(), toCompare.getBetweenForeAndEyes()) +
		evaluateDifference(originalFace.getBetweenForeAndNose(), toCompare.getBetweenForeAndNose()) +
		evaluateDifference(originalFace.getBetweenForeAndMouth(), toCompare.getBetweenForeAndMouth()) +
		evaluateDifference(originalFace.getBetweenEyesAndNose(), toCompare.getBetweenEyesAndNose()) +
		evaluateDifference(originalFace.getBetweenEyesAndMouth(), toCompare.getBetweenEyesAndMouth()) +
		evaluateDifference(originalFace.getBetweenNoseAndMouth(), toCompare.getBetweenNoseAndMouth()) +
		evaluateDifference(originalFace.getBetweenForeAndNose(), toCompare.getBetweenForeAndNose()) +
		
		evaluateDifference(originalFace.getForehead().getForeheadHeight(), toCompare.getForehead().getForeheadHeight()) +
		evaluateDifference(originalFace.getForehead().getForeheadWidth(), toCompare.getForehead().getForeheadWidth()) +
		
		evaluateDifference(originalFace.getEyes().getEyeLength(), toCompare.getEyes().getEyeLength()) +
		evaluateDifference(originalFace.getEyes().getEyeSpace(), toCompare.getEyes().getEyeSpace()) +
		
		evaluateDifference(originalFace.getNose().getNoseX(), toCompare.getNose().getNoseX()) +
		evaluateDifference(originalFace.getNose().getNoseY(), toCompare.getNose().getNoseY()) +
		evaluateDifference(originalFace.getNose().getNoseHeight(), toCompare.getNose().getNoseHeight()) +
		evaluateDifference(originalFace.getNose().getNoseWidth(), toCompare.getNose().getNoseWidth()) +
		
		evaluateDifference(originalFace.getMouth().getMouthX(), toCompare.getMouth().getMouthX()) +
		evaluateDifference(originalFace.getMouth().getMouthY(), toCompare.getMouth().getMouthY()) +
		evaluateDifference(originalFace.getMouth().getLipThickness(), toCompare.getMouth().getLipThickness()) +
		evaluateDifference(originalFace.getMouth().getMouthSpace(), toCompare.getMouth().getMouthSpace());
		
		return percent;
	}
	
	public double evaluateDifference(int original, int compare)
	{
		double result = 0;
		int difference = original - compare;
		if(-10 <= difference || difference <= 10)
		{
			if(difference == 0)
				result = (double)(100)/(double)(21);
			else
				result = (double)(50)/(double)(21);
		}
		return result;
	}
}
