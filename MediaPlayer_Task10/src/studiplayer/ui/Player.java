package studiplayer.ui;

import java.io.File;
import java.net.URL;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
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
import studiplayer.audio.SortCriterion;

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
	private Label playListLabel;
	private Label currentSongLabel;
	private Label playTimeLabel;
	private ChoiceBox<SortCriterion> sortChoiceBox = new ChoiceBox<>();
	private TextField searchTextField = new TextField();
	private Button filterButton = new Button("display");
	private SongTable table;
	private PlayerThread playerThread;
	private TimerThread timerThread = null;
	private boolean songPaused = false;
	
	public Player() {}
	
	public void start(Stage stage) throws Exception {
		SortCriterion criterian = SortCriterion.AUTHOR;
		String path = PLAYLIST_DIRECTORY + "/playList.cert.m3u";
		if (!this.useCertPlayList) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select File To Load");
			File file = fileChooser.showOpenDialog(stage);
			if (file != null) {
				path = file.getPath();
			}
		}
		
		BorderPane mainPane = new BorderPane();
		stage.setTitle("APA Player");
		
		// Filter Box
		TitledPane filterBox = new TitledPane();
		filterBox.setText("Filter");
		
		GridPane filterFieldsBox = new GridPane();
		filterFieldsBox.setPadding(new Insets(5));
		filterFieldsBox.setHgap(20);
		filterFieldsBox.setVgap(5);
		
		Label searchLabel = new Label("Search text");
		filterFieldsBox.add(searchLabel, 0, 0);
		searchTextField.setPromptText("");
		filterFieldsBox.add(searchTextField, 1, 0);
	
		Label sortLabel = new Label("Sort by");
		filterFieldsBox.add(sortLabel, 0, 1);
		sortChoiceBox.getItems().addAll(SortCriterion.AUTHOR, SortCriterion.TITLE, SortCriterion.ALBUM, SortCriterion.DURATION);
		sortChoiceBox.setValue(criterian);
		sortChoiceBox.prefWidthProperty().bind(searchTextField.widthProperty());
		filterFieldsBox.add(sortChoiceBox, 1, 1);
	
		filterFieldsBox.add(filterButton, 2, 1);
		
		filterBox.setContent(filterFieldsBox);
		mainPane.setTop(filterBox);
		
		// Table
		setPlayList(path);
		table = new SongTable(playList);
		mainPane.setCenter(table);
		
		// Event handling using Lambda Expressions
		filterButton.setOnAction(e -> {
			playList.setSearch(searchTextField.getText());
			playList.setSortCriterion(sortChoiceBox.getValue());
			table.refreshSongs();
		});
		
		// Song Info
		GridPane songInfoBox = new GridPane();
		songInfoBox.setPadding(new Insets(5));
		songInfoBox.setHgap(10);
		songInfoBox.setVgap(10);
		
		playListLabel = new Label(path);
		songInfoBox.add(new Label("Playlist"), 0, 0);
		songInfoBox.add(playListLabel, 1, 0);

		AudioFile song = playList.currentAudioFile();
		String songName = song == null? NO_CURRENT_SONG: song.toString();
		currentSongLabel = new Label(songName);
		songInfoBox.add(new Label("Current Song"), 0, 1);
		songInfoBox.add(currentSongLabel, 1, 1);
		
		String songduration = song instanceof SampledFile ? ((SampledFile) song).formatDuration() : INITIAL_PLAY_TIME_LABEL;
		playTimeLabel = new Label(songduration);
		songInfoBox.add(new Label("Playtime"), 0, 2);
		songInfoBox.add(playTimeLabel, 1, 2);
		
		// PlayBack Buttons
		playButton = createButton("play.jpg");
		pauseButton = createButton("pause.jpg");
		stopButton = createButton("stop.jpg");
		nextButton = createButton("next.jpg");
		setButtonStates(false, true, true, false);

		HBox controlBox = new HBox(10, playButton, pauseButton, stopButton, nextButton);
		controlBox.setAlignment(Pos.CENTER);
		controlBox.setPadding(new Insets(10));

		VBox bottomBox = new VBox(0);
		bottomBox.getChildren().addAll(songInfoBox, controlBox);
		mainPane.setBottom(bottomBox);

		playButton.setOnAction(e -> {
			playCurrentSong();
		});
		
		pauseButton.setOnAction(e -> {
			pauseCurrentSong();
		});
		
		stopButton.setOnAction(e -> {
			stopCurrentSong();
		});
		
		nextButton.setOnAction(e -> {
			nextSong();
		});
		
		table.setRowSelectionHandler(e -> {
            stopCurrentSong();
            playList.jumpToAudioFile(table.getSelectionModel().getSelectedItem().getAudioFile());
            table.selectSong(table.getSelectionModel().getSelectedItem().getAudioFile());
        });
		
		Scene scene = new Scene(mainPane, 600, 400);
		
		stage.setOnCloseRequest(e -> {
		    threadTerminate(false);
		    Platform.exit();
		    System.exit(0);
		});

		stage.setScene(scene);
		stage.show();
	}
	
	public void setUseCertPlayList(boolean useCertPlayList) {
		this.useCertPlayList = useCertPlayList;
	}

	public void setPlayList(String pathname) {
		this.playList = new PlayList(pathname);
	}
	
	public void loadPlayList(String pathname) {
		if (pathname == null || pathname.isEmpty()) {
			try {
				this.playList.loadFromM3U(DEFAULT_PLAYLIST);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				this.playList.loadFromM3U(pathname);
			} catch (Exception e) {
				e.printStackTrace();
			}	
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
	
	private void playCurrentSong() {
		AudioFile currentSong = playList.currentAudioFile();
		if (songPaused) {
			songPaused = false;
			startThreads(true);
			currentSong.togglePause();
        } else {
        	startThreads(false);
        }
        table.selectSong(currentSong);
		setButtonStates(true, false, false, false);
		System.out.println("Playing " + currentSong);
		System.out.println("Filename is " + currentSong.getFilename());
	}

	private void pauseCurrentSong() {
		AudioFile currentSong = playList.currentAudioFile();
		if (songPaused) {
			songPaused = false;
			startThreads(true);
        } else {
        	songPaused = true;
            threadTerminate(true);
        }
		currentSong.togglePause();
		setButtonStates(true, false, false, false);
		System.out.println("Pausing " + currentSong);
		System.out.println("Filename is " + currentSong.getFilename());
	}

	private void stopCurrentSong() {
		threadTerminate(false);
		AudioFile currentSong = playList.currentAudioFile();
		currentSong.stop();
		setButtonStates(false, true, true, false);
        updateSongInfo(null);
		
		System.out.println("Stopping " + currentSong);
		System.out.println("Filename is " + currentSong.getFilename());
	}

	private void nextSong() {
		System.out.println("Switching to next audio file: stopped = false, paused = true");
		stopCurrentSong();
		playList.nextSong();
		setButtonStates(true, false, false, false);
		AudioFile currentSong = playList.currentAudioFile();
		updateSongInfo(currentSong);
		playCurrentSong();
		System.out.println("Switched to next audio file: stopped = false, paused = true");
	}

	private void setButtonStates(boolean playButtonState, boolean pauseButtonState, boolean stopButtonState, boolean nextButtonState) {
		playButton.setDisable(playButtonState);
		pauseButton.setDisable(pauseButtonState);
		nextButton.setDisable(nextButtonState);
		stopButton.setDisable(stopButtonState);
	}
	
	private void updateSongInfo(AudioFile af) {
		Platform.runLater(() -> {
			if (af == null) {
				currentSongLabel.setText(NO_CURRENT_SONG);
				playTimeLabel.setText(INITIAL_PLAY_TIME_LABEL);
            } else {
            	currentSongLabel.setText(af.getTitle());
            	playTimeLabel.setText(af.formatPosition());
			}
		});
	}
	
	public class PlayerThread extends Thread {
		private boolean stopped = false;
		public void terminate() {
			stopped = true;
		}
		
		@Override
		public void run() {
			while (!stopped) {
				AudioFile currentSong = playList.currentAudioFile();
				if (currentSong != null) {
					try {
						currentSong.play();
					} catch (NotPlayableException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public class TimerThread extends Thread {
		private boolean stopped = false;
		public void terminate() {
			stopped = true;
		}
		
		@Override
		public void run() {
			while (!stopped) {
				AudioFile currentSong = playList.currentAudioFile();
				if (currentSong != null) {
					updateSongInfo(playList.currentAudioFile());
		                try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
				}
			}
		}
	}
	
	private void startThreads(boolean onlyTimer) {
		if (timerThread == null || !timerThread.isAlive()) {
            timerThread = new TimerThread();
            timerThread.start();
        }

        if (!onlyTimer) {
            if (playerThread == null || !playerThread.isAlive()) {
                playerThread = new PlayerThread();
                playerThread.start();
            }
        }
    }

    private void threadTerminate(boolean onlyTimer) {
        if (!onlyTimer) {
            if (playerThread != null) {
                playerThread.terminate();
                playerThread = null;
            }
        }
        if (timerThread != null) {
            timerThread.terminate();
            timerThread = null;
        }
    }
    
	public static void main(String[] args) {
		launch();
	}
}
