package me.khudyakov.labs.translators.syntaxanalyzer.entity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum LexemeType {
    // Program Keywords
    PROGRAM("PROGRAM"),
    VAR("VAR"),
    BEGIN("BEGIN"),
    END("END"),
    READ("READ"),
    WRITE("WRITE"),
    IF("IF"),
    THEN("THEN"),
    FOR("FOR"),
    TO("TO"),
    DOWNTO("DOWNTO"),
    DO("DO"),
    INTEGER("INTEGER"),
    BOOLEAN("BOOLEAN"),

    // Expression operations
    ADDITION("+"),
    SUBTRACTION("-"),
    MULTIPLICATION("*"),
    DIVISION("/"),

    ASSIGN(":="),

    // Condition operations
    AND("AND"),
    OR("OR"),
    NOT("NOT"),
    EQUAL("="),
    NOT_EQUAL("<>"),
    GREATER(">"),
    EGREATER(">="),
    LESS("<"),
    ELESS("<="),

    // Delimiters
    POINT("."),
    COMMA(","),
    COLON(":"),
    SEMICOLON(";"),
    OPENBRACE("("),
    CLOSEBRACE(")"),

    // Atoms
    CONSTANT("CONSTANT"),
    VARIABLE("VARIABLE"),

    // Bool
    TRUE("TRUE"),
    FALSE("FALSE");


    private static final Set<LexemeType> KEYWORDS = new HashSet<>();
    private static final Set<LexemeType> EXPR_OPERATIONS = new HashSet<>();
    private static final Set<LexemeType> COND_OPERATIONS = new HashSet<>();

    static {
        KEYWORDS.addAll(Arrays.asList(PROGRAM, VAR, BEGIN, END, READ, WRITE, IF, THEN, FOR, TO, DOWNTO, DO, INTEGER, BOOLEAN));
        EXPR_OPERATIONS.addAll(Arrays.asList(ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION));
        COND_OPERATIONS.addAll(Arrays.asList(AND, OR, NOT, EQUAL, GREATER, EGREATER, LESS, ELESS));
    }

    private final String value;

    LexemeType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public boolean isKeyword() {
        return KEYWORDS.contains(this);
    }

    public boolean isExprOperation() {
        return EXPR_OPERATIONS.contains(this);
    }

    public boolean isAtom() {
        return this == VARIABLE || this == CONSTANT;
    }
}
