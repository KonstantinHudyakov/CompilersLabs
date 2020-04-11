package me.khudyakov.labs.translators.postfixform;

import me.khudyakov.labs.translators.postfixform.components.*;

import java.util.Stack;

public class PostfixConverter {

    public Formula toPostfixForm(Formula formula) throws Exception {
        Stack<FormulaMember> st = new Stack<>();
        Formula postfix = new Formula();
        for (int i = 0; i < formula.size(); i++) {
            FormulaMember cur = formula.get(i);
            if (cur instanceof Atom) {
                postfix.add(cur);
            } else if(cur instanceof UnarySubtraction) {
                st.push(cur);
            } else if (cur instanceof OpenBracket) {
                if(i - 1 >= 0 && (formula.get(i - 1) instanceof Atom || formula.get(i - 1) instanceof CloseBracket))
                    throw new Exception();
                st.push(cur);
            } else if (cur instanceof CloseBracket) {
                if(i - 1 >= 0 && (formula.get(i - 1) instanceof OpenBracket || formula.get(i - 1) instanceof Operation))
                    throw new Exception();
                while (!st.empty() && !(st.peek() instanceof OpenBracket)) {
                    postfix.add(st.peek());
                    st.pop();
                }
                if (st.empty())
                    throw new Exception();
                st.pop();
            } else if (cur instanceof Operation) {
                while (!st.empty()) {
                    if (!(st.peek() instanceof Operation)) {
                        break;
                    }
                    Operation top = (Operation) st.peek();
                    if (priority(top) > priority((Operation) cur)) {
                        postfix.add(top);
                        st.pop();
                    } else {
                        break;
                    }
                }
                st.push(cur);
            } else {
                throw new Exception();
            }
        }

        while (!st.empty()) {
            if (!(st.peek() instanceof Operation) && !(st.peek() instanceof Atom)) {
                throw new Exception();
            }
            postfix.add(st.peek());
            st.pop();
        }
        return postfix;
    }

    public double calculate(Formula postfix) {
        Stack<Double> st = new Stack<>();
        for (int i = 0; i < postfix.size(); i++) {
            FormulaMember cur = postfix.get(i);
            if (cur instanceof Atom) {
                st.push(((Atom) cur).getValue());
            } else if (cur instanceof Operation) {
                if (((Operation) cur).dimension() == 1) {
                    double a = st.peek();
                    st.pop();
                    st.push(-a);
                } else {
                    double a = st.peek();
                    st.pop();
                    double b = st.peek();
                    st.pop();
                    if (cur instanceof Addition) {
                        st.push(a + b);
                    } else if (cur instanceof Subtraction) {
                        st.push(b - a);
                    } else if (cur instanceof Multiplication) {
                        st.push(a * b);
                    } else if (cur instanceof Division) {
                        st.push(b / a);
                    }
                }
            }
        }
        return st.peek();
    }


    private int priority(Operation operation) {
        int priority = -1;
        Class<?> operationClass = operation.getClass();
        if (operationClass.equals(UnarySubtraction.class)) {
            priority = 5;
        } else if (operationClass.equals(Division.class)) {
            priority = 4;
        } else if (operationClass.equals(Multiplication.class)) {
            priority = 3;
        } else if (operationClass.equals(Subtraction.class)) {
            priority = 2;
        } else if (operationClass.equals(Addition.class)) {
            priority = 1;
        }
        return priority;
    }
}
