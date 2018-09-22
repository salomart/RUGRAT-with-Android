package edu.uta.cse.rugrat;

/**
 * This class writes in the console the main build.xml for RUGRAT experiments (with FindBugs)
 * @author  ishtiaque.hussain@mavs.uta.edu (Ishtiaque Hussain)
 *
 */

public class BuildMasterAuto {

	public static void main(String []args){
		
		if(args.length != 3){
			System.out.println("USAGE: <StartIndex> <EndIndex> <DirectoryName>");
			System.exit(1);
		}
		
		int start = Integer.valueOf(args[0]);
		int end = Integer.valueOf(args[1]);
		String dir = args[2];
		
		String output = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n" +
		
		"<project name=\"RUGRAT root build file\" >\n\n" +
	    "<description>\n" +
	            "\tThis build file runs other build files to run the experiments overnight\n" +
	    "</description>\n\n";
		
		for(int i = start; i< end; i++){
		output += 
			"\t<target name=\"Expr" +i+"\">" + "\n"+
				"\t\t<ant dir=\""+dir+"/Expr"+i+"\" output=\"log.txt\" />"+"\n"+ 
		"\t</target>"+"\n\n";
		}
		
		output += "</project>";
		
		System.out.println(output);
		
//		String ant = "ant -k ";
//		
//		for(int i = 0; i< 369; i++){
//			ant += "Expr"+i+" ";
//		}
//		System.out.println(ant);
	}
}
