#! /bin/bash
#
# This script compiles all java files, moves them into the classes dir
# and then calls main with two sample args
# 


setup() {

  ## Path info ##
  DIR=$(pwd)
  SRCDIR="$DIR/src/ser322"
  CLASSDIR="$DIR/classes/ser322"
  
  ## DB info ##
  URL="jdbc:mysql://localhost/sports?autoReconnect=true&useSSL=false&useLegacyDatetimeCode=false&&serverTimezone=America/New_York"
  DBUSER="root"
  DBPSWD="root"
  DBDRIVER="com.mysql.cj.jdbc.Driver"

  PROGROMNAME="DBDriver"

}

compile() {
  javac "$SRCDIR"/*.java 

}

copy_compiled_files() {
  cp "$SRCDIR"/*.class "$CLASSDIR"/
}


run() {
java -cp "lib/mysql-connector-java-8.0.21.jar:"classes "ser322.DBDriver" "$URL" "$DBUSER" "$DBPSWD" "$DBDRIVER"
}

## MAIN ##
setup
compile
copy_compiled_files
run

