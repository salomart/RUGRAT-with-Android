package edu.uta.cse.rugrat.result;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Summarizes JavaNCSS output for each LOC category in the following format:
 * NOC	NOM	TLOC	CLOC	MLOC	ACC
 * 
 * @author ishtiaque.hussain@mavs.uta.edu (Ishtiaque Hussain)
 *
 */

public class JavaNCSSOutputProcessor {
	
	/*
	 * args[0] =  Name of the experiment folder; e.g.,: "D:\Rugrat Experiments\New Set of Experiments"
	 * Directory structure: New Set of Experiments -> TestRunX,.. -> 10KLOC, ...
	 */
	
	public static void main(String args[]){
		try{
			
			File dir = new File(args[0]);
			File[] listSubDir = dir.listFiles();
			
			
			
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
				
				StringBuffer metrics = new StringBuffer();
				
				
				for(int j = 0; j < testRunDirs.size(); j++){
					//D:\Rugrat Experiments\TestRun1\10KLOC
					String path = dir.getAbsolutePath()+"/"+testRunDirs.get(j)+"/"+
									LOCdirs.get(i)+"/Expr/javancss_metrics.txt";
					
					String result = processFile(path);
					metrics.append(result+"\n");	
					
					/* This part calculates the MDIT from the config.xml files */
					
//					String path = dir.getAbsolutePath()+"/"+testRunDirs.get(j)+"/"+
//							LOCdirs.get(i)+"/Expr/config.xml";
//					String result = getMDIT(path);
//					metrics.append(result+"\n");
					
					
				}
				
				String xLOC = LOCdirs.get(i).toString();
				//Summary
				System.out.println("\n"+xLOC);
				System.out.println("NOC\tNOM\tTLOC\tCLOC\tMLOC\tACC");
//				System.out.println("MDIT");
				System.out.println(metrics.toString());
				
				
				
			}
			
			System.out.println("Done :)");
		}catch(Exception e){
			e.printStackTrace();
		}
			
			
			
			
	}
	
	
	public static String processFile(String path){
		try{
		File file = new File(path);
		FileReader freader = new FileReader(file);
		BufferedReader reader = new BufferedReader(freader);
		
		String NOC =null , NOM = null, TLOC = null, CLOC = null, MLOC = null, ACC = null;
		
		// Count line total line numbers			
		int lineCount = 0;
		while(reader.readLine() != null){
			lineCount++;
		}
		
//		System.out.println(lineCount);
		
		// re-open file to read
		freader = new FileReader(file);
		reader = new BufferedReader(freader);
		
		String line = "";	
		int count = 0;
		
		
		/**
		 *
		 * Packages   Classes Functions      NCSS  Javadocs | per
		 * -------------------------------------------------------------
		 *     1.00     55.00   1028.00  13878.00      0.00 | Project
		 *              55.00   1028.00  13878.00      0.00 | Package
		 *                        18.69    252.33      0.00 | Class
		 *                                  13.50      0.00 | Function
		 */
		
		while((line = reader.readLine()) != null ){
			count++;
			
			switch(count){
			case 8: // line 8: | Project
				String[] proj = line.trim().split("\\s+");
				NOC 	= proj[1];					
				TLOC	= proj[3];
				break;
			case 10:
				String[] cls = line.trim().split("\\s+");
				NOM 	= cls[0];
				CLOC 	= cls[1];
				break;
				
			case 11:
				String[] fnc = line.trim().split("\\s+");
				MLOC	= fnc[0];
				break;
			
			default: // CCN is the 3rd to last line in the file
				if(count == (lineCount-2)){
					String[] ccn = line.trim().split("\\s+");
					ACC 	= ccn[3];
				}
				break;
			}
			
//			System.out.println(line);
			
		}
		
		String output = (NOC + "\t" + NOM + "\t" + TLOC + "\t" + CLOC + "\t" + MLOC + "\t" + ACC);
		
		
//		System.out.println("TEST TEST TEST \t"+ output);
		return output;
		
	}catch(Exception e){
		e.printStackTrace();
	}
	return null;
	}
	
	
	
	private static String getMDIT(String path){
		
		String output="";		
		DocumentBuilderFactory docBuildFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder docBuilder = docBuildFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new File(path));
			
			Node root = doc.getDocumentElement();
			NodeList nList = root.getChildNodes();
			
			
			
			for(int i = 0; i < nList.getLength(); i++){
				Node node = nList.item(i);
				if(node.getNodeName().equals("maxInheritanceDepth")){
//					System.out.println(node.getTextContent());
					output = node.getTextContent();
					break;
				}
			}							
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
		return output;
		
	}
	

}
