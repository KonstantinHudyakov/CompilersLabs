package me.khudyakov.labs.translators.postfixform;

import me.khudyakov.labs.translators.postfixform.components.*;

public class FormulaParser {

    public Formula parse(String expr) throws Exception {
        expr = expr.trim();
        if (expr.length() < 1) {
            throw new Exception();
        }

        Formula formula = new Formula();
        int openBrackets = 0;
        int n = expr.length();
        for (int i = 0; i < n; i++) {
            char ch = expr.charAt(i);
            if (Character.isLetter(ch)) {
                if (i - 1 >= 0 && !isOperator(expr.charAt(i - 1)) && expr.charAt(i - 1) != '(' && expr.charAt(i - 1) != ')')
                    throw new Exception();
                formula.add(new Variable(ch));
            } else if (ch == '(') {
                openBrackets++;
                formula.add(new OpenBracket());
            } else if (ch == ')') {
                if (openBrackets < 1) {
                    throw new Exception();
                }
                openBrackets--;
                formula.add(new CloseBracket());
            } else if (isOperator(ch)) {
                if (i - 1 < 0 || isOperator(expr.charAt(i - 1))) {
                    if (ch != '+' && ch != '-') {
                        throw new Exception();
                    } else if (ch == '-') {
                        formula.add(new UnarySubtraction());
                    }
                } else {
                    if(ch == '+') {
                        formula.add(new Addition());
                    } else if(ch == '-') {
                        formula.add(new Subtraction());
                    } else if(ch == '*') {
                        formula.add(new Multiplication());
                    } else {
                        formula.add(new Division());
                    }
                }
            } else if(Character.isDigit(ch)) {
                if (i - 1 >= 0 && !isOperator(expr.charAt(i - 1)) && expr.charAt(i - 1) != '(' && expr.charAt(i - 1) != ')')
                    throw new Exception();
                double val = Character.digit(ch, 10);
                i++;
                while(i < n && Character.isDigit(expr.charAt(i))) {
                    val *= 10;
                    val += Character.digit(expr.charAt(i), 10);
                    i++;
                }
                if(i < n) {
                    if(expr.charAt(i) == '.') {
                        i++;
                        double fract = 0;
                        double factor = 10;
                        while(i < n && Character.isDigit(expr.charAt(i))) {
                            double cur = Character.digit(expr.charAt(i), 10) / factor;
                            fract += cur;
                            factor *= 10;
                            i++;
                        }
                        if(fract == 0) {
                            throw new Exception();
                        }
                        val += fract;
                    }
                }
                i--;
                formula.add(new Constant(val));
            } else {
                throw new Exception();
            }
        }

        if(openBrackets != 0) {
            throw new Exception();
        }
        return formula;
    }


    private boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }
}
