package com.shinav.mathapp.calculator;

import javax.inject.Inject;

public class EquationFilterer {

    @Inject
    public EquationFilterer() { }

    public String filterEquation(String equation) {

        equation = filterMultiplyCharacter(equation);
        equation = filterDivideCharacter(equation);
        equation = filterCommaCharacter(equation);
        equation = filterSquareRootCharacter(equation);

        return equation;
    }

    public String filterMultiplyCharacter(String equation) {
        return equation.replace("×", "*");
    }

    public String filterDivideCharacter(String equation) {
        return equation.replace("÷", "/");
    }

    public String filterCommaCharacter(String equation) {
        return equation.replace(",", ".");
    }

    public String filterSquareRootCharacter(String equation) {
        int squareIndex = equation.indexOf("√");
        if (squareIndex != -1) {

            String subString = equation.substring(squareIndex+1);

            String[] splits = subString.split(" ");

            String numberBehindSquare = "";
            if (splits.length > 0) {
                numberBehindSquare = splits[1];
            }

            if (numberBehindSquare.isEmpty()) {
                numberBehindSquare = subString;
            }

            String newReplace = "sqrt(" + numberBehindSquare + ")";
            String oldReplace = "√ " + numberBehindSquare;

            equation = equation.replace(oldReplace, newReplace);
        }
        return equation;
    }

}
