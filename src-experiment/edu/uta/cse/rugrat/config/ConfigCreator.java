package edu.uta.cse.rugrat.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * 	args[0] = Dir path to input folder that holds 10KLOC.txt etc. files.
	args[1] = Dir path to TestRunX
	
	for each TestRunX directory, for all XXLOC subfolders it will create config.xml
	file in 'TestRunX\XXLOC\Expr'.
	
	Pre: TestRunX\XXLOC\Expr should already be in place.
 * 
 * @author Ishtiaque Hussain {ishtiaque.hussain@mavs.uta.edu}
 *
 */

public class ConfigCreator {
	

	
	

	
	public static void main(String [] args){
		
		try{
			// path to dir that holds 10KLOC.txt,..., 1MLOC.txt files (config range files) 
			File dir = new File(args[0]);
			File []listOfFiles = dir.listFiles();
			
			File TestRunDir = new File(args[1]);
			File [] listOfDir = TestRunDir.listFiles();
			
			for(File testDirFolder : listOfDir){
				if(testDirFolder.isDirectory() && testDirFolder.getName().contains("TestRun")){
					
					String pathToTestRunX = testDirFolder.getAbsolutePath();
					
					//DEBUG:
					System.out.println("In "+ pathToTestRunX);
					
					for(File f: listOfFiles){
						if(f.isFile() && f.getName().contains("LOC")){ // check to conform to XXLOC.txt files only
							String file = f.getName().replaceFirst(".txt","" ); // get rid of '.txt'
							
							//DEBUG:
							System.out.println("Working for "+ file);
							
							FileReader freader = new FileReader(f);
							BufferedReader reader = new BufferedReader(freader);
							
												
							String line="";
							StringBuffer output = new StringBuffer();
							output.append("<RUGRAT>");
							
							Context ctx = new Context();
							ctx.init();
							
							//DEBUG: 
//							System.out.println("\t going into WHILE");
							
							while((line = reader.readLine())!= null){
								output.append(ctx.getValue(line)+"\n");
							}
							
							output.append("</RUGRAT>");
							
							//DEBUG:
//							System.out.println("\t Came out of While");
							
							// Write in TestRunX\XXLOC\Expr\config.xml
							File fileOut = new File(pathToTestRunX+ "\\"+ file + "\\Expr\\config.xml");
							FileWriter fwriter = new FileWriter(fileOut);
							BufferedWriter writer = new BufferedWriter(fwriter);
							
							//DEBUG:
//							System.out.println("\t Written to file");
							
//							System.out.println(output);
							writer.write(output.toString());
//							System.out.println(++count);
							
							reader.close();
							writer.close();					
						}
					}
					
				}
			}
			
			
			int count = 0;
			
			
			
			
			
			System.out.println("Done!!");
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	
	}
	
	

}
