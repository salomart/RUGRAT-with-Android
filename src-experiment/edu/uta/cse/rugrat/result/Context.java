package edu.uta.cse.rugrat.result;

import java.util.HashMap;

public class Context {
	
	private static HashMap<String, IToolStrategy> _strategy = new HashMap<String, IToolStrategy>() ;
	String path = "";
	
	public Context(String pathToDir){
		path = pathToDir;
		
		_strategy.put("FindBugs", new FindBugsTool(path));
		_strategy.put("PMD", new PMDTool(path));
		_strategy.put("JLint", new JLintTool(path));
		_strategy.put("Randoop", new RandoopTool(path));			
	}
	
	public String processMax(String tool){
		return _strategy.get(tool).processMax();
	}
	
	public String processMin(String tool){
		return _strategy.get(tool).processMin();
	}
	
	/* input format:
	 * [min]:[sec].[fraction of a sec] 
	 * E.g., : 4:33.688 ; 2.921
	 * 
	 */
	public static String convertToSec(String time){
		
		int 	min  		= 0;
		float 	sec	 		= 0;	
		int 	equivSec 	= 0;
		
		if(time.contains(":")){ // 4:33.688
			String[] tok 	= time.split(":");
			min				= Integer.valueOf(tok[0]);
			sec				= Float.valueOf(tok[1]);
		}else{ // 2.921
			sec 			= Float.valueOf(time);			
		}			
		
		equivSec = (min * 60)+ Math.round(sec);		
		
		return Integer.toString(equivSec);		
	}

}
