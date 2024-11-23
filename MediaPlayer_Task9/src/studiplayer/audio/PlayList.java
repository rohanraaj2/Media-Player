package studiplayer.audio;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class PlayList {
	
	private LinkedList<AudioFile> listOfAudioFiles;
	private int current = 0;

	public PlayList() {
		listOfAudioFiles = new LinkedList<>();
	}

	public PlayList(String m3uPathname) {
		listOfAudioFiles = new LinkedList<>();
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
		if (current > listOfAudioFiles.size() - 1) {
			return null;
		}
		return this.listOfAudioFiles.get(current);
	}
	
	public void nextSong() {
		if (current > this.size() - 2) { // 1 extra decrement because the index in the list starts at 0
			current = 0;
		}
		else {
			current += 1;
		}
	}
	
	public void loadFromM3U(String pathname) {
		List<String> lines = new ArrayList<>();
		Scanner scanner = null;
		current = 0;
		listOfAudioFiles.clear();
		
		try {
			scanner = new Scanner(new File(pathname));
			int lineNo = 1;
			
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.isBlank() || line.charAt(0) == '#') {
					continue;
				}
				else {
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
	
	public List<AudioFile> getList(){
		return listOfAudioFiles;
	}
	
	public int getCurrent() {
		return current;
	}
	
	public void setCurrent(int current) {
		this.current = current;
	}
	
	public static void main(String[] args) {}

}
