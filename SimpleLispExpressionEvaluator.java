package PJ2;
import java.util.*;

public class SimpleLispExpressionEvaluator
{
    // Current input Lisp expression
    private String inputExpr;

    // Main expression stack & current operation stack, see algorithm in evaluate()
    private Stack<Object> exprStack;
    private Stack<Double> currentOpStack;


    // Default constructor
    // Calls second constructor and sets inputExpr to ""
    public SimpleLispExpressionEvaluator()
    {
	this("");
    }

    //Second Constructor
    // set inputExpr to inputExpression 
    // create stack objects
    public SimpleLispExpressionEvaluator(String inputExpression) 
    {
	inputExpr= inputExpression;
	exprStack= new Stack<Object>();
	currentOpStack= new Stack<Double>();
    }

    // set inputExpr to inputExpression 
    // clear stack objects by assigning the references to stacks to new stack objects
    public void reset(String inputExpression) 
    {
	inputExpr= inputExpression;
	exprStack= new Stack<Object>();
	currentOpStack= new Stack<Double>();
    }

    // This function evaluate current operator with its operands
    // See complete algorithm in evaluate()
    //
    // Main Steps:
    // 		Pop operands from exprStack and push them onto 
    // 			currentOpStack until you find an operator
    //  	Apply the operator to the operands on currentOpStack
    //          Push the result into exprStack
    //
    private void evaluateCurrentOperation()
    {	
	
	while(exprStack.peek().getClass().getName().equals("java.lang.Double")){

		currentOpStack.push((Double)exprStack.pop());
		if(exprStack.size()==0){		
		throw new SimpleLispExpressionEvaluatorException("Expression is missing an operand, check beginning of expression");
		}

	}
	if(currentOpStack.empty()){
		throw new SimpleLispExpressionEvaluatorException("No numbers are entered in the expression");
	}
       	
	Character operator= (Character)exprStack.pop();
	
	switch(operator){
	      case '+':    add();
			     break;

		case '-':    subtract();
				break;

		case '*':
				multiply();
				break;
		case '/':
				divide();
				break;

		default: 	break;  
	}
    }
    private void add(){
	
	Double op1= currentOpStack.pop();
	if(currentOpStack.empty()){
		op1= op1;
	}
	while(!currentOpStack.empty()){
		Double op2= currentOpStack.pop();
		op1= op1 + op2;
	}
	
	exprStack.push(op1);


    }
    private void subtract(){
	Double op1= currentOpStack.pop();
	if(currentOpStack.empty()){
		op1= - op1;
	}
	while(!currentOpStack.empty()){
		Double op2= currentOpStack.pop();
		op1= op1 - op2;
	}
	
	exprStack.push(op1);

    }
    private void multiply(){
	
	Double op1= currentOpStack.pop();
	if(currentOpStack.empty()){
		op1= op1;
	}
	while(!currentOpStack.empty()){
		Double op2= currentOpStack.pop();
		op1= op1 * op2;
	}
	
	exprStack.push(op1);


    }
    private void divide(){
	
	Double op1= currentOpStack.pop();
	if(currentOpStack.empty()){
		op1= 1/op1;
	}
	while(!currentOpStack.empty()){
		Double op2= currentOpStack.pop();
		op1= op1 / op2;
	}
	if(op1== Double.POSITIVE_INFINITY){
		throw new SimpleLispExpressionEvaluatorException("Cannot divide by zero");
	}
	exprStack.push(op1);
		

    }
    /**
     * This function evaluates current Lisp expression in inputExpr
     * It return result of the expression 
     *
     * The algorithm:  
     *
     * Step 1   Scan the tokens in the string.
     * Step 2		If you see an operand, push operand object onto the exprStack
     * Step 3  	    	If you see "(", next token should be an operator
     * Step 4  		If you see an operator, push operator object onto the exprStack
     * Step 5		If you see ")", do steps 6,7,8 in evaluateCurrentOperation() :
     * Step 6			Pop operands and push them onto currentOpStack 
     * 					until you find an operator
     * Step 7			Apply the operator to the operands on currentOpStack
     * Step 8			Push the result into exprStack
     * Step 9    If you run out of tokens, the value on the top of exprStack is
     *           is the result of the expression.
     */
    public double evaluate()
    {
      
        // use scanner to tokenize inputExpr
        Scanner inputExprScanner = new Scanner(inputExpr);
        
        // Use zero or more white space as delimiter,
        // which breaks the string into single character tokens
        inputExprScanner = inputExprScanner.useDelimiter("\\s*");

        // Step 1: Scan the tokens in the string.
        while (inputExprScanner.hasNext())      
        {
		
     	    // Step 2: If you see an operand, push operand object onto the exprStack
            if (inputExprScanner.hasNextInt())
            {
                // This forces scanner to grab all of the digits
                // Otherwise, it will just get one char
               String dataString = inputExprScanner.findInLine("\\d+");
		    
		  exprStack.push(new Double(dataString));
		if(!inputExprScanner.hasNext()){
			throw new SimpleLispExpressionEvaluatorException("This is not a correct expression, try inserting a closing )");
		}
		 
            }else
            {
                // Get next token, only one char in string token
                String aToken = inputExprScanner.next();
                char item = aToken.charAt(0);
              
                switch (item)
                {
					case '(':  
						  if(!inputExprScanner.hasNext()){
							throw new SimpleLispExpressionEvaluatorException("This is not a correct expression");
						  }
						    aToken= inputExprScanner.next();
						    item= aToken.charAt(0);
						    switch(item)
						    {
							case '+': case'-':case'*':case'/':
								exprStack.push(item);
							       break;
						    default: 
							throw new SimpleLispExpressionEvaluatorException("Expression is missing a Valid operator before the "+ item);
						    }
							break;
			
					case ')':  	evaluateCurrentOperation();
							break;
					default: 
							throw new SimpleLispExpressionEvaluatorException(item + " is not a VALID token OR there needs to be a ( before " + item);
							

			
     		    // Step 3: If you see "(", next token should be an operator
     		    // Step 4: If you see an operator, push operator object onto the exprStack
     		    // Step 5: If you see ")"  do steps 6,7,8 in evaluateCurrentOperation() :
                    //default:  // error
                        //throw new SimpleLispExpressionEvaluatorException(item + " is not a legal expression operator");
                } // end switch
            } // end else
        } // end while
        
        // Step 9: If you run out of tokens, the value on the top of exprStack is
        //         is the result of the expression.
        //
        //         return result
	if (exprStack.size()!=1){
		throw new SimpleLispExpressionEvaluatorException("Expression is missing a ), check end of expression");
	}
	return (Double)exprStack.pop();
    }
    

