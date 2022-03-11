# BooleanExpressions
The following program takes in 2 boolean expressions as input. The program then solves the boolean expressions using a truth table, outputs the truth tables of 
each expression, and prints a statement of whether the 2 boolean expressions are equivalent.

##How to Compile/Run
Run the program by hitting run/play button, after the program has started, the user is prompted for the first boolean expression along with being provided the
requirements. After the first expression is entered, the user is prompted for the second boolean expression. After the second expression is entered, the 
program will then go through and compare the 2 expressions.

##Expected Input
The prompt for the user lets them know the requirements: only single letter variables (A, B or C), and operators (* / + / !). AND*, OR+, NOT!

##Conversion
The program first creates a truth table depending on how many variables were inputted (2 or 3). Then the program converts the original expression into
an expression that contains the values in each row of the truth table (going row by row). Once the values are plugged into the variables (depending on
which row of the truth table), the program divides the expression into 2 stacks, 1 for values and 1 for the different operations. The program goes through the
entire expression, and uses the applyOps method to solve the expression. The program looks for parenthesis as well and performs the operations within the
parenthesis accordingly. At the end of each row, the output is added to the last column of the truth table. 

##Output Format
Output includes the orginal expressions (including the variables), the truth tables of each under the expression, and then a statement of whether or not the
expressions were equivalent.
