package me.khudyakov.labs.translators.syntaxanalyzer.entity;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class ProgramCode {

    private final List<Lexeme> lexemes;

    public ProgramCode(List<Lexeme> lexemes) {
        this.lexemes = lexemes;
    }

    public int size() {
        return lexemes.size();
    }

    public boolean isEmpty() {
        return lexemes.isEmpty();
    }

    public Iterator<Lexeme> iterator() {
        return lexemes.iterator();
    }

    public Lexeme get(int index) {
        return lexemes.get(index);
    }

    public List<Lexeme> subList(int fromIndex, int toIndex) {
        return lexemes.subList(fromIndex, toIndex);
    }

    public Stream<Lexeme> stream() {
        return lexemes.stream();
    }

    @Override
    public String toString() {
        return lexemes.toString();
    }
}
