package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class FileManager {
	private File file;

	public FileManager(File file) {
		this.file = file;
	}

	public FileManager(String filepath) {
		this(new File(filepath));
	}

	public ArrayList<String> readLines() {
		var lines = new ArrayList<String>();
		Scanner s = null;
		try {
			s = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (s.hasNextLine()) {
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

	public void deleteLines(ArrayList<Integer> lines) {
        File tempFile = new File("temp.txt");
        
        try {
        	BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            int lineIndex = 0;
            while ((currentLine = reader.readLine()) != null) {
                if (!lines.contains(lineIndex)) {
                    writer.write(currentLine);
                    writer.newLine();
                }
                lineIndex++;
            }
            
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (file.delete()) {
            if (!tempFile.renameTo(file)) {
                System.err.println("Could not rename temp file to original file name.");
            }
        } else {
            System.err.println("Could not delete original file.");
        }
    }
	
	public void deleteLine(int line) {
		var lines = new ArrayList<Integer>();
		lines.add(line);
		deleteLines(lines);
	}
	
	public void deleteLine(String text) {
		var lines = readLines();
		var linesToBeDeleted = new ArrayList<Integer>();
		int currentLine = 0;
		for(var line : lines) {
			if(line.equals(text)) linesToBeDeleted.add(currentLine);
			currentLine++;
		}
		deleteLines(linesToBeDeleted);
	}
	
	public void sort(Comparator<? super String> c) {
		File tempFile = new File("temp.txt");
        ArrayList<String> lines = readLines();
        lines.sort(c);
 		
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            
            for(var line : lines) {
            	writer.write(line);
            	writer.newLine();
            }
            
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (file.delete()) {
            if (!tempFile.renameTo(file)) {
                System.err.println("Could not rename temp file to original file name.");
            }
        } else {
            System.err.println("Could not delete original file.");
        }
	}
}
