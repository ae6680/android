package com.shinav.mathapp.calculator;

import javax.inject.Inject;

public class EquationFilterer {

    @Inject
    public EquationFilterer() { }

    public String filter(String equation) {

        equation = filterMultiplyCharacter(equation);
        equation = filterDivideCharacter(equation);
        equation = filterCommaCharacter(equation);
        equation = filterSquareRootCharacter(equation);

        return equation;
    }

    private String filterMultiplyCharacter(String equation) {
        return equation.replace("×", "*");
    }

    private String filterDivideCharacter(String equation) {
        return equation.replace("÷", "/");
    }

    private String filterCommaCharacter(String equation) {
        return equation.replace(",", ".");
    }

    private String filterSquareRootCharacter(String equation) {
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
