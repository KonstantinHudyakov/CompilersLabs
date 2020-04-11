package me.khudyakov.labs.translators.postfixform;

import me.khudyakov.labs.translators.postfixform.components.FormulaMember;
import me.khudyakov.labs.translators.postfixform.components.Variable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class Formula {

    private List<FormulaMember> formula = new ArrayList<>();

    public void add(FormulaMember formulaMember) {
        formula.add(formulaMember);
    }

    public FormulaMember get(int index) {
        return formula.get(index);
    }

    public Stream<FormulaMember> stream() {
        return formula.stream();
    }

    public Iterator<FormulaMember> iterator() {
        return formula.iterator();
    }

    public int size() {
        return formula.size();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(FormulaMember cur : formula) {
            builder.append(cur.toString());
        }
        return builder.toString();
    }

    public String toValueString() {
        StringBuilder builder = new StringBuilder();
        for(FormulaMember cur : formula) {
            if(cur instanceof Variable) {
                builder.append(((Variable) cur).getValue());
            } else {
                builder.append(cur.toString());
            }
        }
        return builder.toString();
    }
}
