package fxml;

import java.util.ArrayList;
import java.util.HashMap;

import database.WorkoutManager;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class GroupController {
	TextField group1, group2, group3, exercise;
	TextArea area;
	
	public void addGroup() {
		try {
			WorkoutManager.addGroup(group1.getText());
		}
		catch(IllegalStateException e) {
			group1.setText("Group already exists");
		}
	}
	
	public void addExerciesToGroup() {
		try {
			WorkoutManager.addExerciseInGroup(group2.getText(), exercise.getText());
		}
		catch(IllegalStateException e) {
			group1.setText("Group does not exists");
		}
		catch(IllegalArgumentException e) {
			group1.setText("Exercise does not exist");
		}
	}
	
	public void showGroup() {
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
}
