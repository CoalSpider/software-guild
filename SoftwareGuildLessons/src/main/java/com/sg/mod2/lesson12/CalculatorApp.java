/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mod2.lesson12;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author Ben Norman
 */
public class CalculatorApp extends Application {

    private GridPane buttons = new GridPane();
    private Label inputLabel = new Label("");
    private ListView<Label> previousOutput = new ListView<>();
    private HBox root = new HBox(buttons, previousOutput);
    private Scene scene = new Scene(root, 700, 500);

    private static final char delimeter = ' ';
    private StringBuilder expressionBuilder = new StringBuilder();

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        primaryStage.setScene(scene);
        primaryStage.show();

        initButtons();
        initOutputPane();
    }

    public static void main(String[] args) {
        InputHandler.main(args);
        launch(args);
    }

    private void initButtons() {
        Button button0 = new Button(ButtonTypes.ZERO.val);
        Button button1 = new Button(ButtonTypes.ONE.val);
        Button button2 = new Button(ButtonTypes.TWO.val);
        Button button3 = new Button(ButtonTypes.THREE.val);
        Button button4 = new Button(ButtonTypes.FOUR.val);
        Button button5 = new Button(ButtonTypes.FIVE.val);
        Button button6 = new Button(ButtonTypes.SIX.val);
        Button button7 = new Button(ButtonTypes.SEVEN.val);
        Button button8 = new Button(ButtonTypes.EIGHT.val);
        Button button9 = new Button(ButtonTypes.NINE.val);
        Button buttonA = new Button(ButtonTypes.ADD.val);
        Button buttonS = new Button(ButtonTypes.SUB.val);
        Button buttonM = new Button(ButtonTypes.MULT.val);
        Button buttonD = new Button(ButtonTypes.DIV.val);
        Button buttonDt = new Button(ButtonTypes.DOT.val);
        Button buttonLP = new Button(ButtonTypes.LEFT_PAREN.val);
        Button buttonRP = new Button(ButtonTypes.RIGHT_PAREN.val);
        Button buttonEn = new Button(ButtonTypes.ENTER.val);
        Button buttonEx = new Button(ButtonTypes.EXIT.val);
        Button buttonDel = new Button(ButtonTypes.DELETE.val);
        Button buttonClr = new Button(ButtonTypes.CLEAR.val);

        Button[] digits = new Button[]{button0, button1, button2, button3, button4, button5, button6, button7, button8, button9, buttonDt};
        Button[] operators = new Button[]{buttonA, buttonS, buttonM, buttonD, buttonLP, buttonRP};
        Button[] specials = new Button[]{buttonEn, buttonEx, buttonDel, buttonClr};
        
        Font font = new Font(25);

        for (Button b : digits) {
            b.setFont(font);
            b.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            b.setOnMouseClicked(event -> {
                expressionBuilder.append(b.getText());
                inputLabel.setText(expressionBuilder.toString());
            });
        }

        for (Button b : operators) {
            b.setFont(font);
            b.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            b.setOnMouseClicked(event -> {
                // TODO: dont allow entry of extra operators except parens if there is one at the end
                expressionBuilder.append(delimeter);
                expressionBuilder.append(b.getText());
                expressionBuilder.append(delimeter);
                inputLabel.setText(expressionBuilder.toString());
            });
        }
        
        for(Button b : specials){
            b.setFont(font);
            b.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        }

        // when user clicks enter to confirm input
        buttonEn.setOnMouseClicked(event -> {
            String currS = expressionBuilder.toString().trim();
            System.out.println("currS="+currS);
            String postFix = InfixToPostfixConverter.toPostfix(currS).trim();
            System.out.println("postFix="+postFix);
            BigDecimal result = MathematicalExpressionParser.parse(postFix);
            previousOutput.getItems().add(previousOutput.getItems().size()-1,new Label(currS));
            expressionBuilder.delete(0, expressionBuilder.length());
            expressionBuilder.append(result.toPlainString());
            inputLabel.setText(expressionBuilder.toString());
        });

        // when use clicks the exit button
        buttonEx.setOnMouseClicked(event -> {
            System.exit(0);
        });

        // when user clicks the delete button just delete one char
        buttonDel.setOnMouseClicked(event -> {
            expressionBuilder.deleteCharAt(expressionBuilder.length());
            inputLabel.setText(expressionBuilder.toString());
        });

        // when user clicks the clear button delete all current input
        buttonClr.setOnMouseClicked(event -> {
            expressionBuilder.delete(0, expressionBuilder.length());
            inputLabel.setText(expressionBuilder.toString());
        });

        // grid sizing
        for (int i = 0; i < 4; i++) {
            buttons.getColumnConstraints().add(new ColumnConstraints(10, 50, 100, Priority.ALWAYS, HPos.CENTER, true));
        }
        for (int i = 0; i < 6; i++) {
            buttons.getRowConstraints().add(new RowConstraints(10, 50, 100, Priority.SOMETIMES, VPos.CENTER, true));
        }

        // add buttons to grid
        buttons.addRow(0, buttonLP, buttonRP, buttonDel, buttonClr);
        buttons.addRow(1, button7, button8, button9, buttonD);
        buttons.addRow(2, button4, button5, button6, buttonM);
        buttons.addRow(3, button1, button2, button3, buttonS);
        buttons.addRow(4, button0, buttonDt, buttonEn, buttonA);
        buttons.add(buttonEx, 0, 5, 4, 1);
    }

    private void initOutputPane() {
        previousOutput.getItems().add(inputLabel);

        root.setStyle("-fx-border-color: black");
        buttons.setStyle("-fx-border-color: red");
        inputLabel.setStyle("-fx-border-color: green");
        inputLabel.setPadding(new Insets(20, 20, 20, 20));
        buttons.setPrefWidth(scene.getWidth() / 2);
        // TODO:
        // output shows previous calculations and the results as column of labels, listview? is it a listview?
        // the current line will be a different color or something
        // this will be placed to the left of the button grid probably in a hbox
    }
}

