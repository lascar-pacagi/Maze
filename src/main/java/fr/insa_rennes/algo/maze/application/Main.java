package fr.insa_rennes.algo.maze.application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
        primaryStage.setTitle("Maze");
        primaryStage.setResizable(true);
        primaryStage.centerOnScreen();
		primaryStage.show();
	}
}

