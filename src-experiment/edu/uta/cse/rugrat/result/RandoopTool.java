package edu.uta.cse.rugrat.result;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class RandoopTool implements IToolStrategy {
	
	private String dirPath="";

	public RandoopTool(String path) {
		dirPath = path;

	}

	@Override
	public String processMax() {
		File file = new File(dirPath+"\\Randoop Build\\max\\maxTime.txt");
		try {
			FileReader freader = new FileReader(file);
			BufferedReader reader = new BufferedReader(freader);
			String line = "";
			String warnings = null;
			String time = "";
			
						
			while((line = reader.readLine())!= null){
			
				//[stopwatch] [time: 1.094 sec] 
				if(line.contains("stopwatch")){
					String[] tok = line.split(" ");
					time = tok[2];
				}
				// [java] Progress update: test inputs generated=2471, failing inputs=0		
				else if(line.contains("Progress update")){
					String [] tok = line.split(" ");
					warnings = tok[12];
					tok = warnings.split("=");
					warnings = tok[1];
				}
			}
			
			
			reader.close();
			return Context.convertToSec(time)+"\t"+warnings;
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return null;
	}

	@Override
	public String processMin() {
		File file = new File(dirPath+"\\Randoop Build\\min\\minTime.txt");
		try {
			FileReader freader = new FileReader(file);
			BufferedReader reader = new BufferedReader(freader);
			String line = "";
			String warnings = null;
			String time = "";
			
						
			while((line = reader.readLine())!= null){
			
				//[stopwatch] [time: 1.094 sec] 
				if(line.contains("stopwatch")){
					String[] tok = line.split(" ");
					time = tok[2];
				}
				// [java] Progress update: test inputs generated=2471, failing inputs=0		
				else if(line.contains("Progress update")){
					String [] tok = line.split(" ");
					warnings = tok[12];
					tok = warnings.split("=");
					warnings = tok[1];
				}
			}
			
			
			reader.close();
			return Context.convertToSec(time)+"\t"+warnings;
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return null;
	}

}
