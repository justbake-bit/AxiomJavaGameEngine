package com.axiom.gameengine.Utils;

import java.io.*;

public class FileLoader {
	
	public static String loadFileAsString(String path) {
		String fileString = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String line;
			while ((line = reader.readLine()) != null) {
				fileString += line + "\r\n";
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileString;
	}

}
