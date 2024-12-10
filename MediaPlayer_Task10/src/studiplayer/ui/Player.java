package studiplayer.ui;

import java.io.File;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import studiplayer.audio.NotPlayableException;
import studiplayer.audio.PlayList;

public class Player extends Application {
	
	public static final String DEFAULT_PLAYLIST = "playlists/DefaultPlayList.m3u";
	private String PLAYLIST_DIRECTORY;
	private String INITIAL_PLAY_TIME_LABEL;
	private String NO_CURRENT_SONG;
	private PlayList playList;
	private boolean useCertPlayList = false;
	private Button playButton = new Button("play");
	private Button pauseButton = new Button("pause");
	private Button stopButton = new Button("stop");
	private Button nextButton = new Button("next");
	private Label playListLabel = new Label("Playlist");
	private Label playTimeLabel = new Label("Current Song");
	private Label currentSongLabel = new Label("Playtime");
	private ChoiceBox sortChoiceBox = new ChoiceBox();
	private TextField searchTextField = new TextField();
	private Button filterButton = new Button("display");
	
	public Player() {}
	
	public void start(Stage stage) {
		TableView<String> table = new TableView<String>();
		String path = "playList.cert.m3u";
		String fileName = "etc";
//		useCertPlayList = true;
		BorderPane mainPane = new BorderPane();
		stage.setTitle("Player");
		
		Label searchLabel = new Label("Search text");
		HBox searchBox = new HBox(10);
		searchBox.getChildren().addAll(searchLabel, searchTextField);
        
		Label sortLabel = new Label("Sort by");
		HBox sortBox = new HBox(10);
		sortChoiceBox.setValue("AUTHOR");
        sortBox.getChildren().addAll(sortLabel, sortChoiceBox, filterButton);
        
		VBox filterBox = new VBox(10);
		filterBox.getChildren().addAll(searchBox, sortBox);
		
		mainPane.setTop(filterBox);
		
		TableColumn artist = new TableColumn("Artist");
        TableColumn title = new TableColumn("Title");
        TableColumn album = new TableColumn("Album");
        TableColumn duration = new TableColumn("Duration");
        
        table.getColumns().addAll(artist, title, album, duration);
		
		if (!this.useCertPlayList) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select File To Load");
			File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
//            	System.out.println(file.getPath());
            	path = file.getPath();
            	fileName = file.getName();
            	
            }
			setPlayList(path);
		}
		
		VBox songInfoBox = new VBox(10);
		
		HBox playListBox = new HBox(10);
		Label pathLabel = new Label(path);
		playListBox.getChildren().addAll(playListLabel, pathLabel);
		
		HBox currentSongBox = new HBox(10);
		Label songNameLabel = new Label(fileName);
		currentSongBox.getChildren().addAll(currentSongLabel, songNameLabel);
		
		HBox playTimeBox = new HBox(10);
		Label durationLabel = new Label(path);
		playTimeBox.getChildren().addAll(playTimeLabel, durationLabel);
		
		songInfoBox.getChildren().addAll(table, playListBox, currentSongBox, playTimeBox);
		mainPane.setCenter(songInfoBox);
		
		FlowPane buttonPane = new FlowPane();
		buttonPane.getChildren().addAll(playButton, pauseButton, stopButton, nextButton);
//		buttonPane.setHgap(10);
		buttonPane.setAlignment(Pos.CENTER);
//		FlowPane.setMargin(tooBigButton, new Insets(10, 10, 10, 10));
//		FlowPane.setMargin(tooSmallButton, new Insets(10, 10, 10, 10));
//		FlowPane.setMargin(nailedItButton, new Insets(10, 10, 10, 10));
		mainPane.setBottom(buttonPane);
		
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
	
	public void createButton(String iconfile) {
		
	}

	
	public static void main(String[] args) {
		launch();
	}
}
