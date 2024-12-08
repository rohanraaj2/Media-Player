package studiplayer.ui;

import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import studiplayer.audio.NotPlayableException;
import studiplayer.audio.PlayList;

public class Player extends Application {
	private PlayList playList;
	private boolean useCertPlayList = false;
	public static final String DEFAULT_PLAYLIST = "playlists/DefaultPlayList.m3u";
	
	public Player() {}
	
	public void start(Stage stage) {
		BorderPane mainPane = new BorderPane();
		stage.setTitle("Player");
		
		if (!this.useCertPlayList) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select File To Load");
			File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
            	setPlayList(file.getPath());
            }
		} else {
			setPlayList("playList.cert.m3u");
		}
		
		Scene scene = new Scene(mainPane, 600, 40);
		stage.setScene(scene);
		stage.show();
	}

	public void setUseCertPlayList(boolean value) {
		this.useCertPlayList = value;
	}
	
	public void setPlayList(String pathname) {
		this.playList = new PlayList(pathname);
	}
	
	public void loadPlayList(String pathname) throws NotPlayableException {
		if (pathname == null || pathname.isEmpty()) {
			this.playList.loadFromM3U(DEFAULT_PLAYLIST);
		} else {
			this.playList.loadFromM3U(pathname);	
		}
	}
	
	public static void main(String[] args) {
		launch();
	}
}
