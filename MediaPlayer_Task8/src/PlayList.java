import java.util.LinkedList;
import java.util.List;

public class PlayList {
	
	private LinkedList<AudioFile> listOfAudioFiles;
	private int current = 0;

	public PlayList() {
		listOfAudioFiles = new LinkedList<>();
	}

	public PlayList(String m3uPathname) {
		listOfAudioFiles = new LinkedList<>();
	}
	
	public void add(AudioFile file) {
		listOfAudioFiles.add(file);
	}
	
	public void remove(AudioFile file) {
		listOfAudioFiles.remove(file);
	}

	public int size() {
		return listOfAudioFiles.size();	
	}
	
	public AudioFile currentAudioFile() {
		return null;
	}
	
	public void nextSong() {
		if (current > this.size() - 2) { // 1 extra decrement because the index in the list starts at 0
			current = 0;
		}
		else {
			current += 1;
		}
	}
	
	public void loadFromM3U(String pathname) {
		AudioFile audioFile = AudioFileFactory.createAudioFile(pathname);
	}
	
	public void saveAsM3U(String pathname) {
		
	}
	
	public List<AudioFile> getList(){
		return listOfAudioFiles;
	}
	
	public int getCurrent() {
		return current;
	}
	
	public void setCurrent(int current) {
		this.current = current;
	}
	
	public static void main(String[] args) {}

}
