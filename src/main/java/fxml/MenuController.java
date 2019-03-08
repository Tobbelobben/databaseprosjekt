package fxml;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class MenuController {
	
	@FXML
	public void toRegistration(ActionEvent event) throws IOException {
		Scene scene = ((Node) event.getSource()).getScene();
		scene.setRoot((Parent) FXMLLoader.load(getClass().getResource("Registration.fxml")));
	}
	
	@FXML
	public void toExercises(ActionEvent event) throws IOException {
		Scene scene = ((Node) event.getSource()).getScene();
		scene.setRoot((Parent) FXMLLoader.load(getClass().getResource("Exercises.fxml")));
	}
	
	@FXML
	public void toGroups(ActionEvent event) throws IOException {
		Scene scene = ((Node) event.getSource()).getScene();
		scene.setRoot((Parent) FXMLLoader.load(getClass().getResource("Groups.fxml")));
	}
	
	@FXML
	public void toMachines(ActionEvent event) throws IOException {
		Scene scene = ((Node) event.getSource()).getScene();
		scene.setRoot((Parent) FXMLLoader.load(getClass().getResource("Machines.fxml")));
	}
	
	@FXML
	public void toSessions(ActionEvent event) throws IOException {
		Scene scene = ((Node) event.getSource()).getScene();
		scene.setRoot((Parent) FXMLLoader.load(getClass().getResource("Sessions.fxml")));
	}

}
