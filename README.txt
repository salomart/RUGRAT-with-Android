								R	U	G	R	A	T

		Thank you for using Random Utility Generator for pRogram Analysis and Testing (RUGRAT) version-1.0.

		
What is this?
-------------
RUGRAT is a tool that generates Java application benchmarks within specified constraints and predefined properties.
That is, you provide different properties (Number of classes, number of attributes, total lines of code, inheritance depth, etc.)
of your desired benchmark and RUGRAT will create a 100% Java application conforming to the specifications.


How to run?
------------
Running RUGRAT is easy. 

	1. Double-click on the executable jar file: RUGRAT.jar. This will open up the GUI. 
	2. Provide desired benchmark properties and constraints. Each property has a pop-up tip to help you understand the functionality.
	3. Click 'Generate' button (at the very bottom) to generate a program within specified constraints and properties.
	
You can also load existing configuration file and then modify the properties. For example, click 'Load existing config file' button to load 
the example configuration file (config.xml) in the distribution. This will auto-fill the properties provided in the configuration file. 
'Save current setting' button allows you to save a configuration file for later use.
	

Please note, generated program (java files) will be in 'TestPrograms' directory. 
Currently, all generated java files are under one default package: 'com.accenture.lab.carfast.test'



Known Bug(s)!
------------
Generated programs will not match the specified 'Total lines of code' property. This option works as an upper bound on the LOC.




For further information and bug report:
---------------------------------------
Please send your queries to:
	Mark Grechanik (mark.grechanik@accenture.com)
	Ishtiaque Hussain (ishtiaque.hussain@mavs.uta.edu)
	Christoph Csallner (csallner@uta.edu)



 	

