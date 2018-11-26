package edu.uta.cse.proggen.packageLevelElements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import edu.uta.cse.proggen.configurationParser.ConfigurationXMLParser;
import edu.uta.cse.proggen.util.ProgGenUtil;

/**
 * Creates Single entry classes in a tree structure.
 * For X number of generated classes, it will create Y number of SingleEntry class
 * such that Y = X/150. That is, each of Y classes will call 150 SingleEntry methods
 * of generated classes; this is the leaf level.
 * 
 * Then at the higher level, Z = Y/150, number of classes will call 150 each.
 * 
 *  So, the tree depth is: Log150(X) =  Log(X) / Log(150);
 * 
 * @author Ishtiaque Hussain {ishtiaque.hussain@mavs.uta.edu}
 * 
 */

public class TreeOfSingleEntryGenerator {
	
	private ArrayList<ClassGenerator> listOfClasses ;
	private final double  methCallLimit = 150;
	private final String DirPath;
	private final String formalParam; 
	private final String argument;
	
	private int LEVEL;
	
	public TreeOfSingleEntryGenerator(ArrayList<ClassGenerator> list, String pathToDir){
		listOfClasses = list;
		DirPath = pathToDir;
		LEVEL  = (int) Math.ceil( Math.log10(listOfClasses.size())/ Math.log10(methCallLimit) );
		formalParam = formalParamBuilder();
		argument = argBuilder();		
	}

	
	/**
	 *  Tree of entry Classes	  
	 */ 	
	public void generateTreeOfSingleEntryClass(){	
		System.out.println("Writing SingleEntry classes...");
		createClass(0, listOfClasses.size() );		
		System.out.println("Successfully written!!");
	}
	
	/**
	 * Creates String of {int i0, int i1, int i2, ...}
	 * @return int i0, int i1, int i2, int i3, ...
	 */
	private String formalParamBuilder(){
		
		StringBuilder param = new StringBuilder();
		for(int j =0; j< ProgGenUtil.maxNoOfParameters; j++)
			param.append("int i"+j+",");
		
		String st = param.toString();
		st = st.substring(0, st.length()-1);
				
		return st;
	}
	
	/**
	 *  
	 * @return argument of a method, e.g., f1, f2, f3, f4, f5, f6
	 */
	
	private String argBuilder(){
		StringBuilder fields = new StringBuilder();
		for(int j = 0; j < ProgGenUtil.maxNoOfParameters; j++){
			fields.append("f"+j+",");
		}
		String f = fields.toString();
		f = f.substring(0, fields.length() - 1);
		
		return f;
		
	}
	
