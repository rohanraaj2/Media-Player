package studiplayer.audio;

public class AudioFileFactory {

	public static AudioFile createAudioFile(String path) throws NotPlayableException {
		String extension = path.substring(path.lastIndexOf(".") + 1);
		switch (extension.toLowerCase()) {
		    case "wav":
		        return new WavFile(path);
		    case "mp3":
		    case "ogg":
		        return new TaggedFile(path);
		    default:
		        throw new IllegalArgumentException("Unknown suffix for AudioFile \"" + path + "\"");
		}
	}
	
	public static void main(String[] args) {}

}
