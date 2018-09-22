package edu.uta.cse.rugrat.result;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class PMDTool implements IToolStrategy {
	
	private String dirPath ="";

	public PMDTool(String path) {
		dirPath = path;
	}

	@Override
	public String processMax() {
		try {
			
			/* Two separate files for warnings and time */
			
			//Warnings
			File file = new File(dirPath+"\\PMD Build\\max\\pmd_report.txt");
			
			FileReader freader = new FileReader(file);
			BufferedReader reader = new BufferedReader(freader);
			
			String warnings = null;
			String time = "";
			
			long lineCount = 0;
			
			while(reader.readLine()!= null)
				lineCount++;
						
			
			warnings = Long.toString(lineCount - 1);			
			reader.close();
			
			//Time.
			file = new File(dirPath+"\\PMD Build\\max\\maxTime.txt");
			freader = new FileReader(file);
			reader = new BufferedReader(freader);
			
			String line = "";
			while((line = reader.readLine()) != null){
				//[stopwatch] [time: 5.922 sec]
				if(line.contains("stopwatch")){
					String [] tok = line.split(" ");
					time = tok[2];
				}
			}
			
			
			return Context.convertToSec(time)+"\t"+warnings;
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return null;
	}

	@Override
	public String processMin() {
		try {
			
			/* Two separate files for warnings and time */
			
			//Warnings
			File file = new File(dirPath+"\\PMD Build\\min\\pmd_report.txt");
			
			FileReader freader = new FileReader(file);
			BufferedReader reader = new BufferedReader(freader);
			
			String warnings = null;
			String time = "";
			
			long lineCount = 0;
			
			while(reader.readLine()!= null)
				lineCount++;
						
			
			warnings = Long.toString(lineCount - 1);			
			reader.close();
			
			//Time.
			file = new File(dirPath+"\\PMD Build\\min\\minTime.txt");
			freader = new FileReader(file);
			reader = new BufferedReader(freader);
			
			String line = "";
			while((line = reader.readLine()) != null){
				//[stopwatch] [time: 5.922 sec]
				if(line.contains("stopwatch")){
					String [] tok = line.split(" ");
					time = tok[2];
				}
			}
			
			return Context.convertToSec(time)+"\t"+warnings;
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return null;
	}

}
