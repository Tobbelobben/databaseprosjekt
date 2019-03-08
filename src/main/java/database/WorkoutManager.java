package database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class WorkoutManager {
	
	public static int addMachine(String name, String explanation) {
		if(!machineNameExists(name)) {
			String update = "INSERT INTO Machine(machineName, explanation) VALUES ('" + name + "','" + explanation + "')";
			return DatabaseManager.sendUpdate(update);
		}
		return 0;
	}
	
	public static boolean machineNameExists(String name) {
		String query = "SELECT machineID FROM Machine WHERE machineName = '" + name + "'";
		return DatabaseManager.sendQuery(query).size()>0;
	}
	
	public static boolean exerciseNameExists(String name) {
		String query = "SELECT exerciseID FROM Exercise WHERE exerciseName = '" + name + "'";
		return DatabaseManager.sendQuery(query).size()>0;
	}
	
	public static int getMachineID(String machineName) {
		String query = "SELECT machineID FROM Machine WHERE machineName = '" + machineName + "'";
		ArrayList<HashMap<String,String>> maps = DatabaseManager.sendQuery(query);
		if(maps.size()>1) {throw new IllegalStateException("Several machines with same name");}
		else if(maps.size()==0) {throw new IllegalArgumentException("No machine with that name");}
		return Integer.parseInt(maps.get(0).get("machineID"));
	}
	
	public static int getExerciseID(String exerciseName) {
		String query = "SELECT exerciseID FROM Exercise WHERE exerciseName = '" + exerciseName + "'";
		ArrayList<HashMap<String,String>> maps = DatabaseManager.sendQuery(query);
		if(maps.size()>1) {throw new IllegalStateException("Several exercises with same name");}
		else if(maps.size()==0) {throw new IllegalArgumentException("No exercise with that name");}
		return Integer.parseInt(maps.get(0).get("exerciseID"));
	}
	
	public static int addMachineExercise(String machineName, String exerciseName, int kilos, int sets) {
		if(exerciseNameExists(exerciseName)) {throw new IllegalStateException("Exercise name already exists");}
		if(!machineNameExists(machineName)) {throw new IllegalArgumentException("Machine doesn't exist!");}
		int machineID = getMachineID(machineName);
		String update = "INSERT INTO Exercise(exerciseName) VALUES('" + exerciseName + "')";
		DatabaseManager.sendUpdate(update);
		int exerciseID = getExerciseID(exerciseName);
		update = "INSERT INTO MachineExercise VALUES (" + exerciseID + "," + machineID + "," + kilos + "," + sets +")";
		return DatabaseManager.sendUpdate(update);
	}
	
	public static int addOrdinaryExercise(String exerciseName, String description) {
		if(exerciseNameExists(exerciseName)) {throw new IllegalStateException("Exercise name already exists");}
		String update = "INSERT INTO Exercise(exerciseName) VALUES('" + exerciseName + "')";
		DatabaseManager.sendUpdate(update);
		int exerciseID = getExerciseID(exerciseName);
		update = "INSERT INTO OrdinaryExercise VALUES (" + exerciseID + ",'" + description + "')";
		return DatabaseManager.sendUpdate(update);
	}
	
	public static boolean groupNameExists(String name) {
		String query = "SELECT groupID FROM ExerciseGroup WHERE groupName = '" + name + "'";
		return DatabaseManager.sendQuery(query).size()>0;
	}
	
	public static int getGroupID(String groupName) {
		String query = "SELECT groupID FROM ExerciseGroup WHERE groupName = '" + groupName + "'";
		ArrayList<HashMap<String,String>> maps = DatabaseManager.sendQuery(query);
		if(maps.size()>1) {throw new IllegalStateException("Several groups with same name");}
		else if(maps.size()==0) {throw new IllegalArgumentException("No group with that name");}
		return Integer.parseInt(maps.get(0).get("groupID"));
	}
	
	public static int addGroup(String groupName) {
		if(groupNameExists(groupName)) {throw new IllegalStateException("Group name already exists");}
		String update = "INSERT INTO ExerciseGroup(groupName) VALUES('" + groupName + "')";
		return DatabaseManager.sendUpdate(update);
	}
	
	public static int addExerciseInGroup(String groupName, String exerciseName) {
		if(!groupNameExists(groupName)) {throw new IllegalArgumentException("Group doesn't exist!");}
		if(!exerciseNameExists(exerciseName)) {throw new IllegalArgumentException("Exercise doesn't exist!");}
		int groupID = getGroupID(groupName);
		int exerciseID = getExerciseID(exerciseName);
		String update = "INSERT INTO GroupContainsExercise VALUES(" + groupID + "," + exerciseID + ")";
		return DatabaseManager.sendUpdate(update);
	}
	
	
	public static ArrayList<HashMap<String, String>> getNLastExerciseSessions(int n) {
		// Henter ut og returnerer de n siste trenings�ktene 
		String query = "SELECT * "
				+ "FROM ExerciseSession as E INNER JOIN Note on (E.sessionID = Note.sessionID) "
				+ "ORDER BY E.origin DESC "
				+ "LIMIT " + n; 
		ArrayList<HashMap<String,String>> sessions = DatabaseManager.sendQuery(query);
		if(sessions.size() == 0) {throw new IllegalStateException("No exercise sessions registered");}
		return sessions;
	}
	
	public static int addExerciseToSession(int sessionID, int exerciseID) {
		String update = "INSERT INTO ExerciseInSession (sessionID, exerciseID) "
				+ "VALUES(" + sessionID + "," + exerciseID + ")";
		
		return DatabaseManager.sendUpdate(update);
	}
	
	public static int getSessionID(String datetime) {
		String query = "SELECT sessionID FROM ExerciseSession WHERE origin = '" + datetime + "'";
		ArrayList<HashMap<String,String>> maps = DatabaseManager.sendQuery(query);
		if(maps.size()>1) {throw new IllegalStateException("Several duplicate sessions");}
		else if(maps.size()==0) {throw new IllegalArgumentException("No session with that date");}
		return Integer.parseInt(maps.get(0).get("sessionID"));
	}
	
	public static int addExerciseSession(String datetime, int duration, int form, int performance, List<String> exercises, String noteText) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String sqlDatetime = sdf.format(datetime); // formater datetime til sql-format
		String update = "INSERT INTO ExerciseSession (origin, duration, form, performance) "
				+ "VALUES('" + datetime + "'," + duration + "," + form + "," + performance + ")";
		int result = DatabaseManager.sendUpdate(update);
		int sessionID = getSessionID(datetime);
		if(noteText != null || noteText == "") {
			addExerciseNote(sessionID, noteText);
		}
		
		
		for(String exercise : exercises) {
			int exerciseID = getExerciseID(exercise);
			addExerciseToSession(sessionID, exerciseID);
		}
		
		return result;
	}
	
	public static boolean noteExists(int sessionID) {
		String query = "SELECT sessionID FROM Note WHERE sessionID = " + sessionID;
		return DatabaseManager.sendQuery(query).size()>0;
	}
	
	public static int addExerciseNote(int sessionID, String noteText) {
		System.out.println(sessionID);
		if(noteExists(sessionID)) {throw new IllegalStateException("Session already have a note");}
		String update = "INSERT INTO Note (sessionID, noteText) VALUES("+sessionID+",'"+ noteText + "')";
		return DatabaseManager.sendUpdate(update);
	}
	
	
	public static void main(String[] args) {
		//System.out.println(addOrdinaryExercise("Hangups","L�ft hele kroppen opp og ned"));
//		Date date = new Date();
//		int result = addExerciseSession(date, 60, 3, 7);
		List<String> exercises = new ArrayList<>();
		
		addExerciseSession("2019-12-01 12:12:12", 4, 8, 9, exercises, "onfeonf");
	}
}
