import java.io.File;
import java.nio.file.InvalidPathException;

public class AudioFile {
	
	protected String pathname;
	protected String filename;
	protected String author;
	protected String title;
	
	public AudioFile() {
		
	}
	
	public AudioFile (String path) {

		parsePathname(path);
		
		File file = new File(pathname);
		
		if (!file.canRead()) {
			
			throw new RuntimeException("Invalid path"); 
		}
		parseFilename(filename);
	}
	
	private boolean isWindows() {
		 return System.getProperty("os.name").toLowerCase()
		 .indexOf("win") >= 0;
		}
	
	public void parsePathname(String path) {
	    String temp = path.trim(); 

	    String slashes_changed;
	    String slashes_repetition_fixed;
	    int lastSlashIndex;

	    if ((temp.indexOf('/') == -1) && (temp.indexOf('\\') == -1)) {
	         
	        pathname = temp.replace("‿", "");
	        filename = pathname.substring(0);
	    } else {
	         
	        slashes_repetition_fixed = temp.replaceAll("/+", "/");
	        if (isWindows()) {
	            slashes_changed = temp.replace("/", "\\");
	            slashes_repetition_fixed = slashes_changed.replaceAll("\\\\\\\\+", "\\\\");
	            lastSlashIndex = slashes_repetition_fixed.lastIndexOf("\\");
	        } else {
	            slashes_changed = temp.replace("\\", "/");
	            slashes_repetition_fixed = slashes_changed.replaceAll("/+", "/");
	            lastSlashIndex = slashes_repetition_fixed.lastIndexOf("/");
	        }

	        pathname = slashes_repetition_fixed;
	        if (lastSlashIndex != -1) {
	            filename = pathname.substring(lastSlashIndex + 1);
	        } else {
	            filename = pathname.substring(0);
	        }
	    }
	}
	
	public void parseFilename(String filename) {
	    int hyphenIndex = filename.indexOf(" - ");
	    int extensionIndex = filename.lastIndexOf('.');

	    if (filename.isEmpty()) {
	        author = "";
	        title = "";
	        return; 
	    }


	    if (hyphenIndex != -1) {
	      
	        author = filename.substring(0, hyphenIndex).trim();
	        
	        if (extensionIndex != -1) {
	            title = filename.substring(hyphenIndex + 3, extensionIndex).trim();
	        } else {
	             
	            title = filename.substring(hyphenIndex + 3).trim();
	        }
	    } else {
	         
	        author = "";
	        
	        if (extensionIndex != -1) {
	            title = filename.substring(0, extensionIndex).trim();
	        } else {
	             
	            title = filename.trim();
	        }
	    }
	}
	
	public String getPathname() {
		return pathname;
	}

	public String getFilename() {
		return filename;
	}

	public String getAuthor() {
		return author;
	}

	public String getTitle() {
		return title;
	}
	
	public String toString() {
		if (getAuthor() == "") {
			return title;
		}
		else {
//			if(title != null) {
//				return String.format(author + " - " + title);
//			}
//			if ((author != null) && (title != null)){
				return (String.format("%s - %s",author, title)).trim();
//			}
//			else {
				
//			}
//			return "";
		}
	}
	
	public void play() {
		
	}
	
	public void togglePause() {
		
	}
	
	public void stop() {
		
	}
	
	public String formatDuration() {
		return null;
	}
	
	public String formatPosition() {
		return null;
	}

	public static void main(String[] args) {
		
		}
}