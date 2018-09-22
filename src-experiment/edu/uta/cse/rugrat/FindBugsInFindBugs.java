package edu.uta.cse.rugrat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * This class will open the ExprXX/ subfolder and look for 'log' file and count
 * the 'findbugs' keyword in it. If the count differs from the normal (8 times)
 * then there might be some problem with FindBugs and it will report that file.
 * 
 * PRECONDITION: MUST USE inverted commas {"..."}. There should not be any '\' at the end. E.g.:"D:\Rugrat Experiments\100KLOC\100KLOC-202"
 * 
 * @author ishtiaque.hussain@mavs.uta.edu {Ishtiaque Hussain}
 * 
 */

public class FindBugsInFindBugs {

		
	public static void main(String args[]) {
			
		if (args.length != 1) {
			System.out.println("Please provide the directory name!!");
			System.exit(1);
		}
		
		
		
		String strFile = args[0]; // parent directory
		File parentDir = new File(strFile);
		String[] subDirs = parentDir.list();
		
		boolean ERROR;

		int totalDirs = subDirs.length; // total subdirectory or files inside the directory
		System.out.println("Error in "+ strFile);
		
		for (int i = 0; i < totalDirs; i++) {
			
			int ErrorType = 0;
			String line = "";
			int countFindBugsKW = 0;
			int NORMAL_COUNT = 8;
			ERROR = false;
			String errorDir = subDirs[i];
			 
			try {
				
				if(!subDirs[i].contains("Expr")) // we don't want to go inside any directory other than 'Expr'
					continue;
				
				BufferedReader br = new BufferedReader(new FileReader(strFile+"\\"+ subDirs[i]+"\\log.txt"));
				
				while ((line = br.readLine()) != null) {
					if (line.contains("[findbugs]")) 
						countFindBugsKW++;	
					if(line.contains("[findbugs] Warnings generated:")){
						ErrorType = -1; // No error, so ERROR is not set to 'true'
//						break;
					}
									
					if(line.contains("[java] java.lang.OutOfMemoryError: Java heap space")){
						ErrorType = 1;
						ERROR = true;
						break;
					}
					if(line.contains("code too large")){
						ErrorType = 2;
						ERROR = true;
						break;
					}
					if(line.contains("[findbugs] Out of memory")){
						ErrorType = 3;
						ERROR = true;
						break;
					}
					if(line.contains("[findbugs] Timeout: killed the sub-process")){
						ErrorType = 4;
						ERROR = true;
						break;
					}
					if(line.contains("[java] Insufficent number of classes.")){
						ErrorType = 5;
						ERROR = true;
						break;
					}
					if(line.contains("too many constants")){
						ErrorType = 6;
						ERROR = true;
						break;
					}
					if(line.contains("java.io.IOException: There is not enough space on the disk")){
						ErrorType = 7;
						ERROR = true;
						break;
					}
					if(line.contains("Failed to copy")){
						ErrorType = 8;
						ERROR = true;
						break;
					}
				}
				
				String errorMsg ="";
//				if (ERROR) {
				if((countFindBugsKW != NORMAL_COUNT)|| ERROR){
//					String errorDir = subDirs[i];					
					switch (ErrorType){
//					    case -1: errorMsg = "This should never get printed!!";break; 
						case 0: errorMsg = "***INTERESTING ERROR***."; break;
						case 1: errorMsg = "RUGRAT:\t OutOfMemoryError occured."; break;
						case 2: errorMsg = "RUGRAT:\t Code too large."; break;
						case 3: errorMsg = "FindBugs:\t OutOfMemoryError.";break;
						case 4: errorMsg = "FindBugs:\t Timeout"; break;
						case 5: errorMsg = "ACTS:\t ACTS didn't replace '*"; break;
						case 6: errorMsg = "RUGRAT:\t Too many constants"; break;
						case 7: errorMsg = "Disk space error!"; break;
						case 8: errorMsg = "Could not copy due to disk space limitation."; break;
						default: errorMsg = "This should not print!!";
					}
					System.out.println( errorDir + "\t-- "+errorMsg);
				}
//				else
//					System.out.println("No Errors!");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				if(e instanceof FileNotFoundException)
					System.out.println(errorDir + "\t-- log file not generated!!");
				else
					e.printStackTrace();
			}
		}

	}

}
