package me.khudyakov.labs.translators.syntaxanalyzer.entity;

import static me.khudyakov.labs.translators.syntaxanalyzer.entity.LexemeType.*;

public class Lexeme {

    private final String value;
    private final LexemeType type;
    private final int lineNum;

    public Lexeme(String value, LexemeType type, int line) {
        this.value = value;
        this.type = type;
        this.lineNum = line;
    }

    public String getValue() {
        return value;
    }

    public LexemeType getType() {
        return type;
    }

    public int getLineNum() {
        return lineNum;
    }

    public boolean isKeyword() {
        return type.isKeyword();
    }

    public boolean isExprOperation() {
        return type.isExprOperation();
    }

    public boolean isCondOperation() {
        return type == AND || type == OR;
    }

    public boolean isCompareOperation() {
        return type == GREATER || type == EGREATER || type == LESS
                || type == ELESS || type == EQUAL || type == NOT_EQUAL;
    }

    public boolean isAtom() {
        return type.isAtom();
    }

    public boolean isBool() {
        return type == TRUE || type == FALSE;
    }

    public boolean isFunctionArg() {
        return type.isAtom() || isBool();
    }

    public boolean isVarType() {
        return type == INTEGER || type == BOOLEAN;
    }

    public boolean isStatementStart() {
        return type == VARIABLE || type == WRITE || type == READ
                || type == IF || type == FOR;
    }

    public boolean isToOrDownTo() {
        return type == TO || type == DOWNTO;
    }

    public boolean isTypeOf(LexemeType type) {
        return this.type == type;
    }

//    public boolean isProgram() {
//        return type == PROGRAM;
//    }
//
//    public boolean isVar() {
//        return type == VAR;
//    }
//
//    public boolean isBegin() {
//        return type == BEGIN;
//    }
//
//    public boolean isEnd() {
//        return type == END;
//    }
//
//    public boolean isRead() {
//        return type == READ;
//    }
//
//    public boolean isWrite() {
//        return type == WRITE;
//    }
//
//    public boolean isIf() {
//        return type == IF;
//    }
//
//    public boolean isThen() {
//        return type == THEN;
//    }
//
//    public boolean isFor() {
//        return type == FOR;
//    }
//
//    public boolean isTo() {
//        return type == TO;
//    }
//
//    public boolean isDownto() {
//        return type == DOWNTO;
//    }
//
//    public boolean isDo() {
//        return type == DO;
//    }
//
//    public boolean isInteger() {
//        return type == INTEGER;
//    }
//
//    public boolean isBoolean() {
//        return type == BOOLEAN;
//    }
//
//    public boolean isAddition() {
//        return type == ADDITION;
//    }
//
//    public boolean isSubtraction() {
//        return type == SUBTRACTION;
//    }
//
//    public boolean isUnarySubtraction() {
//        return type == UNARY_SUBTRACTION;
//    }
//
//    public boolean isMultiplication() {
//        return type == MULTIPLICATION;
//    }
//
//    public boolean isDivision() {
//        return type == DIVISION;
//    }
//
//    public boolean isAssign() {
//        return type == ASSIGN;
//    }
//
//    public boolean isAnd() {
//        return type == AND;
//    }
//
//    public boolean isOr() {
//        return type == OR;
//    }
//
//    public boolean isNot() {
//        return type == NOT;
//    }
//
//    public boolean isEqual() {
//        return type == EQUAL;
//    }
//
//    public boolean isGreater() {
//        return type == GREATER;
//    }
//
//    public boolean isEGreater() {
//        return type == EGREATER;
//    }
//
//    public boolean isLess() {
//        return type == LESS;
//    }
//
//    public boolean isELess() {
//        return type == ELESS;
//    }
//
//    public boolean isVar() {
//        return type == POINT;
//    }
//
//    public boolean isVar() {
//        return type == COMMA;
//    }
//
//    public boolean isVar() {
//        return type == COLON;
//    }
//
//    public boolean isVar() {
//        return type == SEMICOLON;
//    }
//
//    public boolean isVar() {
//        return type == OPENBRACE;
//    }
//
//    public boolean isVar() {
//        return type == CLOSEBRACE;
//    }
//
//    public boolean isVar() {
//        return type == CONSTANT;
//    }
//
//    public boolean isVar() {
//        return type == VARIABLE;
//    }

    @Override
    public String toString() {
        return value;
    }
}
