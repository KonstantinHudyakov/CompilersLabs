package me.khudyakov.labs.translators.postfixform.components;

public class Variable extends Atom {
    private char name;

    public Variable(char name) {
        this.name = name;
    }

    public Variable(char name, double value) {
        this.name = name;
        this.value = value;
    }

    public char getName() {
        return name;
    }

    public void setName(char name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.valueOf(name);
    }
}
