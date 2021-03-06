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

public class MachineController {
	
	@FXML TextField machine;
	@FXML TextArea area;
	
	@FXML public void seeExercises() {
		String s = "";
		try{
			ArrayList<HashMap<String,String>> maps = WorkoutManager.getMachineExercises(machine.getText());
			for(HashMap<String,String> map : maps) {
				s += map.get("exerciseName") + ": " + map.get("sets") + " sett, " + map.get("kilos") + " kilo \n"; 
			}
			area.setText(s);
		}
		catch(Exception e) {
			
		}
		
		
	}
	
	@FXML public void back(ActionEvent event) throws IOException {
		Scene scene = ((Node) event.getSource()).getScene();
		scene.setRoot((Parent) FXMLLoader.load(getClass().getResource("Menu.fxml")));
	}
	
}