	private void createClass(int level, int target) {
		boolean generateAndroidServices = ConfigurationXMLParser
				.getProperty("generateAndroidServices")
				.equals("yes");
		boolean generateBasicAndroidApp = ConfigurationXMLParser
				.getProperty("generateBasicAndroidApp")
				.equals("yes");
		String packageName = ConfigurationXMLParser.getProperty("packageName");
		
		if (level == 0 && !generateAndroidServices) {
			
			/*
			 * create 'loop' number of classes where each class calls 150 or
			 * less T.SingleEntry() methods in total 'target' number of
			 * T.SingleEntry() methods name these classes TStart_Llevel_X
			 */
			
			
			int count = 0;
			int loop = (int) Math.ceil((double) target / 150);

			for (int i = 0; i < loop; i++) {
				
				try{
					File file = null;
					
					if (packageName != null && packageName != "") {
						String[] splitPackageName = packageName.split("\\.");
						String directory = DirPath
								+ (generateAndroidServices || generateBasicAndroidApp ? "java" : "src")
								+ File.separator;
						
						for (String eachStr : splitPackageName) {
							directory += eachStr + File.separator;
						}
						
						directory += "TStart_L"+level+"_"+i+".java";
						file = new File(directory);
					} else {
						file = new File( DirPath+
								(generateAndroidServices || generateBasicAndroidApp ? "java" : "src")
								+ File.separator +
								"com" + File.separator +
								"accenture" + File.separator + "lab" + 
								File.separator + "carfast"
								+ File.separator + "test"
								+ File.separator + "TStart_L"+level+"_"+i+".java");
					}
					
					FileWriter  fileWriter = new FileWriter(file);
					BufferedWriter writer = new BufferedWriter(fileWriter);
					
					StringBuffer output = new StringBuffer();
					
					if (packageName != null && packageName != "") {
						output.append("package " + packageName + ";\n\n\n");
					} else {
						output.append("package com.accenture.lab.carfast.test;\n\n\n");
					}
					
					output.append("public class TStart_L"+level+"_"+i+"{\n");
					for(int k = 0; k < ProgGenUtil.maxNoOfParameters; k++){
						output.append("private static int f"+ k + ";\n");
					}
					
					output.append("\n\n");
					output.append("public static void entryMethod(");
					//int i0, int i1, int i2, int i3, int i4, int i5, int i6){\n");					
					output.append(formalParam +"){\n");
					
					for( int k = 0; k < ProgGenUtil.maxNoOfParameters; k++){
						output.append("f"+k+ " = " + "i"+ k+ ";\n");
					}			

					
					for (int j = 0; j < methCallLimit && count < target; j++, count++) {
						// call Tj.SingleEntry();
//						output.append("FiveMLOC"+count+".singleEntry(f0,f1,f2,f3,f4,f5,f6);\n");
						output.append(ConfigurationXMLParser.getProperty("classNamePrefix")+count+".singleEntry("+argument+");\n");						
					}
					
					output.append("}\n}");
					
					String out = output.toString();
//					System.out.println("Writing L0 level entry classes.");
					
					writer.write(out);
					writer.close();
					
					
				}catch(Exception e){
					e.printStackTrace();
				}				
			}			
			
			createClass(level + 1, loop);			
			
		} else {
			if (level ==  LEVEL || generateAndroidServices) {
				// create FiveMLOCStart.java class that will call
				// FiveMLOCStart_L(prevLevel)_0.entryMethod();				
				try{					
					File file = null;
					File mfile = null;
					File rfile = null;
					
					if (packageName != null && packageName != "") {
						String[] splitPackageName = packageName.split("\\.");
						String directory = DirPath
								+ (generateAndroidServices || generateBasicAndroidApp ? "java" : "src")
								+ File.separator;
						String mdirectory = DirPath;
						String rdirectory = DirPath;
						for (String eachStr : splitPackageName) {
							directory += eachStr + File.separator;
						}
						
						directory += ConfigurationXMLParser.getProperty("classNamePrefix")+"Start"+".java";
						file = new File(directory);
						if (generateAndroidServices || generateBasicAndroidApp) {
							mdirectory += "AndroidManifest.xml";
							mfile = new File(mdirectory);
							rdirectory += "res" + File.separator + "layout";
							rfile = new File(rdirectory);
							rfile.mkdirs();
							rdirectory += File.separator + "main_layout.xml";
							rfile = new File(rdirectory);
						}
					} else {
						file = new File( DirPath+
								"TestPrograms" + File.separator +
								"com" + File.separator +
								"accenture" + File.separator + "lab" + 
								File.separator + "carfast"
								+ File.separator + "test"
								+ File.separator + ConfigurationXMLParser.getProperty("classNamePrefix")+"Start"+".java");
						if (generateAndroidServices || generateBasicAndroidApp) {
							mfile = new File( DirPath+
									"TestPrograms" + File.separator +
									"com" + File.separator +
									"accenture" + File.separator + "lab" + 
									File.separator + "carfast"
									+ File.separator + "test"
									+ File.separator + "AndroidManifest"+".xml");
							rfile = new File( DirPath+
									"TestPrograms" + File.separator +
									"com" + File.separator +
									"accenture" + File.separator + "lab" + 
									File.separator + "carfast"
									+ File.separator + "test"
									+ File.separator + "main_layout"+".xml");
						}
					}
					
//					File file = new File("./FiveMLOCStart.java");
					FileWriter  fileWriter = new FileWriter(file);
					BufferedWriter writer = new BufferedWriter(fileWriter);
					
					BufferedWriter mwriter = generateAndroidServices || generateBasicAndroidApp
							? new BufferedWriter(new FileWriter(mfile)) : null;
					BufferedWriter rwriter = generateAndroidServices || generateBasicAndroidApp
									? new BufferedWriter(new FileWriter(rfile)) : null;
					
					StringBuffer output = new StringBuffer();
					StringBuffer manifest = new StringBuffer();
					StringBuffer layout = new StringBuffer();
					
					if (packageName != null && packageName != "") {
						output.append("package " + packageName + ";\n\n");
					} else {
						output.append("package com.accenture.lab.carfast.test;\n\n");
						packageName = "com.accenture.lab.carfast.test";
					}
					
//					output.append("public class FiveMLOCStart {\n");
					
					if (generateAndroidServices) {
						output.append("import android.view.View;\n" + 
								"import android.widget.Button;\n" + 
								"import android.widget.TextView;\n" +
								"import android.app.Activity;\n"
								+ (generateBasicAndroidApp ? "import android.app.AlertDialog;\n" : "")
								+ "import android.os.Bundle;\n"
								+ "import android.content.Intent;\n\n"
								+ "public class " + ConfigurationXMLParser.getProperty("classNamePrefix")
								+ "Start extends Activity {\n");
						manifest.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + 
								"<manifest xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" + 
								"    package=\""+ packageName +"\">\n" + 
								"\n" + 
								"    <application\n" + 
								"        android:allowBackup=\"true\"\n" + 
								"        android:icon=\"@mipmap/ic_launcher\"\n" + 
								"        android:label=\"@string/app_name\"\n" + 
								"        android:roundIcon=\"@mipmap/ic_launcher_round\"\n" + 
								"        android:supportsRtl=\"true\"\n" + 
								"        android:theme=\"@style/AppTheme\">\n" +
								"			<activity android:name=\"."+ ConfigurationXMLParser.getProperty("classNamePrefix") +"Start\">\n" + 
								"            <intent-filter>\n" + 
								"                <action android:name=\"android.intent.action.MAIN\" />\n" + 
								"\n" + 
								"                <category android:name=\"android.intent.category.LAUNCHER\" />\n" + 
								"            </intent-filter>\n" + 
								"        </activity>\n");
						layout.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
								"<ScrollView\n" + 
								"  xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" + 
								"  android:layout_width=\"fill_parent\"\n" + 
								"  android:layout_height=\"fill_parent\">\n" +
								"	<LinearLayout\n" + 
								"              android:layout_width=\"match_parent\"\n" + 
								"              android:layout_height=\"match_parent\"\n" + 
								"              android:orientation=\"vertical\" >\n" + 
								"    <TextView android:id=\"@+id/welcometext\"\n" + 
								"              android:layout_width=\"wrap_content\"\n" + 
								"              android:layout_height=\"wrap_content\"\n" +
								"			   android:textStyle=\"bold\"\n"+
								"              android:text=\"Hello!\" />\n"+
								"    <Button android:id=\"@+id/changebtn\"\n" + 
								"            android:layout_width=\"wrap_content\"\n" + 
								"            android:layout_height=\"wrap_content\"\n" + 
								"            android:text=\"Click Me!\" />\n");
					} else if (generateBasicAndroidApp) {
						output.append("import android.view.View;\n" + 
								"import android.widget.Button;\n" + 
								"import android.widget.TextView;\n"+
								"import android.app.Activity;\n"
								+ "import android.app.AlertDialog;\n"
								+ "import android.os.Bundle;\n\n"
								+ "public class " + ConfigurationXMLParser.getProperty("classNamePrefix")
								+ "Start extends Activity {\n");
						manifest.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + 
								"<manifest xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" + 
								"    package=\""+ packageName +"\">\n" + 
								"\n" + 
								"    <application\n" + 
								"        android:allowBackup=\"true\"\n" + 
								"        android:icon=\"@mipmap/ic_launcher\"\n" + 
								"        android:label=\"@string/app_name\"\n" + 
								"        android:roundIcon=\"@mipmap/ic_launcher_round\"\n" + 
								"        android:supportsRtl=\"true\"\n" + 
								"        android:theme=\"@style/AppTheme\">\n" +
								"			<activity android:name=\"."+ ConfigurationXMLParser.getProperty("classNamePrefix") +"Start\">\n" + 
								"            <intent-filter>\n" + 
								"                <action android:name=\"android.intent.action.MAIN\" />\n" + 
								"\n" + 
								"                <category android:name=\"android.intent.category.LAUNCHER\" />\n" + 
								"            </intent-filter>\n" + 
								"        </activity>\n");
						layout.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
								"<ScrollView\n" + 
								"  xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" + 
								"  android:layout_width=\"fill_parent\"\n" + 
								"  android:layout_height=\"fill_parent\">\n" +
								"	<LinearLayout\n" + 
								"              android:layout_width=\"match_parent\"\n" + 
								"              android:layout_height=\"match_parent\"\n" + 
								"              android:orientation=\"vertical\" >\n" + 
								"    <TextView android:id=\"@+id/welcometext\"\n" + 
								"              android:layout_width=\"wrap_content\"\n" + 
								"              android:layout_height=\"wrap_content\"\n" +
								"			   android:textStyle=\"bold\"\n"+
								"              android:text=\"Hello!\" />\n"+
								"    <Button android:id=\"@+id/changebtn\"\n" + 
								"            android:layout_width=\"wrap_content\"\n" + 
								"            android:layout_height=\"wrap_content\"\n" + 
								"            android:text=\"Click Me!\" />\n");
					} else {
						output.append("public class "
								+ ConfigurationXMLParser.getProperty("classNamePrefix") + "Start {\n");
					}
					
