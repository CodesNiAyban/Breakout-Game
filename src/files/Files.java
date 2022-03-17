package files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Files {
	
	private static String filePath = getDefaultDirectory() + "/BrickBreaker/";
	public static String LEVELPATH = getDefaultDirectory() + "/BrickBreaker/Levels.txt";
	 
	public Files() {
		
	}
	public static void init() {
		createDir(filePath);
		createFile(LEVELPATH);
	}
	
	public static boolean[] readFile(String filePath) {
		boolean[] lockedLevels = new boolean[Level.MAX_LEVEL+1];
		File file = new File(filePath);
		if (file.exists()) {
			Scanner scanner = null;
			try {
				scanner = new Scanner(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			int line = 0;
			while (scanner.hasNextBoolean()) {
				lockedLevels[line] = scanner.nextBoolean();
				line++;
			}
			scanner.close();
			return lockedLevels;
		} else {
			createFile(filePath);
			return lockedLevels;
		}
		
	}
	


	public static void SaveProgress(boolean[] scores) {
		deleteFile(filePath + "Levels.txt");
		createFile(filePath + "Levels.txt");
		writeFile(new File(filePath + "Levels.txt"), scores);
	}

	public static void createFile(String filePath) {
			File path = new File(filePath);
			if (!path.exists()) {
				try {
					path.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				boolean[] lockedLevels = new boolean[Level.MAX_LEVEL+1];
				for(int i = 1; i < lockedLevels.length; i++) {
					lockedLevels[i] = true;
				}
				lockedLevels[0] = false;
				writeFile(path, lockedLevels);
		}
	}

	public static void createDir(String filePath) {
		File path = new File(filePath);
		if (!path.exists()) {
			path.mkdir();
		}
	}

	public static void writeFile(File file, boolean[] lockedLevels) {
		FileWriter writer;
		try {
			writer = new FileWriter(file);
			for (int i = 0; i < lockedLevels.length; i++) {
				writer.write(lockedLevels[i] + "\n");
			}
			System.out.println();
			writer.close();
		} catch (IOException e) {
		}
	}

	public static void deleteFile(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
	}

	public static String getDefaultDirectory() {
		String OS = System.getProperty("os.name").toUpperCase();
		if (OS.contains("WIN")) {
			return System.getenv("APPDATA");
		}
		return System.getProperty("user.home");
	}
}
