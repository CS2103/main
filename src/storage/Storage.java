package storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;

import logic.Task;

public class Storage {

	private static Gson gson = new Gson();
	private static File tempFile = new File("tempFile.json");
	private static File savedPath = new File("savedPath.txt");
	private static String path;
	private static BufferedReader br;

	public Storage() {

	}

	public static void setPath(String path) {
		try {
			FileWriter fw = new FileWriter(savedPath.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(path);
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		Storage.path = path;
	}

	public static void write(ArrayList<Task> tasks){
		try {
			if (path == null) {
				setPath(savedPath.getAbsolutePath());
			}
			File file = new File(path);
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			for (Task task: tasks) {
				String json = gson.toJson(task) + "\n";
				bw.write(json);
			}
			bw.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Task> read() {
		try {
			FileReader fr = new FileReader(savedPath);
			br = new BufferedReader(fr);
			Storage.path = br.readLine();
		} 
		catch (FileNotFoundException e) {
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList<Task> taskList = new ArrayList<Task>();
		String line = "";
		if (path == null) {
			setPath(tempFile.getAbsolutePath());
		}
		try {
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);
			while((line = br.readLine()) != null) {
				taskList.add(gson.fromJson(line, Task.class));
			}
			br.close();
		} 
		catch (FileNotFoundException e) {
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return taskList;
	}
}
