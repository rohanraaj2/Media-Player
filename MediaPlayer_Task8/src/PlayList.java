import java.util.LinkedList;
import java.util.List;

public class PlayList {
	
	
	
	private LinkedList<AudioFile> listOfAudioFiles;

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
		return 0;	
	}
	
	public void setCurrent(int current) {
		
	}
	
	public static void main(String[] args) {}

}
