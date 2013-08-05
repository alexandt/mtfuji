Ping Identity Interview: Take Home Coding Exercise
Engineer Level Exercise

System Requirements
-------------------------------------------------------------------------------
-Java 1.5
-Apache Ant version 1.7.1

Package Contents
-------------------------------------------------------------------------------
-This README file
-A build.xml file (for ant)
-A src directory with java source files

The Exercise
-------------------------------------------------------------------------------
Imagine that you are part of a software development team that is building an
enterprise server that processes tokens in order of their priority. 
As tokens come into the system they are assigned a priority - all priority 1
tokens are to be processed before those with priority 2, etc. Two tokens with 
the same priority should be processed in the same order in which they were 
received.

Each member of your team has to implement a different part of the token handling 
process. Your job is to assure that tokens are processed in the correct order
by storing incoming tokens and returning them based upon their priority and 
arrival order. You are to do this by implementing the TokenPrioritizer 
interface

Implement the interface by filling out the stub implementation in the
TokenPrioritizerImpl class. You can find this class in the src/ directory. 

Key Requirements/Givens:

* It is acceptable to track the tokens in memory -- don't worry about
maintaining state between server restarts.

* All tokens passed to the interface's implementation are guaranteed to be properly formed. 
Validation is done prior to the interface invocation, you may assume that no
duplicates will be passed into the object.

* Tokens with higher priority (a lower integer value) should be returned before 
those with lower priority.

* You may assume that the priorities will all be strictly positive - no zero or
negative numbers.

* Tokens with identical priority should be returned in the order in which they
were added to the TokenPrioritizer

You may begin this excercise by modifying the TokenPrioritizerImpl.java. 
You may add additional or more robust unit tests to the
PrioritizationTest.java -- this is considered to be extra credit.
  
If you run ant from the root of this package you will get a short listing of the
available targets in the build file -- one to compile, one to clean, and one to
execute a very basic test.  Note that currently the test target fails (as noted
by the exception stack trace).  At the very minimum, this test should pass when
you have completed the exercise.  However, the test is only a starting point.
Passing the included unit test does not guarantee your code satisfies the
requirements specified above.

Please return your implementation (or whole package) prior to the provided
deadline.
