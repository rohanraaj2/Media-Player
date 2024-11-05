
public class AudioFile {
	
	private String pathname;
	private String filename;
	private String author;
	private String title;
	
	public AudioFile() {
		
	}
	
	public AudioFile (String path) {
		parsePathname(path);
		parseFilename(filename);
	}

	private boolean isWindows() {
		 return System.getProperty("os.name").toLowerCase()
		 .indexOf("win") >= 0;
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



	
	public void parsePathname(String path) {
	    String temp = path.trim(); 

	    String slashes_changed;
	    String slashes_repetition_fixed;
	    int lastSlashIndex;

	    if ((temp.indexOf('/') == -1) && (temp.indexOf('\\') == -1)) {
	         
	         
	        pathname = temp.replace("‿", "").trim();
	        filename = pathname.equals("-") ? "-" : pathname;
	    } 
	    else {
	        if (isWindows()) {
	            slashes_changed = temp.replace("/", "\\").trim();
	            slashes_repetition_fixed = slashes_changed.replaceAll("\\\\\\\\+", "\\\\");
	            lastSlashIndex = slashes_repetition_fixed.lastIndexOf("\\");
	        } 
	        else {
	            slashes_changed = temp.replace("\\", "/").trim();
	            slashes_repetition_fixed = slashes_changed.replaceAll("/+", "/");
	            if (slashes_repetition_fixed.length() >= 2 && Character.isLetter(slashes_repetition_fixed.charAt(0))
	                    && slashes_repetition_fixed.charAt(1) == ':') {
	            	slashes_repetition_fixed = "/" + slashes_repetition_fixed.charAt(0) + slashes_repetition_fixed.substring(2);
	            }
	            lastSlashIndex = slashes_repetition_fixed.lastIndexOf("/");
	        }

	         
	        pathname = slashes_repetition_fixed.trim();
	        if (lastSlashIndex != -1) {
	            filename = pathname.substring(lastSlashIndex + 1).trim();
	        } else {
	            filename = pathname.equals("-") ? "-" : pathname.trim();
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
			return author + " - " + title;
		}
	}

	public static void main(String[] args) {
		}
}