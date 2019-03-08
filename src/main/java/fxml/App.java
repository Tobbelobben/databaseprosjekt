package fxml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;


public class App extends Application{

	@Override
	public void start(final Stage primaryStage) throws Exception {
		primaryStage.setTitle("Chef's Apprentice");
		primaryStage.setScene(new Scene((Parent) FXMLLoader.load(App.class.getResource("Menu.fxml"))));
		primaryStage.show();
	}

	public static void main(final String[] args) {
		App.launch(args);
	}
}
