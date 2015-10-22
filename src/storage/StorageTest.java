package storage;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class StorageTest {

	@Test
	public void testSetPath1() {
		boolean pass = true;
		Storage.setPath("/Users/hungngth/Downloads");
		File f = new File("/Users/hungngth/Downloads/TBAsave.txt");
		if (!f.exists()) {
			pass = false;
		}
		assertTrue("test whether savefile without name is created", pass);
	}

	@Test
	public void testsetPath4() {
		boolean pass = true;
		Storage.setPath("/Users/hungngth/Downloads/mysave.txt");
		File f = new File("/Users/hungngth/Downloads/mysave.txt");
		if (!f.exists()) {
			pass = false;
		}
		assertTrue("test whether savefile with name is created", pass);
	}

	@Test
	public void testsetPath2() {
		Storage.setPath("/Users/hungngth/Downloads");
		assertEquals("test correct storing of path", Storage.path, "/Users/hungngth/Downloads/TBAsave.txt");
	}

	@Test
	public void testsetPath3() {
		Storage.setPath("/Users/hungngth/Downloads/mysave.txt");
		try {
			FileReader fr = new FileReader("/Users/hungngth/Documents/workspace/TBA/main");
			BufferedReader br = new BufferedReader(fr);
			assertEquals("test correct writing to savedPath", "/Users/hungngth/Documents/workspace/TBA/main", br.readLine());
			br.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
	}




	@Test
	public void testWrite() {
		fail("Not yet implemented");
	}

	@Test
	public void testRead() {
		fail("Not yet implemented");
	}

}
