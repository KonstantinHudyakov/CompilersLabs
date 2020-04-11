package me.khudyakov.labs.translators.postfixform.components;

public class Constant extends Atom {

    public Constant(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
