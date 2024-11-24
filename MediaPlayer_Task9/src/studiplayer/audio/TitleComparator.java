package studiplayer.audio;

import java.util.Comparator;

public class TitleComparator implements Comparator<AudioFile> {

	@Override
	public int compare(AudioFile o1, AudioFile o2) {
		char firstCharO1 = o1.getTitle().charAt(0);
	    char firstCharO2 = o2.getTitle().charAt(0);
	    
	    if (firstCharO1 < firstCharO2) {
	        return -1;
	    } else if (firstCharO1 > firstCharO2) {
	        return 1;
	    }
	    return 0;
	}
}
