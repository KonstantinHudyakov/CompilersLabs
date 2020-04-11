package me.khudyakov.labs.translators.postfixform.components;

public class Subtraction extends Operation {
    @Override
    public int dimension() {
        return 2;
    }

    @Override
    public String toString() {
        return "-";
    }
}
