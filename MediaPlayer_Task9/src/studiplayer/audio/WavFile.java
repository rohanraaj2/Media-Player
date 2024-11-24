package studiplayer.audio;

import java.io.File;

import studiplayer.basic.WavParamReader;

public class WavFile extends SampledFile {
	
	public WavFile () {}

	public WavFile (String path) throws NotPlayableException {
		parsePathname(path);
		File file = new File(pathname);
		
		parseFilename(filename);
		if (!file.canRead()) {
			throw new NotPlayableException("Invalid path: ", path);
		}
		readAndSetDurationFromFile();
	}
	
	public void readAndSetDurationFromFile() throws NotPlayableException {
	    try {
	        WavParamReader.readParams(pathname);
	        float framesPerSecond = WavParamReader.getFrameRate();
	        long frames = WavParamReader.getNumberOfFrames();
	
	        duration = computeDuration(frames, framesPerSecond);
	        setDuration(duration);
	    } catch (Exception e) {
	    	throw new NotPlayableException(pathname, "Error reading the file");
	    }
	}

	@Override
	public String toString() {
	    return super.toString() + " - " + formatDuration();
	}
	
	public static long computeDuration(long numberOfFrames, float frameRate) {
		return (long) (((float)numberOfFrames / frameRate) * 1000000);
	}
}
