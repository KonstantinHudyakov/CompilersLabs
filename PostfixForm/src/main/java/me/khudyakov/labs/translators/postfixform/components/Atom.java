package me.khudyakov.labs.translators.postfixform.components;

public abstract class Atom extends FormulaMember {
    protected double value;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
