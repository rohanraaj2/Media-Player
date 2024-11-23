package studiplayer.audio;

import java.util.Map;

import studiplayer.basic.TagReader;

public class TaggedFile extends SampledFile{
    
	private String album;
	
	public TaggedFile () {}
	
	public TaggedFile (String path) throws NotPlayableException {
		parsePathname(path);
		readAndStoreTags();
	}
	
	public String getAlbum() {
		return album.trim();
	}
	
	public void readAndStoreTags() throws NotPlayableException {
		try {
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
	        if (this.title == "") {
	    		parseFilename(filename);
	        }
		} catch(Exception e) {
			throw new NotPlayableException(pathname, "Error reading the file");
		}
    }
	
	@Override
	public String toString() {
	    StringBuilder result = new StringBuilder();
	    
	    if (author != null && !author.isEmpty()) {
	        result.append(author.trim());
	    }
	    if (title != null && !title.isEmpty()) {
	        if (result.length() > 0) {
	            result.append(" - ");
	        }
	        result.append(title.trim());
	    }

	    if (album != null && !album.isEmpty()) {
	        if (result.length() > 0) {
	            result.append(" - ");
	        }
	        result.append(album.trim());
	    }

	    if (duration > 0) {
	        if (result.length() > 0) {
	            result.append(" - ");
	        }
	        result.append(formatDuration());
	    }

	    return result.toString().trim();
	}

	public static void main(String[] args) {}
}
