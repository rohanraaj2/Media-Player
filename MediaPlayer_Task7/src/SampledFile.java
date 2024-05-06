import studiplayer.basic.BasicPlayer;

public class SampledFile extends AudioFile{
	
	protected long duration;
	
	public SampledFile() {
		
	}
	
	public SampledFile(String path) {
		
	}
	
	public void play() {
		BasicPlayer.play(pathname);
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
