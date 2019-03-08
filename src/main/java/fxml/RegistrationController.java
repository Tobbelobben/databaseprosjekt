package fxml;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import database.*;

public class RegistrationController {
	TextField machinefield, machinename, exercisename, sets, kilos, machineexp, ordexercisename, exercisedesc, exercises, form, prestasjon, note, duration;
	ArrayList<String> exerciseStrings = new ArrayList<>();
	
	public void registerMachine() {
		try {
			WorkoutManager.addMachine(machinefield.getText(), machineexp.getText());
		}
		catch(IllegalStateException e) {
			machinefield.setText("Machine already exists");
		}
	}
	
	public void registerOExercise() {
		try {
			WorkoutManager.addOrdinaryExercise(ordexercisename.getText(), exercisedesc.getText());
		}
		catch(IllegalStateException e) {
			ordexercisename.setText("Exercise already exists");
		}
	}
	
	public void registerMExercise() {
		try {
			WorkoutManager.addMachineExercise(machinefield.getText(), exercisename.getText(), Integer.parseInt(kilos.getText()), Integer.parseInt(sets.getText()));
		}
		catch(IllegalStateException e) {
			ordexercisename.setText("Exercise already exists");
		}
		catch(NumberFormatException e) {
			ordexercisename.setText("kilos and sets must be ints");
		}
		catch(IllegalArgumentException e) {
			ordexercisename.setText("Machine doesn't exist");
		}
		
	}
	
	public void addExerciseToSession() {
		if(!exerciseStrings.contains(exercises.getText())) {
			exerciseStrings.add(exercises.getText());
			return;
		}
		exercises.setText("exercise already added");
	}
	
	public void registerSession() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sqlDatetime = sdf.format(new Date()); // formater datetime til sql-format
		try {
			WorkoutManager.addExerciseSession(sqlDatetime, Integer.parseInt(duration.getText()), Integer.parseInt(form.getText()), Integer.parseInt(prestasjon.getText()), exerciseStrings, note.getText());
		}
		catch(NumberFormatException e) {
			note.setText("kilos and sets must be ints");
		}
	}
	
	@FXML
	public void back(ActionEvent event) throws IOException {
		Scene scene = ((Node) event.getSource()).getScene();
		scene.setRoot((Parent) FXMLLoader.load(getClass().getResource("Menu.fxml")));
	}
	
}
