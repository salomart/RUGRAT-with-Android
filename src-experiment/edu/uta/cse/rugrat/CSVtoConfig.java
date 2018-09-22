package edu.uta.cse.rugrat;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.StringTokenizer;


/**
 Parse CSV File using StringTokenizer example.
This class parses comma separated file (CSV file from ACTS tool) using
Java StringTokenizer and BufferedReader classes and writes config files to disk.
**/

public class CSVtoConfig {
 
	public static void main(String[] args) {
		
		try
		{
			int i = 0;
			FileOutputStream fos = null;
			BufferedOutputStream outstream = null;
 
			//csv file containing data
			String strFile = "rugrat.csv";
 
			//create BufferedReader to read csv file
			BufferedReader br = new BufferedReader( new FileReader(strFile));
			String strLine = "";
			StringTokenizer st = null;
			int lineNumber = 0, tokenNumber = 0;
			String[] param ={"noOfInterfaces","noOfClasses","maxNoOfMethodsPerInterface","maxAllowedMethodCalls",
					"noOfInheritanceChains","probability","maxNestedIfs","maxInheritanceDepth",
					"maxNoOfParametersPerMethod","maxRecursionDepth","maxNoOfClassFields","maxNoOfMethodsPerClass",
					"recursionProbability","callType", "minNoOfParametersPerMethod"};
 
			//read comma separated file line by line
			while( (strLine = br.readLine()) != null)
			{
				lineNumber++;
 
				//break comma separated line using ","
				st = new StringTokenizer(strLine, ",");
				String config = "<RUGRAT>\n";
				int index = 0;
 
				while(st.hasMoreTokens())
				{
					//display csv values
					tokenNumber++;
//					System.out.println("Line # " + lineNumber + 
//							", Token # " + tokenNumber 
//							+ ", Token : "+ st.nextToken());
					String token = st.nextToken();
					if(token.equals("*")){
						switch(index){
						case 0: token = "1"; break; // #interface
						case 1: token = "10"; break; //#classes
						case 2: token = "2"; break; // maxNoMethodPerInterface
						case 3: token = "2"; break; // maxAllowedMethodCalls
						case 4: token = "2"; break; //#inheritanceChain
						case 5: token = "20"; break; //probability
						case 6: token = "2"; break; //maxNestedIfs
						case 7: token = "3"; break; //maxInheritanceDepth
						case 8: token = "2"; break; //maxNoOfParametersPerMethod
						case 9: token = "1"; break; // recursionDepth
						case 10: token = "10"; break; // maxClassFields
						case 11: token = "10"; break; //maxMethodPerClass
						case 12: token = "0"; break; //recurstionPrbability
						case 13: token = "MCO1_1"; break; //CallType
						}
						
					}
					
					config += "<"+param[index]+">"+token+"</"+param[index]+">"+ "\n";
					index++;
					
					
				}
				
				config += "<useQueries>false</useQueries>\n"+
					"<injectFilename></injectFilename>\n"+
					"<password></password>\n"+
					"<maximumArraySize>100</maximumArraySize>\n"+
					"<dbName></dbName>\n"+
					"<dbDriver></dbDriver>\n"+
					"<totalLOC>10000</totalLOC>\n"+// Here you can change the LOC
					"<dbUsername></dbUsername>\n"+
					"<classNamePrefix>TenKLOC</classNamePrefix>\n"+
					"<intMaxValue>100</intMaxValue>\n"+
					"<queryFilename></queryFilename>\n";
				
				config += 				
				"<allowedTypes>\n"+
					"\t<type>char</type>\n"+
					"\t<type>byte</type>\n"+
					"\t<type>short</type>\n"+
					"\t<type>int</type>\n"+
					"\t<type>long</type>\n"+
					"\t<type>float</type>\n"+
					"\t<type>double</type>\n"+
					"\t<type>String</type>\n"+
					"\t<type>Object</type>\n"+
				"</allowedTypes>\n"+
				"</RUGRAT>\n";
				
				fos = new FileOutputStream(new File("config"+i+".xml")); 
				outstream = new BufferedOutputStream(fos);
				outstream.write(config.getBytes());
				
				outstream.flush();
				outstream.close();
				
				System.out.println("config"+i+".xml written..\n");
				
				i++;
 
				//reset token number
				tokenNumber = 0;
 
			}
			
			System.out.println("SUCCSEEFUL!!");
 
 
		}
		catch(Exception e)
		{
			System.out.println("Exception while reading csv file: " + e);			
		}
	}
}