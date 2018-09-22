package edu.uta.cse.rugrat.result;

import java.io.File;
import java.util.ArrayList;

/*
 * args[0] =  Name of the experiment folder; e.g.,: "D:\Rugrat Experiments\New Set of Experiments"
 * Directory structure: New Set of Experiments -> TestRunX,.. -> 10KLOC, ...
 */

public class ResultProcessor {
	
	public static void main(String[] args){
		
		File dir = new File(args[0]);
		File[] listSubDir = dir.listFiles();
		
		System.out.println("FindBugs\t\tPMD\t\tJLint\t\tRandoop");
		System.out.println("Time\tWarnings ... ... \n -------------------------*********----------------------------");
		
		// get All the TestRunX directories
		
		ArrayList<String> testRunDirs = new ArrayList<String>();
		for(int i=0; i < listSubDir.length; i++){
			String path = listSubDir[i].getName(); 
			if(path.contains("TestRun")){
				testRunDirs.add(path);
			}
		}
		
		// get all the dir.s inside a TestRun dir.
		// D:\Rugrat Experiments\New Set of Experiments\TestRun1
		File testRunDir = new File(dir+"/"+testRunDirs.get(0));
		File [] dirsInTestRunDir = testRunDir.listFiles();
		
		ArrayList<String> LOCdirs = new ArrayList<String>();
		for(int i =0; i < dirsInTestRunDir.length; i++){
			String path = dirsInTestRunDir[i].getName();
			if(path.contains("LOC"))
				LOCdirs.add(path);				
		}	
		
		
		/*
		 * For each of the LOCs, compile result for different TestRuns
		 */
		
		for(int i = 0; i < LOCdirs.size(); i++){
			
			StringBuffer maxSummary = new StringBuffer();
			StringBuffer minSummary = new StringBuffer();
			
			for(int j = 0; j < testRunDirs.size(); j++){
				//D:\Rugrat Experiments\TestRun1\10KLOC
				String path = dir.getAbsolutePath()+"/"+testRunDirs.get(j)+"/"+LOCdirs.get(i);
			
							
			
				System.out.println(path);
				Context ctx = new Context(path);
				
				
				maxSummary.append(ctx.processMax("FindBugs") + "\t");
				maxSummary.append(ctx.processMax("PMD") + "\t");
				maxSummary.append(ctx.processMax("JLint") + "\t");
				maxSummary.append(ctx.processMax("Randoop") + "\t");
				maxSummary.append("\n");
				
				
				minSummary.append(ctx.processMin("FindBugs")+ "\t");
				minSummary.append(ctx.processMin("PMD")+ "\t");
				minSummary.append(ctx.processMin("JLint")+ "\t");
				minSummary.append(ctx.processMin("Randoop")+ "\t");
				minSummary.append("\n");
				
				
			}
			
			String xLOC = LOCdirs.get(i).toString();
			//Max Summary
			System.out.println("\n"+xLOC+"/max");
			System.out.println(maxSummary.toString());
			
			System.out.println("\n"+xLOC+"/min");
			System.out.println(minSummary.toString());			
			
		}
		
		System.out.println("Done :)");
	}
		
}


