package edu.uta.cse.rugrat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


/**
 * This small code looks into JavaNCSS outputs to find out if there exists any class with more than 1000 methods.
 * INPUT: Path to the directory that has JavaNCSS output files
 * OUTPUT: If successful in finding such a class, prints in the console the file name and the line in file.
 * @author Ishtiauqe Hussain {ishtiaque.hussain@mavs.uta.edu}
 *
 */

public class JavaNCSSoutpurReader {
	public static void main(String[] args) {
		System.out.println("Starting ....");

		File folder = new File(args[0]);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				
//				try {
					BufferedReader reader = null;
					try {
						reader = new BufferedReader(new FileReader(listOfFiles[i]));
						String line = null;
						while((line = reader.readLine()) != null){ // Nr. NCSS Functions Classes Javadocs Class
							line = line.trim();
							String[] tok = line.split("\\s+");
							int val = -1;
							try{
								val = Integer.parseInt(tok[2]);
							}catch (NumberFormatException e){
								continue;
							}
							if(val >= 1000) //BINGO
								System.out.println(listOfFiles[i] +": "+ line);			
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		
			}			
		}
		System.out.println("We are done analyzing your code!!");
	}

}
