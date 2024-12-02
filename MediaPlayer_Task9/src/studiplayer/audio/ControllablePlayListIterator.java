package studiplayer.audio;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ControllablePlayListIterator implements Iterator<AudioFile> {

	private List<AudioFile> listOfSongs = new LinkedList<AudioFile>();
	private int index = 0;
	
	public ControllablePlayListIterator(List<AudioFile> list) {
		listOfSongs = list;
	}
	
	public ControllablePlayListIterator(List<AudioFile> list, String search, SortCriterion sort) {
		searchAndSort(list, search, sort);
	}
	
	public void searchAndSort(List<AudioFile> list, String search, SortCriterion sort) {
		index = 0;
		List<AudioFile> iteratorList = new LinkedList<AudioFile>();
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
	    int fileIndex = listOfSongs.indexOf(file);
	    if (fileIndex != -1) {
	        if (fileIndex == index) {
	            return listOfSongs.get(index);
	        } else if (fileIndex > index) {
	            while (index < fileIndex) {
	                next();
	            }
	        } else {
	            index = 0;
	            while (index <= fileIndex) {
	                next();
	            }
	        }
	        return listOfSongs.get(fileIndex);
	    }
	    return null;
	}

	@Override
	public AudioFile next() {
		if (this.listOfSongs.isEmpty()) {
			return null;
		}
		AudioFile song = this.listOfSongs.get(index);
		if (hasNext()) {
			index += 1;
		} else {
			index = 0;
		}
		return song;
	}

	@Override
	public boolean hasNext() {
		if (index < listOfSongs.size()) {
			return true;
		}
		return false;
	}
	
	public List<AudioFile> getListOfSongs() {
		return this.listOfSongs;
	}

	public int getIndex() {
		return this.index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
}
