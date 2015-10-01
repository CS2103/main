// Hi Hung

package storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.google.gson.Gson;

import logic.Task;

public class Storage {

	private static Gson gson = new Gson();
	private static File todo = new File("saveFile.txt");

	public Storage() {
	}
	static File file;
	static Path path;

	public boolean newEntry(String taskName) {
		return true;
	}

	public static void write(ArrayList<Task> tasks){
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(todo));
			for(Task task: tasks) {
				String json = gson.toJson(task) + "\n";
				bw.write(json);
			}
			bw.close();
		}

		catch (IOException e) {
			e.printStackTrace();
		}

	}


	public static boolean createFile(String fileName) {
		file = new File(fileName);
		setPath(Paths.get(fileName));
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static void setPath(Path path) {
		Storage.path = path;
	}
	
	public static ArrayList<Task> read() {
		ArrayList<Task> tasks = new ArrayList<Task>();
		String line = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(todo));
			while((line = br.readLine()) != null) {
				tasks.add(gson.fromJson(line, Task.class));
			}
			br.close();
		}
		catch (FileNotFoundException e){
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return tasks;
	}
}
