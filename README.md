# 677 Lab 2

This is the Git repo for 677 Lab 2. See http://lass.cs.umass.edu/~shenoy/courses/spring19/labs/lab2.html for a description of the lab. The lab is due on April 3, 23:55 hrs. Prior to submitting your project, replace this README file with the one that explains how to setup and run your code. Be sure to provide enough details fo us to run it in order to grade it.

On CatalogServer machine run:
cd /target
java -c java -cp com.Lab2-1.0-SNAPSHOT-jar-with-dependencies.jar main.java.CatalogServer <../test/testCase.csv>

On OrderServer machine run:
cd /target
java -c java -cp com.Lab2-1.0-SNAPSHOT-jar-with-dependencies.jar main.java.OrderServer <CatalogServer IP Address>
  
On FrontEndServerServer machine run:
cd /target
java -c java -cp com.Lab2-1.0-SNAPSHOT-jar-with-dependencies.jar main.java.FrontEndServer <CatalogServer IP Address> <OrderServer IP Address>

On Client machine run:
cd /target
java -c java -cp com.Lab2-1.0-SNAPSHOT-jar-with-dependencies.jar main.java.Clinet <FrontEndServer IP Address>
