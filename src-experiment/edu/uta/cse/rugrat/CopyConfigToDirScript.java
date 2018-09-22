package edu.uta.cse.rugrat;

/**
 * This class creates the build.xml (in console) that prepares the experiment.
 * + Creates a directory ExprXXX
 * + Copies the config.xml into the directory
 * + Copies the build1.xml (main config file to RUGRAT) into the directory
 *   
 * @author ishtiaque.hussain@mavs.uta.edu {Ishtiaque Hussain}
 *
 */
public class CopyConfigToDirScript {
	
	public static void main(String args[]){
		
		String output = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n";
		
		output += "<project name=\"project\" default=\"default\">\n" +		    

		   "\t<target name=\"default\"> \n";
		        
		   		
		for(int i= 0; i<202; i++){
			output += "\t\t<mkdir dir=\"Expr"+i+"\"/>"+"\n"+
	    	"\t\t<copy tofile=\"Expr"+i+"/config.xml\" file=\"config"+i+".xml\"/>"+"\n"+
	    	"\t\t<copy tofile=\"Expr"+i+"/build.xml\" file=\"build1.xml\"/>"+"\n"+
	    	"\t\t<delete file=\"config"+i+".xml\"/>\n\n";
		}
		
		output += "\t</target>"+"\n"+
			"</project>";
		
		System.out.println(output);
	}

}
