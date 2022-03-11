/**
 *Destiny Haist
 * COSC 321
 * 2022/03/08
 * **/

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;


public class BooleanExpressions {

    public static void main(String[] args) {
        //scanner for 2 boolean expression inputs
        Scanner kb = new Scanner(System.in);

        //AND *, OR +, NOT !
        //max of 3 single letter variables
        System.out.print("Enter a boolean expression using a max of 3 different single letter variables (A,B or C) and operators (*/+/!): ");
        String boolOne = kb.nextLine();
        System.out.println();
        System.out.print("Input another boolean expression using same variables and operators: ");
        String boolTwo = kb.next();
        System.out.println();

        //output truth tables for both
        System.out.println(boolOne);
        ArrayList<ArrayList<Integer>> truthTableBoolOne = createTruthTable(boolOne);

        System.out.println(boolTwo);
        ArrayList<ArrayList<Integer>> truthTableBoolTwo = createTruthTable(boolTwo);

        //indicate whether expressions equivalent
        checkExpressionsSame(truthTableBoolOne, truthTableBoolTwo);
    }

    /*
     *
     * creates truth table of expression
     * @author Destiny Haist
     * @param expression
     */
    static ArrayList<ArrayList<Integer>> createTruthTable(String expression) {
        //check how many variables are in expression
        char temp;
        int numVars = 0;
        ArrayList variables = new ArrayList();
        for (int i = 0; i < expression.length(); i++){
            //check if char is a letter
            //https://stackoverflow.com/questions/4047808/what-is-the-best-way-to-tell-if-a-character-is-a-letter-or-number-in-java-withou
            temp = expression.charAt(i);
            if(Character.isLetter(temp) && !variables.contains(temp)) {
                variables.add(temp);
                numVars++;
            }

            if(numVars > 3) {
                System.out.println("Please only enter expressions with 2 or 3 single variables.");
                System.exit(0);
            }
        }

        //printing truth table
        //https://stackoverflow.com/questions/10723168/generating-truth-tables-in-java
        int rows = (int)Math.pow(2,numVars);

        //arraylist of arraylists
        //https://www.geeksforgeeks.org/arraylist-of-arraylist-in-java/amp/
        ArrayList<ArrayList<Integer>> truthTable = new ArrayList<>(rows);
        for(int i = 0; i < rows; i++){
            //each row
            ArrayList truthTableRow = new ArrayList<>();
            for (int j =numVars-1; j>= 0; j--) {
                truthTableRow.add((i/(int) Math.pow(2,j))%2);
            }
            evalBoolExpression(expression, truthTableRow);
            truthTable.add(truthTableRow);
        }

        printTruthTable(truthTable);
        return truthTable;
    }


    /*
     *
     * evaluates boolean expression
     * @author Destiny Haist & tutorialcup.com
     * @param expression, truthTableRow
     */
    //https://www.tutorialcup.com/interview/stack/expression-evaluation.htm
    static void evalBoolExpression(String expression, ArrayList truthTableRow){

        //plug in truth table values
        expression = expression.replace("A", String.valueOf(truthTableRow.get(0)));
        expression = expression.replace("B", String.valueOf(truthTableRow.get(1)));
        if(truthTableRow.size() == 3) {
            expression = expression.replace("C", String.valueOf(truthTableRow.get(2)));
        }
        char[] expArr = expression.toCharArray();


        //stack for values
        Stack<Integer> vals = new Stack<>();

        //stack for operators
        Stack<Character> ops = new Stack<>();

        for(int i=0; i<expArr.length; i++){
            //check if current char is whitespace
            if(expArr[i] == ' '){
                continue;
            }

            //if current char is a number, push to vals stack
            if(expArr[i] >= '0' && expArr[i] <= '1') {
                StringBuffer sbuf = new StringBuffer();

                while (i < expArr.length &&
                        expArr[i] >= '0' &&
                        expArr[i] <= '1')
                    sbuf.append(expArr[i++]);
                vals.push(Integer.parseInt(sbuf.
                        toString()));

                //decrease i to correct offset of skipping char after values (operator)
                i--;
            }else if(expArr[i] == '('){
                ops.push(expArr[i]);
            } else if(expArr[i] == ')'){    //if closing parenthesis, solve expression
                while (ops.peek() != '('){
                    vals.push(applyOp(ops.pop(), vals.pop(), vals.pop()));
                }
                ops.pop();
            }else if(expArr[i] == '!') {
                //if value to right of ! is 0, then 1 else if its 1, then 0
                int nextIndex = i+1;
                if(expArr[nextIndex] == '0') {expArr[nextIndex] = '1';}
                else if(expArr[nextIndex] == '1') {expArr[nextIndex] = '0';}
            }
            else if(expArr[i] == '+' || expArr[i] == '*') {  //current char operator
                //while tops of ops has same of greater precedence, apply operator to top two vals
                while (!ops.empty() && hasPrecedence(expArr[i], ops.peek())) {
                    vals.push(applyOp(ops.pop(), vals.pop(), vals.pop()));
                }

                //push current char to ops
                ops.push(expArr[i]);
            }
        }

        //whole expression parsed, apply remaining ops
        while(!ops.empty()) {
            vals.push(applyOp(ops.pop(), vals.pop(), vals.pop()));
        }
        //top of vals contains result, add to truthTableRow
        truthTableRow.add(vals.pop());
    }

    /*
     *
     * checks precedence of operations
     * @author tutorialcup.com
     * @param op1, op2
     */
    public static boolean hasPrecedence(
            char op1, char op2)
    {
        if (op2 == '(' || op2 == ')')
            return false;
        else return op1 != '*' && op1 != '+' && op1 != '!';
    }

    /*
     *
     * applies operations
     * @author tutorialcup.com
     * @param op, b, a
     */
    public static int applyOp(char op,
                              int b, int a)
    {
        switch (op)
        {
            case '+':
                if(a==1 || b == 1)
                    return 1;
                else
                    return 0;
            case '*':
                if(a==1 && b ==1)
                    return 1;
                else
                    return 0;
        }
        return 0;
    }


    /*
     *
     * prints truth table
     * @author Destiny Haist
     * @param truthTable
     */
    static void printTruthTable(ArrayList<ArrayList<Integer>> truthTable){
        for(int i = 0; i < truthTable.size(); i++){
            for (int j = 0; j < truthTable.get(i).size(); j++) {
                System.out.print(truthTable.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

    //function to check if expressions are equivalent
    //check if arraylists are equal

    /*
     *
     * checks if expressions are same
     * @author Destiny Haist
     * @param truthTable1, truthTable2
     */
    static void checkExpressionsSame(ArrayList<ArrayList<Integer>>truthTable1, ArrayList<ArrayList<Integer>> truthTable2) {
        //https://www.tutorialspoint.com/java-program-to-check-if-two-given-matrices-are-identical
        int flag = 1;
        int size = truthTable1.get(0).size();
        for(int i=0; i< size;i++){
            for(int j=0; j< size; j++){
                if(!truthTable1.get(i).get(j).equals(truthTable2.get(i).get(j))){
                    flag = 0;
                }
            }
        }
        if (flag == 1){
            System.out.println("Expressions are equivalent.");
        }else{
            System.out.println("Expressions are NOT equivalent.");
        }
    }
}

