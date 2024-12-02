package studiplayer.audio;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Iterator;
import java.lang.Iterable;

public class PlayList implements Iterable<AudioFile> {
	
	private List<AudioFile> listOfAudioFiles = new LinkedList<>();
	private String search;
	private SortCriterion sortCriterion = SortCriterion.DEFAULT;
	private ControllablePlayListIterator iterator = new ControllablePlayListIterator(listOfAudioFiles);

	public PlayList() {}

	public PlayList(String m3uPathname) {
		loadFromM3U(m3uPathname);
	}
	
	public void add(AudioFile file) {
		listOfAudioFiles.add(file);
	}
	
	public void remove(AudioFile file) {
		listOfAudioFiles.remove(file);
	}

	public int size() {
		return listOfAudioFiles.size();	
	}
	
	public AudioFile currentAudioFile() {
		List<AudioFile> list = iterator.getListOfSongs();
		if(iterator.getIndex() >= list.size()) {
			iterator.setIndex(0);
		}
		if (listOfAudioFiles.size() == 0) {
			return null;
		} else {
			if (iterator.getIndex() < 0) {
				iterator.setIndex(0);
			}
			return iterator.getListOfSongs().get(iterator.getIndex());
		}
	}
	
	public void nextSong() {
		iterator.next();
	}
	
	public void loadFromM3U(String pathname) {
		List<String> lines = new ArrayList<>();
		Scanner scanner = null;
		listOfAudioFiles.clear();
		
		try {
			scanner = new Scanner(new File(pathname));
			int lineNo = 1;
			
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.isBlank() || line.charAt(0) == '#') {
					continue;
				} else {
					AudioFile audioFile = AudioFileFactory.createAudioFile(line);
					add(audioFile);	
				}
				String lineWithNumber = String.format("%03d: %s", lineNo++, line);
				lines.add(lineWithNumber);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				System.out.println("File " + pathname + " read!");
				scanner.close();	
			} catch (Exception e) {
				// ignore; probably because file could not be opened
			}
		}
	}
	
	public void saveAsM3U(String pathname) {
		FileWriter writer = null;
		String sep = System.getProperty("line.separator");
		
		try {
			writer = new FileWriter(pathname);
			
			List<String> lines = new ArrayList<>();
			for (AudioFile file : listOfAudioFiles) {
				lines.add(file.getPathname());
			}
			for (String line : lines) {
				writer.write(line + sep);
			}
		} catch (IOException e) {
			throw new RuntimeException("Unable to write file " + pathname + "!");
		} finally {
			try {
				System.out.println("File " + pathname + " written!");
				writer.close();
			} catch (Exception e) {
				// ignore; probably because file could not be opened	
			}
		}
	}
	
	public List<AudioFile> getList() {
		return listOfAudioFiles;
	}
	
	public SortCriterion getSortCriterion() {
		return sortCriterion;
	}

	public void setSortCriterion(SortCriterion sortCriterion) {
		this.sortCriterion = sortCriterion;
		iterator.searchAndSort(listOfAudioFiles, search, sortCriterion);
	}
	
	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
		iterator.searchAndSort(listOfAudioFiles, search, sortCriterion);
	}
	
	public Iterator<AudioFile> iterator() {
		return new ControllablePlayListIterator(listOfAudioFiles, search, sortCriterion);
	}
	
	public void jumpToAudioFile(AudioFile audiofile) {
		iterator.jumpToAudioFile(audiofile);
	}
}
