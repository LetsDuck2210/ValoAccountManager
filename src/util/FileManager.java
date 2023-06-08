package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import valorant.Account;

public class FileManager {
	File file = new File("accounts.txt");
	Scanner s = null;
	public FileManager() {
		try {
			s = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/*
	 * returns an ArrayList of all Accounts in the txt file
	 */
	public ArrayList<Account> readAccounts() {
		var accounts = new ArrayList<Account>();
		while(s.hasNextLine()) {
			var attributes = readAttributes(s.nextLine());
//			accounts.add(new Account(attributes[0], attributes[1], attributes[2], attributes[3], attributes[4]));
		}
		return accounts;
	}
	private String[] readAttributes(String raw) {
		var attributes = new String[5];
		for(int i = 0; i < 5; i++) {
			if(i < 4) {
				attributes[i] = raw.substring(0, raw.indexOf('|'));
				raw = raw.substring(raw.indexOf('|') + 1);
			} else {
				attributes[i] = raw;
			}
		}
		return attributes;
	}
	public void writeAccounts(Account acc) {
		if(acc == null) return;
		try {
			var writer = new BufferedWriter(new FileWriter(file, true));
			writer.write(acc.toString());
			writer.newLine();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
