package me.khudyakov.labs.translators.postfixform.components;

public class UnarySubtraction extends Operation {
    @Override
    public int dimension() {
        return 1;
    }

    @Override
    public String toString() {
        return "-";
    }
}
