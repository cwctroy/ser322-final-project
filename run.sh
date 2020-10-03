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
  DBNAME=""
  DBUSER=""
  DBPSWD=""
  DBDRIVER=""

  PROGROMNAME="DBDriver"

}

compile() {
  javac "$SRCDIR"/*.java 

}

copy_compiled_files() {
  cp "$SRCDIR"/*.class "$CLASSDIR"/
}


run() {
java -cp classes "ser322.DBDriver" "arg1 arg2"
}

## MAIN ##
setup
compile
copy_compiled_files
run

