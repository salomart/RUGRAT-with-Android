package edu.uta.cse.rugrat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * @author Ishtiaque Hussain {ishtiaque.hussain@mavs.uta.edu}
 * 
 *         This class lists the names of the classes for Randoop. Input: Path to
 *         all *.java files (e.g., D:\Rugrat Experiments\DIT-Example
 *         Programs\10KLOC\Expr\TestPrograms\com\accenture\lab\carfast\test)
 *         Output (In the console): List of classes with full name. E.g.,
 *         "com.accenture.lab.carfast.test.TenKLOC3.java"
 * 
 */

public class ListOfFilesForRandoop {
	public static void main(String[] args) {

		File folder = new File(args[0]);
		File[] listOfFiles = folder.listFiles();

		try {
			String fileName = args[1] + File.separator + "MyListOfClasses.txt";
			FileWriter fwriter = new FileWriter(fileName);
			BufferedWriter out = new BufferedWriter(fwriter);

			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					String str = listOfFiles[i].getName();
					if (str.contains(".java")) {
						str = str.replaceAll(".java", "");
//						System.out.println("com.accenture.lab.carfast.test."+ str);
						out.write("com.accenture.lab.carfast.test."+ str +"\n");
					}
				} else if (listOfFiles[i].isDirectory()) {
					System.out.println("Directory " + listOfFiles[i].getName());
				}
			}
			
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
