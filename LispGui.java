//Saru Mehta LispGUI design
package PJ2;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class LispGui implements ActionListener {


public static void main(String[] args) {
LispGui gui = new LispGui();

}

//data fields of LispGui
private JFrame frame;
private JTextArea input; // initialize text field for the first fraction's numerator
private JTextArea output;//initialize text field for the second fraction's numerator
private JButton computeButton;

//Default Constructor
public LispGui() {
input= new JTextArea(5,40);
output = new JTextArea(5,40);

computeButton = new JButton("Calculate Expression");// create button with text
computeButton.addActionListener(this); 
frame = new JFrame("SimpleLispExpression Evaluator");//Frame's title
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//exits program when closing window
frame.setSize(600,400); // frame's size

frame.setLayout(new FlowLayout());
frame.add(new JLabel("Enter Valid Lisp Expression"));
frame.add(input); // add text field for user Input to the frame
frame.add(computeButton);// add Calculate button
frame.add(output);// add text field for computer Output to the frame
frame.setVisible(true);
}// end default constructor

public void actionPerformed(ActionEvent event) {

// get inputted text from the "input" text field and store it as String
// convert String into a an integer 
String userInput = input.getText();

// Calculations depending on the expression inputted by user
// Calls the SimpleLispExpressionEvaluator class, which is also in package PJ2

SimpleLispExpressionEvaluator expr= new SimpleLispExpressionEvaluator(userInput);
try{
output.setText( ""+ expr.evaluate() );
}catch(Exception e){
	output.setText(e.getMessage());

}
}
}//end LispGui