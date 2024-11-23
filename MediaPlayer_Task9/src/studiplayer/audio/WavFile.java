package studiplayer.audio;

import java.io.File;

import studiplayer.basic.WavParamReader;

public class WavFile extends SampledFile{
	
	public WavFile () {
		
	}

	public WavFile (String path) {
		parsePathname(path);
		File file = new File(pathname);
		
		parseFilename(filename);
		if (!file.canRead()) {
			
			throw new RuntimeException("Invalid path"); 
		}
		
		readAndSetDurationFromFile();
	}
	

	private void readAndSetDurationFromFile() {
		WavParamReader.readParams(pathname);
	    float framesPerSecond = WavParamReader.getFrameRate();
	    long frames = WavParamReader.getNumberOfFrames();
	
	    duration = computeDuration(frames, framesPerSecond);
	    
	    setDuration(duration);
	    
	}


	@Override
	public String toString() {
	    return super.toString() + " - " + formatDuration();
	}
	
	public static long computeDuration(long numberOfFrames, float frameRate) {
		return (long) (((float)numberOfFrames / frameRate) * 1000000);
	}

	public static void main(String[] args) {

	}

}