enum ButtonTypes {
    ZERO("0"), ONE("1"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"), EIGHT("8"), NINE("9"), ADD("+"), SUB("-"), MULT("*"), DIV("/"), DOT("."), LEFT_PAREN("("), RIGHT_PAREN(")"), ENTER("="), EXIT("Exit"), DELETE("<--"), CLEAR("AC");
    String val;

    ButtonTypes(String val) {
        this.val = val;
    }
}

enum Operator {
    ADD(1, "+"), SUB(2, "-"), MULT(3, "*"), DIV(4, "/");
    int precedence;
    String val;

    Operator(int precedence, String val) {
        this.precedence = precedence;
        this.val = val;
    }

    static Operator fromString(String s) {
        for (Operator op : Operator.values()) {
            if (op.val.equals(s)) {
                return op;
            }
        }
        return null;
    }

    static boolean stringContainsOperator(String s) {
        // break if input doesnt contain operator
        for (Operator op : Operator.values()) {
            if (s.contains(op.val)) {
                return true;

            }
        }
        return false;
    }
}

class InputHandler {

    public static void main(String[] args) {
        System.out.println(InfixToPostfixConverter.toPostfix("123 / 9"));
        System.out.println(MathematicalExpressionParser.parse("123 9 /"));
    }
}

// hidden class in expression parser?
class InfixToPostfixConverter {

    public static String toPostfix(String infix) {
        return shuntingYardAlgorithm(infix);
    }

    // TODO: deal with decimals
    /**
     * from http://eddmann.com/posts/shunting-yard-implementation-in-java/ *
     */
    private static String shuntingYardAlgorithm(String infix) {
        StringBuilder output = new StringBuilder();
        Deque<String> stack = new LinkedList<>();

        for (String token : infix.split("\\s")) {
            // operator
            if (Operator.fromString(token) != null) {
                while (!stack.isEmpty() && isHigherPrecedence(token, stack.peek())) {
                    output.append(stack.pop()).append(' ');
                }
                stack.push(token);

                // left parenthesis
            } else if (token.equals("(")) {
                stack.push(token);

                // right parenthesis
            } else if (token.equals(")")) {
                while (!stack.peek().equals("(")) {
                    output.append(stack.pop()).append(token).append(' ');
                }
                stack.pop();

                // digit
            } else {
                output.append(token).append(' ');
            }
        }

        while (!stack.isEmpty()) {
            output.append(stack.pop()).append(' ');
        }

        return output.toString();
    }

    /**
     * *@return if precedence of op2 >= precedence of op1
     */
    private static boolean isHigherPrecedence(String op1, String op2) {
        Operator ops2 = Operator.fromString(op2);
        Operator ops1 = Operator.fromString(op1);
        return ops2 != null && ops2.precedence >= ops1.precedence;
    }

}

class MathematicalExpressionParser {

    private static final MathContext mc = new MathContext(25, RoundingMode.HALF_UP);

    public static BigDecimal parse(String postfixExpression) throws NumberFormatException {
        Stack<BigDecimal> operands = new Stack<>();

        for (String s : postfixExpression.split("\\s")) {
            if (isOperator(s)) {
                if (operands.size() < 2) {
                    throw new RuntimeException("malformed expression @ " + s);
                }
                BigDecimal result = operands.pop();
                while (operands.empty() == false) {
                    BigDecimal op = operands.pop();
                    switch (Operator.fromString(s)) {
                        case ADD:
                            result = op.add(result, mc);
                            break;
                        case SUB:
                            result = op.subtract(result, mc);
                            break;
                        case MULT:
                            result = op.multiply(result, mc);
                            break;
                        case DIV:
                            // TODO: divide by zero error
                            result = op.divide(result, mc);
                            break;
                        default:
                            throw new IllegalArgumentException("unknown operator");
                    }
                }
                operands.push(result);
            } else {
                operands.push(new BigDecimal(s));
            }
        }

        return operands.pop();
    }

    private static boolean isOperator(String s) {
        return Operator.fromString(s) != null;
    }

}
