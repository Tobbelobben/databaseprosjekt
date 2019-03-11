package fxml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import database.WorkoutManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class GroupController {
	@FXML TextField group1, group2, group3, exercise;
	@FXML TextArea area;
	
	@FXML public void addGroup() {
		try {
			System.out.println("trying to add group");
			WorkoutManager.addGroup(group1.getText());
			group1.setText("group added");
		}
		catch(IllegalStateException e) {
			group1.setText("Group already exists");
		}
	}
	
	@FXML public void addExerciseToGroup() {
		try {
			WorkoutManager.addExerciseInGroup(group2.getText(), exercise.getText());
			group2.setText("exercise added to group");
		}
		catch(IllegalStateException e) {
			group1.setText("Group does not exists");
		}
		catch(IllegalArgumentException e) {
			group1.setText("Exercise does not exist");
		}
	}
	
	@FXML public void showGroup() {
		try{
			ArrayList<HashMap<String,String>> maps = WorkoutManager.getExercisesInGroup(group3.getText());
			String exes = "";
			for(HashMap<String,String> map : maps) {
				exes += map.get("exerciseName") + "\n";
			}
			area.setText(exes);
		}
		catch(IllegalArgumentException e) {
			group3.setText("Group does not exist!");
		}
	}
	
	@FXML public void back(ActionEvent event) throws IOException {
		Scene scene = ((Node) event.getSource()).getScene();
		scene.setRoot((Parent) FXMLLoader.load(getClass().getResource("Menu.fxml")));
	}
}
