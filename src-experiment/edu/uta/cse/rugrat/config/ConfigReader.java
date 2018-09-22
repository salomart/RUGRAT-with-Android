package edu.uta.cse.rugrat.config;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 * @author Ishtiaque Hussain {ishtiaque.hussain@mavs.uta.edu}
 * 
 * This is to read and summarize the random configuration files
 * args[0] = "D:\Rugrat Experiments\New Set of Experiments"
 *
 */


public class ConfigReader {
	
	// parameters to look for.
	static String [] configOptions ={"noOfInterfaces","maxNoOfMethodsPerInterface","noOfClasses",
			"maxNoOfMethodsPerClass","maxNoOfClassFields","minNoOfClassFields", "minNoOfParametersPerMethod"};
	
	// LOCs that we consider
	static String[] LOCs = {"10KLOC", "50KLOC", "100KLOC","500KLOC", "1MLOC", "2.5MLOC", "5MLOC"};
	
	
	static HashMap<String,String> configOp = new HashMap<String,String>();
	
	
	public static void main(String []args){
		
		//Init
		configOp.put("noOfInterfaces", "#I");
		configOp.put("maxNoOfMethodsPerInterface", "MPI");
		configOp.put("noOfClasses", "#C");
		configOp.put("maxNoOfMethodsPerClass", "MPC");
		configOp.put("maxNoOfClassFields", "CF");
		configOp.put("minNoOfClassFields", "mCF");
		configOp.put("minNoOfParametersPerMethod", "mPM");
		
		
		
		//print meaning of the notations
		
		Iterator it = configOp.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry pairs = (Map.Entry) it.next();
			System.out.println(pairs.getValue()+ "\t= " + pairs.getKey() );
		}
		
		
		// args[0] = Experiment directory
		File dir = new File(args[0]);
		
		// TestRunX
		File[] listOfTestRun = dir.listFiles();
		
		
		
		//get the header
		File headerFile = new File(dir+"/TestRun1/10KLOC/Expr/config.xml");
		String header = "";
		try{
			Document config = null;
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			config = docBuilder.parse(headerFile);
			header = getHeader(config);
			
			
			}catch(Exception e){
				e.printStackTrace();
			}
		
		
				
		for (int i = 0; i < LOCs.length; i++) {
			StringBuilder summary = new StringBuilder();
			for (File f : listOfTestRun) {
				if (f.getName().contains("TestRun")&& f.isDirectory()) {
//					System.out.println(f.getName());
					
					File xmlFile = new File(f.getAbsolutePath() + "/" + LOCs[i]+"/Expr/config.xml");
					
					// if the file does not exist, continue the loop (ignore)
					if(!xmlFile.exists())
						continue;
					
					try{
						Document config = null;
						DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
						DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
						
						config = docBuilder.parse(xmlFile);
						summary.append(getMetrics(config)+"\n");
						
						
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				}
			
			System.out.println(LOCs[i]);
			System.out.println(header+"\n"+summary);
			}
	}

		
				
		
		
	private static String getHeader(Document config){
		
		Node root = config.getDocumentElement();
		NodeList children = root.getChildNodes();
		
		StringBuilder header = new StringBuilder();
		
		int totalElements = children.getLength();
		
		for(int i =0; i < totalElements; i++){
			Node node = children.item(i);
			String name = node.getNodeName();
			
			if(configOp.containsKey(name)){
				header.append("\t"+configOp.get(name));
			}
		}	
		
		return header.toString();			
	}
		
		

	private static String getMetrics(Document config) {
		Node root = config.getDocumentElement();
		NodeList children = root.getChildNodes();
		
		int totalElements = children.getLength();
		StringBuilder value = new StringBuilder();
		
		for(int i =0; i < totalElements; i++){
			Node node = children.item(i);
			String name = node.getNodeName();
			
			if(configOp.containsKey(name)){
				value.append("\t"+node.getTextContent());				
			}		
		}
		
		return value.toString();
	}
		
		
}


	
	
	





