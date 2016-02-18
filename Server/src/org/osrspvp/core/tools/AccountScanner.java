package org.osrspvp.core.tools;

import org.osrspvp.Config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AccountScanner {

	private static final int MAX_INVENTORY_ITEMS = 28;

	private static final int MAX_ITEM_AMOUNT = 1000000000;

	public static File directory = new File("./data/characters/");

	public static void main(String[] parameters) {
		File[] characterFiles = directory.listFiles();
		for (int index = 0; index < characterFiles.length; index++) {
			scanAccounts(characterFiles[index]);
		}
	}
	
	private static void flagAccount(File fileName) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("flaggedaccs.txt", true));
			writer.write(fileName.toString());
			writer.newLine();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void scanAccounts(File fileName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line = "";
			try {
				while ((line = reader.readLine()) != null) {
					for (int itemIndex = 0; itemIndex < MAX_INVENTORY_ITEMS; itemIndex++) {
						if (line.contains("character-item = " + itemIndex + "	")) {
							line = line.replaceAll("character-item = "
									+ itemIndex + "	", "");
							String[] line2 = line.split("	");
							if (Integer.parseInt(line2[1]) >= MAX_ITEM_AMOUNT) {
								System.out.println(fileName + " flagged.");
								System.out.println(Integer.parseInt(line2[1]));
								flagAccount(fileName);
							}
						}
					}
					for (int bankIndex = 0; bankIndex < Config.BANK_SIZE; bankIndex++) {
						if (line.contains("character-bank = " + bankIndex + "	")) {
							line = line.replaceAll("character-bank = "
									+ bankIndex + "	", "");
							String[] line2 = line.split("	");
							if (Integer.parseInt(line2[1]) >= MAX_ITEM_AMOUNT) { 
								System.out.println(fileName + " flagged.");
								System.out.println(Integer.parseInt(line2[1]));
								flagAccount(fileName);
							}
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