    //=====================================================================

    // This static method is used by main() only
    private static void evaluateExprTest(String s, SimpleLispExpressionEvaluator expr)
    {
        Double result;
        System.out.println("Expression " + s);
	expr.reset(s);
        result = expr.evaluate();
        System.out.printf("Result %.2f\n", result);
        System.out.println("-----------------------------");
    }

    // define few test cases, exception may happen 
    public static void main (String args[])
    {
        SimpleLispExpressionEvaluator expr= new SimpleLispExpressionEvaluator();
        String test1 = "(+ (- 6) (* 2 3 4) (/ (+ 3) (* 1)(- 2 3 1)))";
        String test2 = "(+ (- 632) (* 21 3 4) (/ (+ 32) (* 1) (- 21 3 1)))";
        String test3 = "(+ (/ 2) (* 2) (/ (+ 1) (+ 1) (- 2 1 )))";
        String test4 = "(+ (/2))";
        String test5 = "(+ (/2 3 0))"; // Divide by zero
        String test6 = "(+ (/ 2) (* 2) (/ (+ 1) (+ 3) (- 2 1 ))))";
	evaluateExprTest(test1, expr);
	evaluateExprTest(test2, expr);
	evaluateExprTest(test3, expr);
	evaluateExprTest(test4, expr);
	evaluateExprTest(test5, expr);
	evaluateExprTest(test6, expr);
    }
}
