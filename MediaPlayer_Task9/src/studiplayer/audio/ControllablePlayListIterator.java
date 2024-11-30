package studiplayer.audio;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ControllablePlayListIterator implements Iterator<AudioFile> {

	private List<AudioFile> listOfSongs;
	private int index = -1;
	
	public ControllablePlayListIterator(List<AudioFile> list) {
		listOfSongs = list;
	}
	
	public ControllablePlayListIterator(List<AudioFile> list, String search, SortCriterion sort) {
		List<AudioFile> iteratorList = new LinkedList<>();
		if (search == null || search.isEmpty()) {
			iteratorList.addAll(list);
		} else {
		    for (AudioFile file : list) {
		        if (checkForMatch(file, search)) {
		        	iteratorList.add(file);
		        }
		    }
		}
		if (sort != null) {
		    switch (sort) {
                case AUTHOR:
                	iteratorList.sort(new AuthorComparator());
                    break;
                case TITLE:
                	iteratorList.sort(new TitleComparator());
                    break;
                case ALBUM:
                	iteratorList.sort(new AlbumComparator());
                    break;
                case DURATION:
                	iteratorList.sort(new DurationComparator());
                    break;
                case DEFAULT:
                    break;
            }
		}
		listOfSongs = iteratorList;
	}
	
	private boolean checkForMatch(AudioFile file, String keyword) {
		if (file.getTitle().equals(keyword) || file.getAuthor().equals(keyword)) {
			return true;
		}
		if (file instanceof TaggedFile) {
			if (((TaggedFile) file).getAlbum().equals(keyword)) {
				return true;
			}
		}
		return false;
    }
	
	public AudioFile jumpToAudioFile(AudioFile file) {
		if (listOfSongs.contains(file)) {
			index = listOfSongs.indexOf(file);
			return file;
		}
		return null;
	}

	@Override
	public AudioFile next() {
		index += 1;
		return listOfSongs.get(index);
	}

	@Override
	public boolean hasNext() {
		if (index < listOfSongs.size() - 1) {
			return true;
		}
		return false;
	}
}
