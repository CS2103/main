

import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class MainController implements Initializable {
	
	// Main Scene items
    @FXML // fx:id="clear"
    private Button clear; // Value injected by FXMLLoader
    @FXML // fx:id="consoleInput"
    private TextField consoleInput; // Value injected by FXMLLoader
    @FXML
    private TextFlow textOut;

    // Task Scene items
    @FXML
    private TextField checkTaskName;
    @FXML
    private TextField checkStart;
    @FXML
    private TextArea enterDescription;
    @FXML
    private TextField checkEnd;
    @FXML
    private TextField checkPriority;
    
    
    enum COMMAND_TYPE {
    	ADD_LINE,
    	DISPLAY_ALL,
    	DELETE_ALL,
    	CLEAR_ALL,
    	EXIT,
    	INVALID,
    	EDIT,
    	SEARCH
    }
  
    public String readConsoleInput(ActionEvent event) {
    	String input = consoleInput.getText().trim();
    	System.out.println("Successfully read input: " + input); //Debug
    	//text = new Text(command);
    	consoleInput.clear();
    	JavaFXMain.input = input;
    	System.err.format(event.toString());
    	JavaFXMain.handleCommand(input);
    	return input;
    }
    
    public Task createTask() {
    	Task newTask = new Task(JavaFXMain.taskName, JavaFXMain.taskDescription, JavaFXMain.priority);
		return newTask;
    }
    
    public void confirmTaskDetails () {
    	JavaFXMain.taskName = checkTaskName.getText().trim();
    	JavaFXMain.start = checkStart.getText().trim();
    	JavaFXMain.end = checkEnd.getText().trim();
    	JavaFXMain.priority = checkPriority.getText().trim();
    	Task newTask = createTask();
    	
    	newTask.setStartString(JavaFXMain.start);
    	newTask.setEndString(JavaFXMain.end);
    	JavaFXMain.taskDescription = enterDescription.getText().trim();
    	JavaFXMain.tasks_array.add(newTask);
    	JavaFXMain.showMainScene(JavaFXMain.primaryStage);
    	showCustom("asd");
    	System.out.println(newTask.details());
    	JavaFXMain.clearTaskVariables();
    	
    }
    
    public void exitProgram(ActionEvent event) {
    	System.exit(0);
    }
    public void showTextToUser(Text text) {
    	textOut.getChildren().addAll(text);
    }
    
    public void showCustom(String strstr) {
    	Text text = new Text(strstr);
    	textOut.getChildren().addAll(text);
    }
    protected void toConsole(Object str) {
    	String str1 = str.toString();
    	System.out.println(str1);
    }
    
    protected void clearTextOut() {
    	textOut.getChildren().clear();
    }  
    
    public void updateTaskView(String string) {
    	checkTaskName.setText(string);
    	checkStart.setText(JavaFXMain.start);
    	checkEnd.setText(JavaFXMain.end);
    	checkPriority.setText(JavaFXMain.priority);
    } 
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub	
	}
    
}