package com.example.calculator;

//SIMPLE CALCULATOR
//This program creates a simple calculator with numbers 1-9, four operations + - * /, a decimal button and a clear button and a = button.
//Calculator.java handles the gui, layout and display logic. It also contains the class ButtonHandler which handles most of the logic of
// when to display a number or call the Calculate class to do the required math.
//The Calculate class handles all the math and will return the result.
//The Calculator class uses styleSheet.css for most of the styling of the buttons, background, textfield.
//Both Calculator and ButtonHandler classes implement the Output interface which is mostly used for the TextField called output,
//output is used to display numbers.

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Calculator extends Application implements Output {

    @Override
    public void start(Stage stage) {
        VBox wrapper = new VBox(); //Will contain the top and bottom Vbox's
        VBox top = new VBox();
        VBox bottom = new VBox();

        //output is the TextField which will display the numbers for the calculator. ButtonHandler manipulates the text displayed by changing the output text.
        output.setFont(Font.font("Impact", FontWeight.BOLD, FontPosture.ITALIC, 40));
        output.setEditable(false);
        top.getChildren().add(output);
        top.setPadding(new Insets(20));
        top.setAlignment(Pos.CENTER);

        Button decimal = new Button(".");
        Button zero = new Button("0");
        Button one = new Button("1");
        Button two = new Button("2");
        Button three = new Button("3");
        Button four = new Button("4");
        Button five = new Button("5");
        Button six = new Button("6");
        Button seven = new Button("7");
        Button eight = new Button("8");
        Button nine = new Button("9");
        Button add = new Button("+");
        Button subtract = new Button("-");
        Button divide = new Button("/");
        Button multiply = new Button("*");
        Button equals = new Button("=");
        Button clear = new Button("AC");

        ButtonHandler buttonHandler = new ButtonHandler();
        //An array and a for loop to set all buttons except clear to be handled by ButtonHandler. Reduces repetitive code.
        Button setOnActionArray[] = {
                decimal, zero, one, two, three, four, five, six, seven, eight, nine,
                add, subtract, divide, multiply, equals
        };
        for (Button button : setOnActionArray) {
            button.setOnAction(buttonHandler);
        }
        clear.setOnAction(e -> {
            output.setText("");
            calculator.clear();
            buttonHandler.decimalFlag = false;
            buttonHandler.resetText = false;
            buttonHandler.flag = 0;
        });

        HBox row1 = new HBox();
        row1.getChildren().add(clear);
        row1.setAlignment(Pos.CENTER_RIGHT);
        row1.setPadding(new Insets(0, 25, 10, 0));
        HBox row2 = new HBox();
        HBox row3 = new HBox();
        HBox row4 = new HBox();
        HBox row5 = new HBox();
        HBox styleArray[] = {row2, row3, row4, row5};
        for (HBox row : styleArray) {
            row.setAlignment(Pos.CENTER);
            row.setPadding(new Insets(10));
            row.setSpacing(20);
        }

        row2.getChildren().addAll(seven, eight, nine, divide);
        row3.getChildren().addAll(four, five, six, multiply);
        row4.getChildren().addAll(one, two, three, subtract);
        row5.getChildren().addAll(decimal, zero, add, equals);

        bottom.getChildren().addAll(row1, row2, row3, row4, row5);
        wrapper.getChildren().addAll(top, bottom);
        wrapper.setAlignment(Pos.CENTER);

        clear.setStyle("-fx-pref-height: 30px; -fx-background-color: #51458a");
        divide.setStyle("-fx-background-color: #e05353");
        multiply.setStyle("-fx-background-color: #e05353");
        subtract.setStyle("-fx-background-color: #e05353");
        add.setStyle("-fx-background-color: #e05353");
        equals.setStyle("-fx-background-color: #e05353");

        Scene scene = new Scene(wrapper, 350, 450);
        scene.getStylesheets().add(getClass().getResource("styleSheet.css").toString());
        stage.setTitle("Simple Calculator!");
        stage.setScene(scene);
        stage.show();
        stage.setMaxHeight(stage.getHeight());
        stage.setMaxWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
        stage.setMinWidth(stage.getWidth());
    }

    public static void main(String[] args) {
        launch();
    }
}

//handles all buttons except for the clear button, depending on if the button text is an operation or a number it will display something in the TextField output or pass some value
//to Calculate.java
class ButtonHandler extends Button implements EventHandler<ActionEvent>, Output {
    //resetText is a check used when text on the calculator needs to be blank
    boolean resetText = false;
    //flag is used to make sure the user is inputting (by clicking buttons) a number or an operation when appropriate (so the user cant do something like 5++++++ or ++5++)
    // 0: operations  only
    // 1: numbers only
    // 2,3: special condition for = operation
    int flag = 0;
    //decimalFlag is used to make sure the user does not incorrectly input decimals when they are not supposed to ex. 5.... ...555.5.5
    boolean decimalFlag = false;

    @Override
    public void handle(ActionEvent e) {
        double update = 0;
        Button button = (Button) e.getSource();
        char operation = button.getText().charAt(0);

        if ((operation == '+' || operation == '-' || operation == '/' || operation == '*' || operation == '=')) {
            decimalFlag = false;
            if (flag == 3) {
                flag = 0;
            }
            if (resetText)
                output.setText("0");

            if (flag == 2 && operation != '=') {
                if (operation == '/' || operation == '*')
                    update = calculator.add(1, operation);
                else {
                    update = calculator.add(0, operation);
                }

                flag = 3;
            }

            if (flag == 0 || operation == '=') {
                if (operation == '=')
                    flag = 2;
                else {
                    flag = 1;
                }
                update = calculator.add(Double.parseDouble(output.getText()), operation);
                System.out.println("Calculator Return value: " + update);
            }

            output.setText("");
            if (update != 0 || calculator.resultZero) {
                if (update % 1 == 0)
                    output.setText(String.valueOf((int) update));
                else
                    output.setText(String.valueOf(update));
                resetText = true;
                calculator.resultZero = false;
            }


        } else {//the buttons text is a number or a decimal not an operation
            if (resetText) {
                output.setText("");
                resetText = false;
            }

            if (!decimalFlag || !(button.getText().equals(".")))
                output.setText(output.getText() + button.getText());

            if (button.getText().equals("."))
                decimalFlag = true;

            if (flag == 3)
                calculator.setY(Double.parseDouble(output.getText()));

            if (flag != 3)
                flag = 0;
        }
    }
}

