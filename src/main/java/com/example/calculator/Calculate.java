package com.example.calculator;

//Takes two variables and an operation, then it performs that operation using the two variables
//Returns the result through the variable toReturn.
public class Calculate implements Output {
    private double x = 0;
    private double y = 0;
    //these flags indicate that the operation related to the flag be used on the current variables
    private boolean addFlag = false;
    private boolean subtractFlag = false;
    private boolean divideFlag = false;
    private boolean multiplyFlag = false;
    private boolean selectedFlag = false;
    private boolean xIsEmpty = true;
    private boolean yIsEmpty = true;
    //The boolean resultZero (checks if result = zero) is called by the ButtonHandler to check if it = zero
    public boolean resultZero = false;

    //the add() is not the math add operation, it instead  adds the number and operation to the Calculate object assigning the number to a variable x or y
    //and the appropriate operation flag is turned on for the operation.
    //add(number1, operation1) only takes in one variable and one operation, once you invoke add(number2, operation2) a second time it will take number1 and number 2 and perform
    //the operation1, and it will set up the next operation (operation2) with the result from the first operation.
    public double add(double z, char operation) {
        double toReturn = 0;

        if (xIsEmpty) {
            xIsEmpty = false;
            x = z;
            System.out.println("X: " + x);
        } else if (yIsEmpty) {
            yIsEmpty = false;
            y = z;
            System.out.println("Y: " + y);
        }

        //when the selectedFlag is true both number variables have been filled and the operation has been selected so this condition calls result() to calculate
        //the numbers with the selected operation
        if (selectedFlag || operation == '=') {
            toReturn = result();
        }

        if (operation == '+' && !(selectedFlag)) {
            addFlag = true;
            selectedFlag = true;
        } else if (operation == '-' && !(selectedFlag)) {
            subtractFlag = true;
            selectedFlag = true;

        } else if (operation == '/' && !(selectedFlag)) {
            divideFlag = true;
            selectedFlag = true;
        } else if (operation == '*' && !(selectedFlag)) {
            multiplyFlag = true;
            selectedFlag = true;
        }
        return toReturn;
    }

    //takes the operation flag which is true and applies that operation to the numbers stored in variables x and y and stores the result to x
    //y is reset to zero and the flag yIsEmpty is turned true so another number can be stored and another operation can be stored
    public double result() {
        if (!xIsEmpty && !yIsEmpty) {
            if (addFlag)
                x = x + y;
            if (subtractFlag)
                x = x - y;
            if (divideFlag)
                x = x / y;
            if (multiplyFlag)
                x = x * y;
            y = 0;
            addFlag = false;
            subtractFlag = false;
            divideFlag = false;
            multiplyFlag = false;
            selectedFlag = false;
            yIsEmpty = true;
        }

        if (x == 0) {
            resultZero = true;
        }
        return x;
    }

    public void clear() {
        x = 0;
        y = 0;
        addFlag = false;
        subtractFlag = false;
        divideFlag = false;
        multiplyFlag = false;
        selectedFlag = false;
        xIsEmpty = true;
        yIsEmpty = true;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }
}
