package studiplayer.audio;

import java.util.List;

public class ControllablePlayListIterator implements Iterator{

	private List<AudioFile> listOfSongs;
	private int index = -1;
	
	public ControllablePlayListIterator(List<AudioFile> list) {
		listOfSongs = list;
	}
	
	public ControllablePlayListIterator(List<AudioFile> list, String search, SortCriterion sort) {}
	
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
	
	public static void main(String[] args) {}
}
