import java.util.Map;

import studiplayer.basic.TagReader;

public class TaggedFile extends SampledFile{
    
	private String album;
	public TaggedFile () {
		
	}
	
	public TaggedFile (String path) {
		parsePathname(path);
		readAndStoreTags();
	}
	
	public String getAlbum() {
		return album.trim();
	}
	
	public void readAndStoreTags() {
        Map<String, Object> tagMap = TagReader.readTags(this.pathname);
        this.title = (String) tagMap.getOrDefault("title", "");
        this.author = (String) tagMap.getOrDefault("author", "");
        this.album = (String) tagMap.getOrDefault("album", "");
        Object durationValue = tagMap.get("duration");

        if (durationValue instanceof Number) {
            this.duration = ((Number) durationValue).longValue();
        } else {
            this.duration = 0;  // Default value or error handling
        }
    }
	
	@Override
	public String toString() {
	    String baseString = super.toString();// Get the author and title
	    if (album != null && !album.isEmpty()) {
	        String fullString = String.format("%s - %s - %s", baseString, album.trim(), formatDuration().trim());
	        return fullString.trim();
	    } else {
	        return super.toString();
	    }
	}





	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
