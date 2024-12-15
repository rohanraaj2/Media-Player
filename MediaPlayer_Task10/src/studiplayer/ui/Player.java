package studiplayer.ui;

import java.io.File;
import java.net.URL;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import studiplayer.audio.AudioFile;
import studiplayer.audio.NotPlayableException;
import studiplayer.audio.PlayList;
import studiplayer.audio.SampledFile;

public class Player extends Application {
	
	public static final String DEFAULT_PLAYLIST = "playlists/DefaultPlayList.m3u";
	private static final String PLAYLIST_DIRECTORY = "playlists";
	private static final String INITIAL_PLAY_TIME_LABEL = "00:00";
	private static final String NO_CURRENT_SONG = "-";
	private PlayList playList;
	private boolean useCertPlayList = false;
	private Button playButton = new Button("play");
	private Button pauseButton = new Button("pause");
	private Button stopButton = new Button("stop");
	private Button nextButton = new Button("next");
	private Label playListLabel = new Label("Playlist");
	private Label currentSongLabel = new Label("Current Song");
	private Label playTimeLabel = new Label("Playtime");
	private ChoiceBox<String> sortChoiceBox = new ChoiceBox<>();
	private TextField searchTextField = new TextField();
	private Button filterButton = new Button("display");
	
	public Player() {}
	
	public void start(Stage stage) {
		String path = PLAYLIST_DIRECTORY + "playList.cert.m3u";
		if (!this.useCertPlayList) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select File To Load");
			File file = fileChooser.showOpenDialog(stage);
			if (file != null) {
				path = file.getPath();
			}
		}
		
		BorderPane mainPane = new BorderPane();
		stage.setTitle("Player");
		
		// Filter Box
		GridPane positionBoxTop = new GridPane();
		positionBoxTop.setPadding(new Insets(5));
		positionBoxTop.setHgap(20);
		positionBoxTop.setVgap(5);
		
		TitledPane filterHeader = new TitledPane();
		filterHeader.setText("Filter");
		filterHeader.setCollapsible(true);
	
		Label searchLabel = new Label("Search text");
		searchTextField.setPromptText("");
		positionBoxTop.add(searchLabel, 0, 0);
		positionBoxTop.add(searchTextField, 1, 0);
	
		Label sortLabel = new Label("Sort by");
		sortChoiceBox.getItems().addAll("AUTHOR", "TITLE", "ALBUM", "DURATION");
		sortChoiceBox.setValue("AUTHOR");
		sortChoiceBox.prefWidthProperty().bind(searchTextField.widthProperty());
		positionBoxTop.add(sortLabel, 0, 1);
		positionBoxTop.add(sortChoiceBox, 1, 1);
	
		positionBoxTop.add(filterButton, 2, 1);
		
		VBox filterBox = new VBox(5);
		filterBox.getChildren().addAll(filterHeader, positionBoxTop);
		mainPane.setTop(filterBox);
		
		// Table
		TableView<String> table = new TableView<>();
		TableColumn<String, String> artist = new TableColumn<>("Artist");
		TableColumn<String, String> title = new TableColumn<>("Title");
		TableColumn<String, String> album = new TableColumn<>("Album");
		TableColumn<String, String> duration = new TableColumn<>("Duration");
        
        table.getColumns().addAll(artist, title, album, duration);
		
        // Song Info
		VBox songInfoBox = new VBox(0);
		
		GridPane positionBoxBottom = new GridPane();
		positionBoxBottom.setPadding(new Insets(5));
		positionBoxBottom.setHgap(10);
		positionBoxBottom.setVgap(10);
		
		setPlayList(path);
		Label pathLabel = new Label(path);
		positionBoxBottom.add(playListLabel, 0, 0);
		positionBoxBottom.add(pathLabel, 1, 0);

		AudioFile song = playList.currentAudioFile();
		String songName = song == null? NO_CURRENT_SONG: song.toString();
		Label songNameLabel = new Label(songName);
		positionBoxBottom.add(currentSongLabel, 0, 1);
		positionBoxBottom.add(songNameLabel, 1, 1);
		
		String songduration = song instanceof SampledFile ? ((SampledFile) song).formatDuration() : INITIAL_PLAY_TIME_LABEL;
		Label durationLabel = new Label(songduration);
		positionBoxBottom.add(playTimeLabel, 0, 2);
		positionBoxBottom.add(durationLabel, 1, 2);
	
		songInfoBox.getChildren().addAll(table, positionBoxBottom);
		mainPane.setCenter(songInfoBox);
		
		// PlayBack Buttons
		playButton = createButton("play.jpg");
		pauseButton = createButton("pause.jpg");
		stopButton = createButton("stop.jpg");
		nextButton = createButton("next.jpg");

		HBox controlBox = new HBox(10, playButton, pauseButton, stopButton, nextButton);
		controlBox.setAlignment(Pos.CENTER);
		controlBox.setPadding(new Insets(10));

		mainPane.setBottom(controlBox);

		Scene scene = new Scene(mainPane, 600, 400);
		stage.setScene(scene);
		stage.show();
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
	
	private Button createButton(String iconfile) {
		Button button = null;
		try {
			URL url = getClass().getResource("/icons/" + iconfile);
			Image icon = new Image(url.toString());
			ImageView imageView = new ImageView(icon);
			imageView.setFitHeight(20);
			imageView.setFitWidth(20);
			button = new Button("", imageView);
			button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			button.setStyle("-fx-background-color: #fff;");
		} catch (Exception e) {
			System.out.println("Image " + "icons/" + iconfile + " not found!");
			System.exit(-1);
		}
		return button;
	}

	public static void main(String[] args) {
		launch();
	}
}
