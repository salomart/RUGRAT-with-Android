package edu.uta.cse.rugrat.result;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class JLintTool implements IToolStrategy {
	
	private String dirPath = "";

	public JLintTool(String path) {
		dirPath = path;

	}

	@Override
	public String processMax() {
		
		File file = new File(dirPath+"\\JLint Build\\max\\maxTime.txt");
		try {
			FileReader freader = new FileReader(file);
			BufferedReader reader = new BufferedReader(freader);
			String line = "";
			String warnings = null;
			String time = "";
			
			long lineCount = 0;
			
			while((line = reader.readLine())!= null){
				lineCount++;
				//[stopwatch] [time: 1.094 sec] 
				if(line.contains("stopwatch")){
					String[] tok = line.split(" ");
					time = tok[2];
				}
				// [exec] Verification completed: 308 reported messages.				
				else if(line.contains("Verification completed")){
					String [] tok = line.split(" ");
					warnings = tok[8]; // there are lots of spaces at beginning.
				}
				
			}
			if(warnings == null)
				warnings = Long.toString(lineCount - 3);
			
			reader.close();
			return Context.convertToSec(time)+"\t"+warnings;
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return null;
	}

	@Override
	public String processMin() {
		File file = new File(dirPath+"\\JLint Build\\min\\minTime.txt");
		try {
			FileReader freader = new FileReader(file);
			BufferedReader reader = new BufferedReader(freader);
			String line = "";
			String warnings = null;
			String time = "";
			
			long lineCount = 0;
			
			while((line = reader.readLine())!= null){
				lineCount++;
				//[stopwatch] [time: 1.094 sec] 
				if(line.contains("stopwatch")){
					String[] tok = line.split(" ");
					time = tok[2];
				}
				// [exec] Verification completed: 308 reported messages.				
				else if(line.contains("Verification completed")){
					String [] tok = line.split(" ");
					warnings = tok[8]; // there are lots of spaces at beginning.
				}
				
			}
			if(warnings == null)
				warnings = Long.toString(lineCount - 3);
			
			reader.close();
			return Context.convertToSec(time)+"\t"+warnings;
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return null;
	}

}
