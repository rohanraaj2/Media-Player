package studiplayer.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Player extends Application {
	
	public Player() {}
	
	public void start(Stage stage) {
		BorderPane mainPane = new BorderPane();
		stage.setTitle("Player");
		
//		VBox explainBox = new VBox();
//		explainBox.getChildren().addAll(explain1, explain2, explain3);
//		mainPane.setTop(explainBox);
		
//		// Tip in Center area
//		FlowPane centerPane = new FlowPane();
//		Label tipLabel = new Label();
		
//		// Set background color using CSS Style
//		centerPane.setStyle("-fx-background-color: lightblue");
//		// Bold print
//		tipLabel.setStyle("-fx-font-weight: bold");
//		guess(tipLabel);
//		centerPane.getChildren().add(tipLabel);
//		FlowPane.setMargin(tipLabel, new Insets(10, 10, 10, 10));
//		mainPane.setCenter(centerPane);
		
//		// User feedback in Bottom area
//		FlowPane buttonPane = new FlowPane();
//		Button tooBigButton = new Button("too big");
//		Button tooSmallButton = new Button("too small");
//		Button nailedItButton = new Button("you nailed it!");
		
		// Event handling using Lambda Expressions
//		tooBigButton.setOnAction(e -> {
//			upper = tip;
//			guess(tipLabel);
//		});
//		tooSmallButton.setOnAction(e -> {
//			lower = tip;
//			guess(tipLabel);
//		});
//		nailedItButton.setOnAction(e -> {
//			tipLabel.setText("Finally!");
//			tooBigButton.setDisable(true);
//			tooSmallButton.setDisable(true);
//			nailedItButton.setDisable(true);
//		});
		
//		buttonPane.getChildren().addAll(tooBigButton, tooSmallButton, nailedItButton);
//		buttonPane.setHgap(10);
//		buttonPane.setAlignment(Pos.CENTER);
//		FlowPane.setMargin(tooBigButton, new Insets(10, 10, 10, 10));
//		FlowPane.setMargin(tooSmallButton, new Insets(10, 10, 10, 10));
//		FlowPane.setMargin(nailedItButton, new Insets(10, 10, 10, 10));
//		mainPane.setBottom(buttonPane);
		
		Scene scene = new Scene(mainPane, 600, 40);
		stage.setScene(scene);
		stage.show();		
	}

	public static void main(String[] args) {
		launch();
	}
}