					if (!generateAndroidServices) {
						for(int k = 0; k < ProgGenUtil.maxNoOfParameters; k++){
							output.append("private static int f"+ k + ";\n");
						}
						
						output.append("\n\n");
						
						output.append("public static void entryMethod(");
						//int i0, int i1, int i2, int i3, int i4, int i5, int i6){\n");
						
						output.append(formalParam + "){\n");
						
						for( int k = 0; k < ProgGenUtil.maxNoOfParameters; k++){
							output.append("f"+k+ " = " + "i"+ k+ ";\n");
						}
						
						output.append("TStart_L"+(level-1)+"_0.entryMethod("+ argument +");\n}\n\n");
					}
					
					if ((generateAndroidServices && generateBasicAndroidApp) || generateAndroidServices) {
						if (generateAndroidServices && generateBasicAndroidApp) {
							output.append(ProgGenUtil.tabSpacing(1) + "@Override\n"
									+ ProgGenUtil.tabSpacing(1) + "protected void onCreate(Bundle savedInstanceState) {\n"
									+ ProgGenUtil.tabSpacing(2) + "super.onCreate(savedInstanceState);\n"
									+ ProgGenUtil.tabSpacing(2) + "setContentView(R.layout.main_layout);\n" +
									ProgGenUtil.tabSpacing(2) + "Button btn = (Button) findViewById(R.id.changebtn);\n" + 
									ProgGenUtil.tabSpacing(2) + "final TextView wt = (TextView) findViewById(R.id.welcometext);\n" + 
									ProgGenUtil.tabSpacing(2) + "btn.setOnClickListener(new View.OnClickListener() {\n" + 
									ProgGenUtil.tabSpacing(3) + "public void onClick(View v) {\n" + 
									ProgGenUtil.tabSpacing(4) + "wt.setText(\"Welcome to android\");\n" + 
									ProgGenUtil.tabSpacing(3) +"}\n" + 
									ProgGenUtil.tabSpacing(2) + "});\n");
						} else {
							output.append("@Override\n" + 
									"protected void onCreate(Bundle savedInstanceState) {\n" +
									"super.onCreate(savedInstanceState);\n");
						}
						output.append(ProgGenUtil.tabSpacing(1) + "}\n");
						int count = 0;
						
						for (int j = 0; j < methCallLimit && count < target; j++, count++) {
							output.append("\n" + ProgGenUtil.tabSpacing(1) + "public void service"+ ConfigurationXMLParser.getProperty("classNamePrefix") + count +"(View view) {\n" + 
									ProgGenUtil.tabSpacing(2) + "startService(new Intent(this, " + ConfigurationXMLParser.getProperty("classNamePrefix")
									+ count + ".class));\n"
									+ProgGenUtil.tabSpacing(1) +"}\n");
							manifest.append("<service\n"+
									"android:name=\"."+ ConfigurationXMLParser.getProperty("classNamePrefix") + count + "\"\n"+
									"android:exported=\"false\"/>\n");
							layout.append("<Button android:id=\"@+id/button"+ count +"\"\n" + 
									"            android:layout_width=\"wrap_content\"\n" + 
									"            android:layout_height=\"wrap_content\"\n" + 
									"            android:onClick=\"service"+ConfigurationXMLParser.getProperty("classNamePrefix") + count+"\"\n" +
									"            android:text=\""+ ConfigurationXMLParser.getProperty("classNamePrefix") + count + " \"/>\n");
						}
						
					} else {
						if (generateBasicAndroidApp) {
							output.append("@Override\n"
									+ "protected void onCreate(Bundle savedInstanceState) {\n"
									+ "super.onCreate(savedInstanceState);\n"
									+ "setContentView(R.layout.main_layout);\n"
									+ "Button btn = (Button) findViewById(R.id.changebtn);\n" + 
									"        final TextView wt = (TextView) findViewById(R.id.welcometext);\n" + 
									"        btn.setOnClickListener(new View.OnClickListener() {\n" + 
									"                                   public void onClick(View v) {\n" + 
									"                                       wt.setText(\"Welcome to android\");\n" + 
									"                                   }\n" + 
									"                               });\n"
									+ "entryMethod(");
						} else {
							output.append("public static void main(String[] args){\n entryMethod(");
						}
						
						StringBuilder str = new StringBuilder();
						
						for(int i =0; i < ProgGenUtil.maxNoOfParameters; i++){
							str.append(generateBasicAndroidApp ? "(int)(Math.random() * 100)," : "Integer.parseInt(args["+ i+ "]),");
						}
						
						String s = str.toString();
						s = s.substring(0, str.length()-1);
						s += ");\n}";
						output.append(s);
					}
					
