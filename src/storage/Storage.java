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
	private static File tempFile = new File("temp.json");
	private static String path;

	public Storage() {

	}

	public static void setPath(String path) {
		//File checkFile = new File(path);
		//if (checkFile.exists() && !checkFile.isDirectory()) {
			//System.out.println("File in used");
		//} else {
			Storage.path = path;
		//}
	}

	public static void write(ArrayList<Task> tasks){
		try {
			if (path == null) {
				setPath(tempFile.getAbsolutePath());
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
		ArrayList<Task> taskList = new ArrayList<Task>();
		String line = "";
		if (path == null)
			setPath(tempFile.getAbsolutePath());
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
