package edu.uta.cse.rugrat.result;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FindBugsTool implements IToolStrategy {
	
	private String dirPath ="";

	public FindBugsTool(String path) {
		// dirPath = D:\Rugrat Experiments\TestRun1\5MLOC
		dirPath = path;
		
	}

	@Override
	public String processMax() {
		
		File file = new File(dirPath+"\\FindBugs Build\\max\\maxTime.txt");
		try {
			FileReader freader = new FileReader(file);
			BufferedReader reader = new BufferedReader(freader);
			String line = "";
			String warnings = "";
			String time = "";
			
			while((line = reader.readLine())!= null){
				// [findbugs] Warnings generated: 481
				if(line.contains("Warnings")){
					String[] tok = line.split(" ");
					warnings = tok[4];
				}
				// [stopwatch] [time: 18.469 sec]
				else if(line.contains("stopwatch")){
					String[] tok = line.split(" ");
					time = tok[2];
				}				
			}
			reader.close();
//			return time+"\t"+warnings;
			return Context.convertToSec(time)+"\t"+warnings;
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return null;
	}

	@Override
	public String processMin() {
		File file = new File(dirPath+"\\FindBugs Build\\min\\minTime.txt");
		try {
			FileReader freader = new FileReader(file);
			BufferedReader reader = new BufferedReader(freader);
			String line = "";
			String warnings = "";
			String time = "";
			
			while((line = reader.readLine())!= null){
				// [findbugs] Warnings generated: 481
				if(line.contains("Warnings")){
					String[] tok = line.split(" ");
					warnings = tok[4];
				}
				// [stopwatch] [time: 18.469 sec]
				else if(line.contains("stopwatch")){
					String[] tok = line.split(" ");
					time = tok[2];
				}				
			}
			reader.close();
//			return time+"\t"+warnings;
			return Context.convertToSec(time)+"\t"+warnings;
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return null;
	}
	
	
}
