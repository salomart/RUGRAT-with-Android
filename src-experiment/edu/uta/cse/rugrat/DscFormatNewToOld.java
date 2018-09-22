package edu.uta.cse.rugrat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class transforms Dsc output (for CarFast in Dumper mode with lots of local variables) of the pathcondition into a simpler format.
 * The output file is intended for the query evaluator for CarFast. 
 * @author Ishtiaque Hussain {ishtiaque.hussain@mavs.uta.edu}
 *
 */

public class DscFormatNewToOld {
	
	//maps from local variable to expressions (without local variables)
	static HashMap<String, String> hashMap = new HashMap<String, String>();
	
	// These are for optimization purpose
	static HashSet<String> setConstraints = new HashSet<String>();
	static StringBuffer constraint = new StringBuffer();
	static boolean ForcedQuit = false;
	static int depthGone = 0;
	static int ALLOWEDDEPTH = 20;
	static int SETSIZE = 20;
	
	static FileWriter fw;
	static PrintWriter out;

	
	public static void main(String[] args){
		String fileName = args[0]; // takes the first argument as input .txt file
		
		processFile(fileName);
		
		System.out.println("done dona done");
	}
	
	public static void processFile(String fileName){
		String line = null;
		int lineNum = 0;
		
		try{
			File file = new File(fileName); 
			FileReader fileReader = new FileReader(file);
			
			BufferedReader reader = new BufferedReader(fileReader);
			
			fw = new FileWriter("output.txt");
			out = new PrintWriter(fw);
			
			while((line = reader.readLine()) != null){
				System.out.println("Processing line number: "+ lineNum++);
				if(line.contains("=("))	// this is an assignment statement: v1=(expr)
					assignmentHandler(line);
				else
					conditionHandler(line);				
			}
			reader.close();		
			out.close();
			fw.close();			
		}catch(Exception e){
			e.printStackTrace();
		} catch (Error er) {
			System.err.println(line);
			er.printStackTrace();
			
		}
		
	}
	
	private static void assignmentHandler(String line) {
		String[] token = line.split("=");
		String lhs = token[0].trim(); // local variable: doesn't have a '(' in the beginning.
		String rhs = token[1]; // expression:
		
		if (token.length != 2) {
			System.out.println("Not a valid assignment: " + line);
		}
		
		hashMap.put(lhs, rhs.substring(1, rhs.length() - 1).trim());
	}
	
	private static void conditionHandler(String line){
		
		//FIXME: assuming condition statements always have local variables on their lhs and rhs
		
		String[] token = line.split(" "); // (v1 >= v2)
		if(token.length != 3) {
			System.out.println("Array/field access");
			System.out.println("Should never print");
			return;
		}
		if(line.contains("com.accenture.lab.carfast.test")){
			System.out.println("Array/field access: " + line);
			return;
		}
		String lhs = token[0].substring(1,token[0].length()).trim();
		String rhs = token[2].substring(0,token[2].length()-1).trim();
		String op = token[1];
		
		if(checkForCachedCondition(lhs, op, rhs) == false)
			getEvaluatedCondition(lhs, op, rhs);
	}
	

	
	
	
	/**
	 *  Tries caching of constraints to avoid printing/writing redundant constraints to the file.
	 *  This helps reduce the output file size  
	 * @param left
	 * @param op
	 * @param right
	 * @return false- if recursion goes more than specified depth.
	 */
	private static boolean checkForCachedCondition(String left, String op,
			String right) {

		// Initialization of recursion controllers		
		ForcedQuit = false;
		constraint.delete(0, constraint.length()) ;
		depthGone = 0;
		
		getLocalVarValueOpt(left);
	
		constraint.append(" "+ op + " ");
		
		// allow ALLOWEDDEPTH times recursion for each variable
		depthGone = 0;
		getLocalVarValueOpt(right);
		
		/* 'ForcedQuit' is true when recursion is more than allowed depth.
		 * To avoid OutOfMemoryError, we don't build the string. 
		 */
		if(ForcedQuit)
			return false;

		String finalConstraint = constraint.toString();
		
		/*flush the set when more than SETSIZE elements are there
		 * this is done to avoid OutOfMemoryError.
		 * We don't want to cache all the constraints, only most recently used ones.		
		 */
		if(setConstraints.size() >= SETSIZE)
			setConstraints.clear();
		
		//unique constraint, add to set and write to file
		if(setConstraints.add(finalConstraint)){
			out.write(finalConstraint);
			out.write("\n");
			return true;
		}
		// constraint in the set/cache means already written to file, so NOP.		
		return true;
		
			
		
		
	}

	private static void getLocalVarValueOpt(String var) {
		depthGone++;
		if(depthGone >= ALLOWEDDEPTH){
			ForcedQuit = true;
			return;
		}
		String val = hashMap.get(var);		
		if (val == null) {
			constraint.append(var);
		} else {			
			constraint.append("(");
			String[] toks = val.split(" ");
			
			if (toks.length != 3) {
				System.out.println("Array/field accessed");
			}
			
			// var1
			String v1 = toks[0];
						
			if (matchesLocalVariable(v1)) {				
				getLocalVarValueOpt(v1);
			}
			else{
				constraint.append(v1);
			}
			
			if (v1.length() > 10000000) {
				System.out.println("For debug purpose : too long String");
				System.out.println(v1 +": "+v1.length());
			}
			
			constraint.append(" " + toks[1]+ " ");
			
			
			// var2
			String v2 = toks[2];			
			if (matchesLocalVariable(v2)) {
				getLocalVarValueOpt(v2);
			}
			else{
				constraint.append(v2);
			}
			
			if (v2.length() > 10000000) {
				System.out.println("For debug purpose : too long String");
				System.out.println(v2+ ": "+v2.length());
			}

			constraint.append(")");
		}		
		
	}

	private static void getLocalVarValue(String var) {
		String val = hashMap.get(var);		
		if (val == null) {
			out.write(var);
		} else {			
			out.write("(");			
			String[] toks = val.split(" ");
			
			if (toks.length != 3) {
				System.out.println("Array/field accessed");
			}
			
			// var1
			String v1 = toks[0];
						
			if (matchesLocalVariable(v1)) {				
				getLocalVarValue(v1);
			}
			else{
				out.write(v1);
			}
			
			if (v1.length() > 10000000) {
				System.out.println("For debug purpose : too long String");
				System.out.println(v1 +": "+v1.length());
			}
			
			out.write(" " + toks[1] + " ");
			
			// var2
			String v2 = toks[2];			
			if (matchesLocalVariable(v2)) {
				getLocalVarValue(v2);
			}
			else{
				out.write(v2);
			}
			
			if (v2.length() > 10000000) {
				System.out.println("For debug purpose : too long String");
				System.out.println(v2+ ": "+v2.length());
			}

			out.write(")");			
		}		
	}
	
		
	private static void getEvaluatedCondition(String left, String op, String rght) {
		getLocalVarValue(left);
		out.write(" " + op + " ");
		getLocalVarValue(rght);
		out.write("\n");
	}

	private static boolean matchesLocalVariable(String line) {
		Pattern p = Pattern.compile("v\\d++");
		Matcher m = p.matcher(line);
		
		return m.matches();
	}
}