					output.append("}");	
					manifest.append("</application>\n" + 
							"\n" + 
							"</manifest>");
					layout.append("</LinearLayout>\n" + 
							"</ScrollView>");
					String out = output.toString();
					String mout = manifest.toString();
					String rout = layout.toString();
//					System.out.println("Writing the 'Start' class.");
					
					if (mwriter != null) {
						mwriter.write(mout);
						mwriter.close();
					}
					rwriter.write(rout);
					rwriter.close();
					writer.write(out);
					writer.close();

				}catch(Exception e){
					e.printStackTrace();
				}
				
				return;
				
			} else if (!generateAndroidServices) {

				int count = 0;
				int loop = (int) Math.ceil((double) target / 150);

				for (int i = 0; i < loop; i++) {
					/*
					 * create 'loop' number of classes,where each calls 150 or
					 * less TStart_L(prevlevel)_X.entryMethod() methods; in
					 * total 'target' number of calls.
					 * 
					 * Name each class TStart_Llevel_X
					 */
					
					try{
						
						File file = null;
						
						if (packageName != null && packageName != "") {
							String[] splitPackageName = packageName.split("\\.");
							String directory = DirPath + "TestPrograms" + File.separator;
							
							for (String eachStr : splitPackageName) {
								directory += eachStr + File.separator;
							}
							
							directory += "TStart_L"+level+"_"+i+".java";
							file = new File(directory);
						} else {
							file = new File( DirPath+
									"TestPrograms" + File.separator +
									"com" + File.separator +
									"accenture" + File.separator + "lab" + 
									File.separator + "carfast"
									+ File.separator + "test"
									+ File.separator + "TStart_L"+level+"_"+i+".java");
						}
						
//						File file = new File("./TStart_L"+level+"_"+i+".java");
						FileWriter  fileWriter = new FileWriter(file);
						BufferedWriter writer = new BufferedWriter(fileWriter);
						
						StringBuffer output = new StringBuffer();
						
						if (packageName != null && packageName != "") {
							output.append("package " + packageName + ";\n\n\n");
						} else {
							output.append("package com.accenture.lab.carfast.test;\n\n\n");
						}
						
						output.append("public class TStart_L"+level+"_"+i+"{\n");
						for(int k = 0; k < ProgGenUtil.maxNoOfParameters; k++){
							output.append("private static int f"+ k + ";\n");
						}
						
						output.append("\n\n");
						
						output.append("public static void entryMethod(");
						//int i0, int i1, int i2, int i3, int i4, int i5, int i6){\n");						
						
						output.append(formalParam + "){\n");
						
						for( int k = 0; k < ProgGenUtil.maxNoOfParameters; k++){
							output.append("f"+k+ " = " + "i"+ k+ ";\n");
						}		
						
						for (int j = 0; j < methCallLimit && count < target; j++, count++) {
							// Call TStart_L(prevlevel)_X.entryMethod()
							output.append("TStart_L"+(level-1)+"_"+count+".entryMethod("+argument+");\n");						
						}
						
						output.append("}\n}");
						
												
						String out = output.toString();
//						System.out.println("Writing mid level classes.");
						
						writer.write(out);
						writer.close();						
						
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				
				createClass(level + 1, loop);
				
			}
		}
	}

}
