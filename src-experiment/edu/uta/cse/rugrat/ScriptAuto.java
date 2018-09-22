package edu.uta.cse.rugrat;

public class ScriptAuto {
	
	public static void main(String args[]){
		
		String output = "";
		
		for(int i= 0; i<166; i++){
			output += "<mkdir dir=\"Expr"+i+"\"/>"+"\n"+
	    	"<copy tofile=\"Expr"+i+"/config.xml\" file=\"config"+i+".xml\"/>"+"\n"+
	    	"<copy tofile=\"Expr"+i+"/build.xml\" file=\"build1.xml\"/>"+"\n"+
	    	"<delete file=\"config"+i+".xml\"/>\n\n";
		}
		
		
		System.out.println(output);
	}

}
