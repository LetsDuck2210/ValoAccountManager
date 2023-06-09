package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {
	private File file;
	private Scanner s = null;
	public FileManager(File file) {
		this.file = file;
		try {
			s = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public FileManager(String filepath) {
		this(new File(filepath));
	}
	
	public ArrayList<String> readLines() {
		var lines = new ArrayList<String>();
		while(s.hasNextLine()) {
			lines.add(s.nextLine());
		}
		return lines;
	}
	
	public void writeLine(String text) {
		try {
			var writer = new BufferedWriter(new FileWriter(file, true));
			writer.write(text);
			writer.newLine();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
