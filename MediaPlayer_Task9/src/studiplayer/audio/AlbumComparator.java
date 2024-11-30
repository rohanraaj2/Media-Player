package studiplayer.audio;

import java.util.Comparator;

public class AlbumComparator implements Comparator<AudioFile> {
	
	@Override
	public int compare(AudioFile o1, AudioFile o2) {
		String album1 = o1 instanceof TaggedFile ? ((TaggedFile) o1).getAlbum() : "";
	    String album2 = o2 instanceof TaggedFile ? ((TaggedFile) o2).getAlbum() : "";
	    
	    if (album1.isEmpty() && !album2.isEmpty()) {
	        return -1;
	    } else if (!album1.isEmpty() && album2.isEmpty()) {
	        return 1;
	    }
	    return 0;
	}
}
