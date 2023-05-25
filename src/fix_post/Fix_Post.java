package fix_post;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class Fix_Post {

    // Start 1 *****************************************************************
    // convert string infix expression (separated by a space) into a linked list of strings
    public static LinkedList<String> convertIntoLL(String expression) { // "( 1 + 2 ) * 3"
        LinkedList<String> linkedList = new LinkedList<>(); // linked list to sort the tokens
        String[] tokens = expression.split(" "); // convert string to array of strings ["(", "1", "+", ...] 
        for (String token : tokens) { // loop to add tokens into linked list
            linkedList.add(token);
        }
        return linkedList;
    }        
    // End 1 *******************************************************************
    
    // Start 2 *****************************************************************
    // determine if the infix expression is valid
    public static boolean isValid(LinkedList<String> expression) {
        Stack<String> stack = new Stack<>(); // empty stack to sort ( ) 
        for (String token : expression) {
            if (token.equals("(")) { // check if the token is (
                stack.push(token);
            } else if (token.equals(")")) { // check if the token is )
                if (stack.isEmpty() || !(stack.pop().equals("("))) { // check if the ) has (
                    return false;
                }
            }
        }
        return stack.isEmpty(); 
    }
    // End 2 *******************************************************************
    
    // Start 3 *****************************************************************
    // converts an infix expression into a postfix expression
    public static LinkedList<String> infixToPostfix(LinkedList<String> expression) {
        Stack<String> stack = new Stack<>(); // operators
        LinkedList<String> postfixExpression = new LinkedList<>(); // num or digits

        for (String token : expression) {
            if (isOperand(token)) { // num or digit
                postfixExpression.add(token); // add the token to thw linked list
            } else if (token.equals("(")) { // if the token equal ( add it to stack
                stack.push(token);
            } else if (token.equals(")")) { // if the token equal ) check if stack is not empty and stack.peek() equal (
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    postfixExpression.add(stack.pop());
                }
                stack.pop(); // Discard the opening parenthesis
            } else { // this is operator
                while (!stack.isEmpty() && hasHigherPrecedence(token, stack.peek())) {
                    postfixExpression.add(stack.pop());
                }
                stack.push(token);
            }
        }

        while (!stack.isEmpty()) {
            postfixExpression.add(stack.pop());
        }

        return postfixExpression;
    }

    private static boolean isOperand(String token) {
        // Check if the token is an operand (number or variable) 
        return Character.isLetterOrDigit(token.charAt(0));
    }

    private static boolean hasHigherPrecedence(String operator1, String operator2) { // اولويات العمليات الحسابية
        // Check if operator1 has higher precedence than operator2
        int precedence1 = getPrecedence(operator1);
        int precedence2 = getPrecedence(operator2); 
        return precedence1 <= precedence2;
    }

    private static int getPrecedence(String operator) {
        // Return the precedence of an operator
        switch (operator) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            case "^": // to the power
                return 3;
            default:
                return -1; // Invalid operator
        }
    }
    // End 3 *******************************************************************

    // Start 4 *****************************************************************
    // evaluates the postfix expression
    public static double evaluatePostfix(LinkedList<String> expression) {
      Stack<Double> stack = new Stack<>(); // empty stack to sort the result
      for (String token : expression) { // example "1" 1.0
        if (token.matches("\\d+")) { //  token.matches("0") || token.matches("1") || token.matches("2") || token.matches("3") || token.matches("4") || token.matches("5") || token.matches("6") || token.matches("7") || token.matches("8") || token.matches("9")
          stack.push(Double.parseDouble(token)); // parseDouble convert the string to double
        } 
        else { // it is operator
          double operand2 = stack.pop();
          double operand1 = stack.pop();
          double result = performOperation(token, operand1, operand2);
          stack.push(result);
        }
      }
      return stack.pop();
    }

    // توضح أولويات العمليات الحسابية
    private static double performOperation(String operator, double operand1, double operand2) {
      switch (operator) {
        case "+":
          return operand1 + operand2;
        case "-":
          return operand1 - operand2;
        case "*":
          return operand1 * operand2;
        case "/":
          return operand1 / operand2;
        default: 
          throw new IllegalArgumentException("Invalid operator: " + operator);
      }
    }
    // End 4 *******************************************************************
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter an infix expression separated by spaces: ");
        String infixExpression = input.nextLine(); // "( 1 + 2 ) * 3"
        LinkedList<String> infixLL = convertIntoLL(infixExpression);

        if (!isValid(infixLL)) {
            System.out.println("Invalid expression");
            return;
        }

        LinkedList<String> postfixLL = infixToPostfix(infixLL);
        System.out.println("Postfix expression: " + postfixLL.toString());

        double result = evaluatePostfix(postfixLL);
        System.out.println("Result: " + result);   
    }    
}
