import java.io.File;

import studiplayer.basic.WavParamReader;

public class WavFile extends SampledFile{
	
	public WavFile () {
		
	}

	public WavFile (String path) {
		parsePathname(path);
		File file = new File(pathname);
		
		if (!file.canRead()) {
			
			throw new RuntimeException("Invalid path"); 
		}
		
		readAndSetDurationFromFile();
	}
	

	private void readAndSetDurationFromFile() {
	    // Call the WavParamReader methods
		WavParamReader.readParams(pathname);
	    float framesPerSecond = WavParamReader.getFrameRate();
	    // Read the number of frames
	    long frames = WavParamReader.getNumberOfFrames();
	
	    // Calculate the duration
	    duration = computeDuration(frames, framesPerSecond);
	    
//	    System.out.println(getDuration());
	    // Set the duration attribute
	    setDuration(duration);
	    
//	    System.out.println(getDuration());
	}


	@Override
	public String toString() {
	    return super.toString() + " - " + formatDuration();
	}
	
	public static long computeDuration(long numberOfFrames, float frameRate) {
		System.out.println("Frames: " + numberOfFrames);
		System.out.println("Frame rate: " + (long) frameRate);
		System.out.println("Result: " + (((float)numberOfFrames / frameRate) * 1000000));
		return   (long) (((float)numberOfFrames / frameRate) * 1000000);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
