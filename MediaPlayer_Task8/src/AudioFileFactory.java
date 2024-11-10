
public class AudioFileFactory {

	public static AudioFile createAudioFile(String path) {
		String extension = path.substring(path.lastIndexOf(".") + 1);
        switch (extension) {
            case "wav":
                return new WavFile(path);
            case "mp3":
                return new TaggedFile(path);
            default:
                throw new IllegalArgumentException("Unsupported file format");
        }
	}
	
	public static void main(String[] args) {

	}

}
