package studiplayer.audio;

import studiplayer.basic.BasicPlayer;

public abstract class SampledFile extends AudioFile{
	
	protected long duration;
	
	public SampledFile() {}
	
	public SampledFile(String path) throws NotPlayableException {}
	
	public void play() throws NotPlayableException {
		try {
			BasicPlayer.play(pathname);
		} catch (Exception e) {
			throw new NotPlayableException(pathname, "Error playing the file");
		}
	}

	public void togglePause() {
		BasicPlayer.togglePause();
	}
	
	public void stop() {
		BasicPlayer.stop();
	}

	public String formatDuration() {
		return timeFormatter(duration);
	}
	
	public String formatPosition() {
		return timeFormatter(BasicPlayer.getPosition());
	}
	
	static public String timeFormatter(long timeInMicroSeconds) {
		if (timeInMicroSeconds <= -1L || timeInMicroSeconds >= 6000000000L) {
			throw new RuntimeException();
		}
		long minutes = (timeInMicroSeconds / 1000000 / 60);
	    long seconds = timeInMicroSeconds / 1000000 - minutes * 60;
	    return String.format("%02d:%02d", minutes, seconds);
	}
	
	public long getDuration() {
        return duration;
    }

	public void setDuration(long duration) {
		this.duration = duration;
	}
}
