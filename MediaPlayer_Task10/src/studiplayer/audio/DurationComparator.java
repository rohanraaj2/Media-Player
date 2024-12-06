package studiplayer.audio;

import java.util.Comparator;

public class DurationComparator implements Comparator<AudioFile> {

	@Override
	public int compare(AudioFile o1, AudioFile o2) {
		long duration1 = o1 instanceof SampledFile ? ((SampledFile) o1).getDuration() : 0;
	    long duration2 = o2 instanceof SampledFile ? ((SampledFile) o2).getDuration() : 0;
	    
	    if (duration1 < duration2) {
	        return -1;
	    } else if (duration1 > duration2) {
	        return 1;
	    }
	    return 0;
	}
}
