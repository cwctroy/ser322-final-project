## DATABASE SETUP ""
To set up, run sports.sql in your local instance (such as mysql workbench) to create the schema.
You can then execute run.sh to begin the interactive session.

## EXECUTION VIA RUN.SH ##
This program can be run via the run.sh script assuming the system supports bash. 
The database credentials can be edited in run.sh but the assumption is that there 
exists a database named sports with user root/root. If your database user has different credentials, you will nee to edit
the values under DBINFO in run.sh

## MANUAL COMPILATION ##
You can manually compile and execute the code. The arguments to DBDriver are in the same format as SER 322 assignment 5, 
`java -cp "lib/mysql-connector-java-8.0.21.jar:"classes ser322.DBDriver URL DBUSER DBPSWD DBDRIVER`
The program will dynamically prompt the user for arguments to the various methods.


