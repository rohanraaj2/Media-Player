package studiplayer.audio;

import java.util.Comparator;

public class AuthorComparator implements Comparator<AudioFile> {

    @Override
    public int compare(AudioFile o1, AudioFile o2) {
        String author1 = o1.getAuthor();
        String author2 = o2.getAuthor();

        if (author1.isEmpty() && !author2.isEmpty()) {
	        return -1;
	    } else if (!author1.isEmpty() && author2.isEmpty()) {
	        return 1;
        } else {
	        char firstCharO1 = author1.charAt(0);
	        char firstCharO2 = author2.charAt(0);

	        if (firstCharO1 < firstCharO2) {
	            return -1;
	        } else if (firstCharO1 > firstCharO2) {
	            return 1;
	        }
        }
        return 0;
    }
}
