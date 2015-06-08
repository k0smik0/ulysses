#!/bin/bash

( [ -z ${OLYMPUS_HOME} ] || [ ! -d ${OLYMPUS_HOME} ] ) && echo "OLYMPUS_HOME environment variable is not setted with right directory" && exit 1

[ ! -f jars.txt ] && echo "no jars.txt found" && exit 1

jars=$(cat jars.txt)

rm -f *.jar

for jar in ${jars}; do
   ln -fs ${OLYMPUS_HOME}/_libs/${jar} .
done
