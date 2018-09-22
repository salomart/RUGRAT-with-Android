package edu.uta.cse.rugrat.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class Context {
	
	public class Classes implements IconfigOption{

		@Override
		public String getValue(String s) {		

			int value = Context.getInt(s);
			
			Context._noOfClass = value; 
			
			return "<noOfClasses>"+ value +"</noOfClasses>";			
		}		
	}
	
	public class InterfacesClassCanImplement implements IconfigOption{
		
		public String getValue(String s){
				
			int value = Integer.MAX_VALUE;
			while(value > Context._noInterfaces)
				value = Context.getInt(s);
			
			return "<maxInterfacesToImplement>"+value+"</maxInterfacesToImplement>";			
		}
	}
	
	public class MethodsPerInterface implements IconfigOption{
		
		public String getValue(String s){
			int value = Context.getInt(s);		
			return "<maxNoOfMethodsPerInterface>" + value+ "</maxNoOfMethodsPerInterface>";
		}
	}
	
	public class Interfaces implements IconfigOption{
		
		public String getValue(String s){
			int value = Context.getInt(s);
			Context._noInterfaces = value;
			return "<noOfInterfaces>" + value + "</noOfInterfaces>";
		}
	}
	
	public class UseQueries implements IconfigOption{
		public String getValue(String s){
			//TODO: pick random value
			String value = "false";
			return "<useQueries>" + value + "</useQueries>";
		}
	}
	
	public class MaxNoMethodsPerInterface implements IconfigOption{

		@Override
		public String getValue(String s) {
			int value = Context.getInt(s);
			return "<maxNoOfMethodsPerInterface>" + value + "</maxNoOfMethodsPerInterface>";
		
		}		
	}
	
	public class MaxAllowedMethodCall implements IconfigOption{

		@Override
		public String getValue(String s) {
			int value = Context.getInt(s);			
			return "<maxAllowedMethodCalls>" + value + "</maxAllowedMethodCalls>";			
		}		
	}
	
	public class MaxValueForLoop implements IconfigOption{
		public String getValue (String s){
			int value = Context.getInt(s);
			return "<maxValueForLoop>" + value + "</maxValueForLoop>";		
		}		
	}
	
	public class InjectFileName implements IconfigOption{
		public String getValue(String s){
			//TODO: implement 
			String value ="";
			return "<injectFilename>" + value + "</injectFilename>"; 
		}
	}
	
	public class Password implements IconfigOption{
		public String getValue(String s){
			// TODO: Implement
			String value ="";
			return "<password>" + value + "</password>";
		}
	}
	
	public class AllowedIndirectRecursion implements IconfigOption{
		@Override
		public String getValue(String s) {
			// TODO Implement 
			String value = "no";
			return "<allowIndirectRecursion>" + value + "</allowIndirectRecursion>";
		}		
	}
	
	public class MaximumArraySize implements IconfigOption{

		@Override
		public String getValue(String s) {
			int value = Context.getInt(s);
			return "<maximumArraySize>" + value + "</maximumArraySize>";			
		}		
	}
	
	public class DBName implements IconfigOption{

		@Override
		public String getValue(String s) {
			//TODO: implement
			String value = "";
			return "<dbName>" + value + "</dbName>";
		}		
	}
	
	public class ReachabilityMatrix implements IconfigOption{

		@Override
		public String getValue(String s) {
			// TODO: Implement
			String value = "no";
			return "<doReachabilityMatrix>" + value + "</doReachabilityMatrix>";
		}		
	}
	
	public class InheritanceChain implements IconfigOption{

		@Override
		public String getValue(String s) {
			/**
			 *  Since #chains depends on 'depth'
			 *  class "MaxInheritanceDepth" handles operation of this.
			 *  
			 *  
			 *  #chains * depth <= #classes
			 *  so, #chains <= (#classes/depth)
			 */			
			
			return "";

		}		
	}
	
	public class Probability implements IconfigOption{

		@Override
		public String getValue(String s) {
			int value = Context.getInt(s);
			return "<probability>" + value + "</probability>";			
		}		
	}
	
	public class MaxNestedIf implements IconfigOption{

		@Override
		public String getValue(String s) {
			int value = Context.getInt(s);
			return "<maxNestedIfs>" + value + "</maxNestedIfs>";
		}		
	}
	
	public class MinNoOfClassFields implements IconfigOption {

		@Override
		public String getValue(String s) {

			

			int value = Integer.MAX_VALUE;
			// maxNoOfClassFields not defined yet
			if (Context._maxNoClassField == -1)
				value = Context.getInt(s);
			else { // maxNoOfClassFields already defined
				while (value  > Context._maxNoClassField)
					value = Context.getInt(s);					
			}

			Context._minNoClassField = value;

			return "<minNoOfClassFields>" + value + "</minNoOfClassFields>";
		}
	}
	
	public class AllowArrays implements IconfigOption{

		@Override
		public String getValue(String s) {
			
			// Input String 's' is not used.
			
			Random rand = new Random(System.currentTimeMillis());
			String value ="no";
			if((Boolean) (rand.nextInt()%2==0))
					value = "yes";
			
			return "<allowArray>" + value + "</allowArray>";
		}		
	}
	
	public class MaxInheritanceDepth implements IconfigOption{

		@Override
		public String getValue(String s) {
			
			
			String output = "";
			
			int value = Context.getInt(s);
			
			//DEBUG:
//			System.out.println("MaxInheritDepth: "+ value);
			
			Context._maxInheritDepth = value;
			
			output = "<maxInheritanceDepth>" + value + "</maxInheritanceDepth>" +"\n";
			
			//setting range of #chains, Min is hardcoded to 1
			int Max = Context._noOfClass / value;
			int Min = 1;
			
			int chains =  Min + (int)(Math.random() * ((Max - Min) + 1));
			
			output +="<noOfInheritanceChains>" + chains + "</noOfInheritanceChains>";
			
			return output;			
		}		
	}
	
	public class MaxNoOfParametersPerMethod implements IconfigOption{

		@Override
		public String getValue(String s) {
						
			int value = Integer.MIN_VALUE;
			
			//minNoOfParametersPerMethod not defined yet
			if(Context._minNoParamMethod == -1)
				value = Context.getInt(s);
			else{
				while(value < Context._minNoParamMethod)
					value = Context.getInt(s);
			}
			
			Context._maxNoParamMethod = value;
			return "<maxNoOfParametersPerMethod>" + value + "</maxNoOfParametersPerMethod>";
		}		
	}
	
	public class MinInheritanceDepth implements IconfigOption{

		@Override
		public String getValue(String s) {
			
			
			
			int value = Integer.MAX_VALUE;
			
			// To avoid infinite loop and bound #tries
			int noTries = 10;
			
			//
			
			while(value > Context._maxInheritDepth && noTries > 0){
				value = Context.getInt(s);			
				noTries--;				
			}
			
			//DEBUG:
//			System.out.println("No of tries:" + Math.abs(10-noTries));
			
			
			// Exhausted!! - keeping Max and Min the same
			if(noTries <= 0)
				value = Context._maxInheritDepth;
			
			Context._minInheritDepth = value;
			
			
			return "<minInheritanceDepth>" + value + "</minInheritanceDepth>";
		}		
	}
	
	public class DBDriver implements IconfigOption{

		@Override
		public String getValue(String s) {
			// TODO Implement
			String value = "";
			return "<dbDriver>" + value + "</dbDriver>";
		}		
	}
	
	public class TotalLOC implements IconfigOption{

		@Override
		public String getValue(String s) {
			String [] tok = s.split("\\s+");
			return "<totalLOC>" + tok[1] + "</totalLOC>";
		}		
	}
	
	public class MaxInterfacesToImplement implements IconfigOption{

		@Override
		public String getValue(String s) {
			int value = Context.getInt(s);
			return "<maxInterfacesToImplement>" + value + "</maxInterfacesToImplement>";
		}		
	}
	
	public class MaxRecursionDepth implements IconfigOption{

		@Override
		public String getValue(String s) {
			int value = Context.getInt(s);
			return "<maxRecursionDepth>" + value + "</maxRecursionDepth>";
		}		
	}
	
	public class DBUsername implements IconfigOption{
		//TODO: Implement
		public String getValue(String s){
			String value = "";
			return "<dbUsername>" + value + "</dbUsername>";
		}
	}
	
	public class ClassNamePrefix implements IconfigOption{

		@Override
		public String getValue(String s) {
			String [] tok = s.split("\\s+");
			return "<classNamePrefix>" + tok[1] + "</classNamePrefix>";
		}		
	}
	
	public class MaxNoOfClassFields implements IconfigOption{

		@Override
		public String getValue(String s) {
			
			
			int value = Integer.MIN_VALUE;
			
			// minNoOfClassFields not defined yet
			if(Context._minNoClassField == -1)
				value = Context.getInt(s);
			else{
				while(value < Context._minNoClassField)
					value = Context.getInt(s);				
			}
			
			Context._maxNoClassField = value;
			
			return "<maxNoOfClassFields>" + value + "</maxNoOfClassFields>";
		}		
	}
	
	public class MaxNoOfMethodsPerClass implements IconfigOption{

		@Override
		public String getValue(String s) {
			int value = Context.getInt(s);
			return "<maxNoOfMethodsPerClass>" + value + "</maxNoOfMethodsPerClass>";
		}		
	}
	
	public class RecursionProbability implements IconfigOption{
		//TODO: Implement
		public String getValue(String s){
			int value = 0;
			return "<recursionProbability>" + value + "</recursionProbability>"; 
		}
	}
	
	public class MinNoOfParametersPerMethod implements IconfigOption{

		@Override
		public String getValue(String s) {
			
			int value = Integer.MAX_VALUE;
			
			// maxNoOfParametersPerMethod not defined yet
			if(Context._maxNoParamMethod == -1)
				value = Context.getInt(s);
			else{
				while(value > Context._maxNoParamMethod)
					value = Context.getInt(s);
			}
			
			Context._minNoParamMethod = value;
			
		
			return "<minNoOfParametersPerMethod>" + value + "</minNoOfParametersPerMethod>";			
		}		
	}
	
	public class IntMaxValue implements IconfigOption{

		@Override
		public String getValue(String s) {
			int value = Context.getInt(s);
			return "<intMaxValue>" + value + "</intMaxValue>";
		}		
	}
	
	public class QueryFilename implements IconfigOption{

		@Override
		public String getValue(String s) {
			// TODO Implement
			String value = "";
			return "<queryFilename>" + value + "</queryFilename>";
		}		
	}
	
	public class CallType implements IconfigOption{

		@Override
		public String getValue(String s) {
			// TODO Implement
			String value = "MCO2_2";
			return "<callType>" + value + "</callType>";			
		}		
	}
	
	public class AllowedTypes implements IconfigOption{

		@Override
		public String getValue(String s) {
			// s = allowedTypes 2 char int long ..
			String [] tok = s.split("\\s+");
			int noTypes = Integer.valueOf(tok[1]);
			int availableTypes = tok.length - 2;
			
			HashSet<String> allowedTypesSet = new HashSet<String>();
			Random rand = new Random(System.currentTimeMillis());
			
			while(allowedTypesSet.size() != noTypes){
				String type = tok[rand.nextInt(availableTypes) + 2];
				allowedTypesSet.add(type);				
			}
			
			StringBuffer types = new StringBuffer();
			
			for(String t: allowedTypesSet){
				types.append("\t<type>" + t +"</type>\n");
			}
			
			return "<allowedTypes>" +"\n"+ types.toString()+ "</allowedTypes>";			
		}
		
	}
	
	/* Constraints */
	
	/**
	 * 1. maxInheritDepth * noInheritChain <= noClass
	 * 2. minNoParamMethod <= maxNoParamMethod
	 * 3. minInheritDepth <= maxInheritDepth
	 * 4. minNoClassField <= maxNoClassField
	 */
	
	static int _noOfClass; 
	static int _maxInheritDepth; 
	static int _minInheritDepth;
	static int _noInheritChain; 
	static int _minNoParamMethod;
	static int _maxNoParamMethod;
	static int _minNoClassField; 
	static int _maxNoClassField;
	static int _noInterfaces;
	
	public void init(){
		
		 _noOfClass = -1; 
		 _maxInheritDepth = -1; 
		 _minInheritDepth = -1;
		 _noInheritChain = -1; 
		 _minNoParamMethod = -1;
		 _maxNoParamMethod = -1;
		 _minNoClassField = -1; 
		 _maxNoClassField = -1;
		 _noInterfaces = -1;		
	}
	
	
	private HashMap<String, IconfigOption> map = new HashMap<String, IconfigOption>();
	
	public Context(){		
		map.put("maxInterfacesToImplement", new InterfacesClassCanImplement());
		map.put("noOfInterfaces", new Interfaces());
		map.put("useQueries", new UseQueries());
		map.put("maxNoOfMethodsPerInterface", new MaxNoMethodsPerInterface());
		map.put("noOfClasses", new Classes());
		map.put("maxAllowedMethodCalls", new MaxAllowedMethodCall());
		map.put("maxValueForLoop", new MaxValueForLoop());
		map.put("injectFilename",new InjectFileName());
		map.put("password", new Password());
		map.put("allowIndirectRecursion", new AllowedIndirectRecursion());
		map.put("maximumArraySize", new MaximumArraySize());
		map.put("dbName", new DBName());
		map.put("doReachabilityMatrix", new ReachabilityMatrix());
		map.put("noOfInheritanceChains", new InheritanceChain());
		map.put("probability", new Probability());
		map.put("maxNestedIfs", new MaxNestedIf());
		map.put("minNoOfClassFields", new MinNoOfClassFields());
		map.put("allowArray", new AllowArrays());
		map.put("maxInheritanceDepth", new MaxInheritanceDepth());
		map.put("maxNoOfParametersPerMethod", new MaxNoOfParametersPerMethod());
		map.put("minInheritanceDepth", new MinInheritanceDepth());
		map.put("dbDriver", new DBDriver());
		map.put("totalLOC", new TotalLOC());
		map.put("maxInterfacesToImplement", new MaxInterfacesToImplement());
		map.put("maxRecursionDepth", new MaxRecursionDepth());
		map.put("dbUsername", new DBUsername());
		map.put("classNamePrefix", new ClassNamePrefix());
		map.put("maxNoOfClassFields", new MaxNoOfClassFields());
		map.put("maxNoOfMethodsPerClass", new MaxNoOfMethodsPerClass());
		map.put("recursionProbability", new RecursionProbability());
		map.put("minNoOfParametersPerMethod", new MinNoOfParametersPerMethod());
		map.put("intMaxValue", new IntMaxValue());
		map.put("queryFilename", new QueryFilename());
		map.put("callType", new CallType());
		map.put("allowedTypes", new AllowedTypes());
	}
	
	public String getValue(String str){
		String[] tok = str.split("\\s+");
		
		//DEBUG:
//		System.out.println(map.get(tok[0]).getClass().getName());
		
		return map.get(tok[0]).getValue(str);
	}
	
	public static int getInt(String str){
		String [] tok = str.split("\\s+");
		int Min = Integer.valueOf(tok[1]);
		int Max = Integer.valueOf(tok[2]);
		
		int value = Min + (int)(Math.random() * ((Max - Min) + 1));
		
		return value;
		
	}

}
