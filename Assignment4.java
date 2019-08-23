package Assignment4;

/**
 * Name - Ankita Das
 * Class - COSC 2336.001
 * Date - Nov 05, 2017
 * Assignment 4 - Interpreter for EZ language
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class Assignment4 {

	static int alphabet[] = new int[26];
	static Proc[] procNames = new Proc[10];
	static Proc currProc;
	static int countProcedure = 0;
	Stack<Character> operStack = new Stack<Character>();
	
	/**
	 * Method to check if the character in the expression is an operator
	 * @param c: character of the mathematical expression
	 * @return boolean value if the character is an operator
	 */
	private static boolean isOperator(char c) {
		return c == '+' || c == '*';
	}

	/**
	 * Method to decide the precendence of each operator
	 * @param ch: chracter of the mathematical expression
	 * @return: returning the precendence value of an operator or group of operators
	 */
	private static int getPrecedence(char ch) {
		switch (ch) {
		case '+':
			return 1;
		case '*':
			return 2;
		}
		return -1;
	}

	/**A utility function to check if the given character is operand
	 * @param ch: string mathematical expression which can have numeric as well as variables
	 * @return: boolean value if the character is an operand
	 */
	private static boolean isOperand(char ch) {
		return (ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'Z');
	}

	/**
	 *Scan the infix expression from left to right.
	 *If the scanned character is an operand, ouput it.
	 *Else,
	 *If the precedence of the scanned operator is greater than 
	 *the precedence of the operator in the stack(or the stack is empty), push it.
	 *Else, Pop the operator from the stack until the precedence of the scanned operator is less-equal to the 
	 *precedence of the operator residing on the top of the stack. Push the scanned operator to the stack.
	 * @param infix: the mathematical expression in infix form
	 * @return: string postfix expression
	 */
	public static String convertToPostfix(String infix) {
		Stack<Character> stack = new Stack<Character>();
		StringBuffer postfix = new StringBuffer(infix.length());
		char c;
		for (int i = 0; i < infix.length(); i++) {
			c = infix.charAt(i);

			if (isOperand(c)) {
				postfix.append(c);
			} else if (isOperator(c)) // operator encountered
			{
				if (!stack.isEmpty() && getPrecedence(c) <= getPrecedence(stack.peek())) {
					postfix.append(stack.pop());
				}
				stack.push(c);
			}
		}

		while (!stack.isEmpty()) {
			postfix.append(stack.pop());
		}
		return grow(postfix.toString());
	}
	
	/**
	 * The converted mathematical expression in string format is sent as an input here
	 * This method evaluates the postfix expression
	 * @param exp: postfix string mathematical expression
	 * @return evaluated answer
	 */
	public static int postfixEvaluate(String exp) {
	 	Stack<Integer> s = new Stack<Integer> ();
		Scanner tokens = new Scanner(exp);
		
		while (tokens.hasNext()) {
			String nextToken = tokens.next();
			int varAscii = (int) nextToken.toCharArray()[0];
			if (varAscii >= 48 && varAscii <= 57) {
				s.push(Integer.parseInt(nextToken));
			}else if(varAscii >= 65 && varAscii <= 90) {
				s.push(alphabet[varAscii - 65]);
			}else {
				int num2 = s.pop();
				int num1 = s.pop();
				String op = nextToken;
				
				if (op.equals("+")) {
					s.push(num1 + num2);
				} else if (op.equals("*")) {
					s.push(num1 * num2);
				}
				//  "+", "*"
			}
		}
		return s.pop();
    }

	/**
	 * Inserted space after each operator and operand
	 * @param text: String expression
	 * @return String expression with space between each character
	 */
	public static String grow(String text) {
		String ret = "";                     
		for (char ch : text.toCharArray()) { 
			if (ch != ' ')					
				ret += ch + " ";             
		}

		return ret.substring(0, ret.length() - 1); 
	}
	

	/**
	 * Runs the procedure whose index has been provided
	 * Implicit stack implementation as recursion has been followed in this method
	 * @param index is the index of the required command
	 */
	static void runProcedure(int index) {
		Proc temp = procNames[index];
		String[] tempComm = temp.getCommand();

		for (int i = 0; i < tempComm.length; i++) {
			if (tempComm[i] == null) {
				break;
			}

			/*
			 * Handling different commands 
			 * In copy command ascii code of variables have been
			 * used to map in the alphabet array 
			 * In echo command printing the expression and
			 * putting value of the variable 
			 * In call the asked command has been called via its index(Recursion - implicit stack implementation)
			 */
			String command = tempComm[i].substring(0, 4);
			String value = tempComm[i].substring(5);
			if (command.equals("copy")) {
				String leftVar = value.substring(0, 1);
				int ascii = (int) leftVar.toCharArray()[0];
				String rightOper = value.substring(2);
				alphabet[ascii - 65] = postfixEvaluate(convertToPostfix(rightOper));

			} else if (command.equals("echo")) {
				char[] valueChar = value.toCharArray();

				for (int m = 0; m < valueChar.length; m++) {
					int valueAscii = (int) valueChar[m];
					if (valueAscii >= 65 && valueAscii <= 90) {
						System.out.print(alphabet[valueAscii - 65]);
					} else {
						System.out.print(valueChar[m]);
					}
				}
				System.out.print("\n");
			} else if (command.equals("call")) {
				int ind = findProcIndex(value);
				runProcedure(ind);       //calling methods recursively (implicit stack)
			}

		}
	}
	

	/**
	 * Finding the index of the procedure name
	 * @param n : is the name of the procedure
	 * @return index of the procedure name
	 */
	static int findProcIndex(String n) {
		int lengthProcArray = procNames.length;
		for (int i = 0; i < lengthProcArray; i++) {
			if (procNames[i].getName().equals(n)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Main method to execute the input set of commands
	 * @param args:Taking the input from input.txt
	 * @throws FileNotFoundException: if the input file is not found
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Scanner input = new Scanner(new File(args[0]));
		String inputStr = "";
		String command = "";
		String value = "";
		int length = 0;
		while (input.hasNextLine()) {
			inputStr = input.nextLine();
			length = inputStr.length();
			command = inputStr.substring(0, 4);
			if (command.equals("proc")) {
				value = inputStr.substring(5, length);
				currProc = new Proc(value); // Declared object for every new procedure name
			} else if (command.equals("stop")) {
				procNames[countProcedure] = currProc; // Inserting the procedure line by line
				countProcedure++;
			} else {
				currProc.addCommand(inputStr);
			}
		}
		int index = findProcIndex("main");
		runProcedure(index);
		input.close();
	}

}
