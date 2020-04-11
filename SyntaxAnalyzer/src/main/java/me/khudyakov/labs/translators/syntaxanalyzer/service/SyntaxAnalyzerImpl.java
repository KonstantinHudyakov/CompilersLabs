package me.khudyakov.labs.translators.syntaxanalyzer.service;

import com.sun.istack.internal.NotNull;
import me.khudyakov.labs.translators.syntaxanalyzer.entity.Lexeme;
import me.khudyakov.labs.translators.syntaxanalyzer.entity.LexemeType;
import me.khudyakov.labs.translators.syntaxanalyzer.entity.ProgramCode;
import me.khudyakov.labs.translators.syntaxanalyzer.util.SyntaxAnalyzerException;

import java.util.function.BooleanSupplier;

import static me.khudyakov.labs.translators.syntaxanalyzer.entity.LexemeType.*;
import static me.khudyakov.labs.translators.syntaxanalyzer.util.SyntaxAnalyzerException.ERROR;
import static me.khudyakov.labs.translators.syntaxanalyzer.util.SyntaxAnalyzerException.PREMATURE_END;

public class SyntaxAnalyzerImpl implements SyntaxAnalyzer {

    private int curInd = 0;

    @Override
    public void checkOrThrow(ProgramCode code) throws SyntaxAnalyzerException {
        curInd = 0;
        program(code);
    }

    private void program(ProgramCode code) throws SyntaxAnalyzerException {
        checkTypeOfCurOrThrow(code, PROGRAM);
        checkTypeOfCurOrThrow(code, VARIABLE);
        checkTypeOfCurOrThrow(code, SEMICOLON);
        block(code);
        checkTypeOfCurOrThrow(code, POINT);
    }

    private void block(ProgramCode code) throws SyntaxAnalyzerException {
        if (checkTypeOfCur(code, VAR)) {
            varList(code);
        }
        executablePart(code);
    }

    private void varList(ProgramCode code) throws SyntaxAnalyzerException {
        checkTypeOfCurOrThrow(code, VAR);
        Lexeme cur = getCurOrThrow(code);
        while (cur.isTypeOf(VARIABLE)) {
            idList(code);
            checkTypeOfCurOrThrow(code, COLON);
            cur = getCurOrThrow(code);
            checkTypeOfCurOrThrow(code, cur::isVarType);
            checkTypeOfCurOrThrow(code, SEMICOLON);
            cur = getCurOrThrow(code);
        }
    }

    private void idList(ProgramCode code) throws SyntaxAnalyzerException {
        checkTypeOfCurOrThrow(code, VARIABLE);
        while (checkTypeOfCur(code, COMMA)) {
            curInd++;
            checkTypeOfCurOrThrow(code, VARIABLE);
        }
    }

    private void executablePart(ProgramCode code) throws SyntaxAnalyzerException {
        checkTypeOfCurOrThrow(code, BEGIN);
        if (!checkTypeOfCur(code, END)) {
            statementList(code);
        }
        checkTypeOfCurOrThrow(code, END);
    }

    private void statementList(ProgramCode code) throws SyntaxAnalyzerException {
        Lexeme cur = getCurOrThrow(code);
        while (cur.isStatementStart()) {
            statement(code);
            cur = getCurOrThrow(code);
        }
    }

    private void statement(ProgramCode code) throws SyntaxAnalyzerException {
        Lexeme cur = getCurOrThrow(code);
        if (cur.isTypeOf(READ) || cur.isTypeOf(WRITE)) {
            readOrWriteStatement(code);
        } else if (cur.isTypeOf(IF)) {
            ifStatement(code);
        } else if (cur.isTypeOf(FOR)) {
            forStatement(code);
        } else { // cur is variable
            curInd++;
            cur = getCurOrThrow(code);
            if (cur.isTypeOf(SEMICOLON)) { //it is call function
                curInd++;
            } else if (cur.isTypeOf(OPENBRACE)) {
                curInd++;
                callFunction(code);
                checkTypeOfCurOrThrow(code, SEMICOLON);
            } else if (cur.isTypeOf(ASSIGN)) {
                curInd--;
                assign(code);
                checkTypeOfCurOrThrow(code, SEMICOLON);
            }
        }
    }

    private void readOrWriteStatement(ProgramCode code) throws SyntaxAnalyzerException {
        Lexeme cur = getCurOrThrow(code);
        if(cur.isTypeOf(READ) || cur.isTypeOf(WRITE)) {
            curInd++;
            checkTypeOfCurOrThrow(code, OPENBRACE);
            if (!checkTypeOfCur(code, CLOSEBRACE)) {
                idList(code);
            }
            checkTypeOfCurOrThrow(code, CLOSEBRACE);
            checkTypeOfCurOrThrow(code, SEMICOLON);
        } else {
            throwError(cur);
        }
    }

    private void callFunction(ProgramCode code) throws SyntaxAnalyzerException {
        if (!checkTypeOfCur(code, CLOSEBRACE)) {
            functionArgs(code);
        }
        checkTypeOfCurOrThrow(code, CLOSEBRACE);
    }

    private void functionArgs(ProgramCode code) throws SyntaxAnalyzerException {
        Lexeme cur = getCurOrThrow(code);
        checkTypeOfCurOrThrow(code, cur::isFunctionArg);
        while (checkTypeOfCur(code, COMMA)) {
            curInd++;
            cur = getCurOrThrow(code);
            checkTypeOfCurOrThrow(code, cur::isFunctionArg);
        }
    }

    private void assign(ProgramCode code) throws SyntaxAnalyzerException {
        checkTypeOfCurOrThrow(code, VARIABLE);
        checkTypeOfCurOrThrow(code, ASSIGN);
        expression(code);
    }

    private void ifStatement(ProgramCode code) throws SyntaxAnalyzerException {
        checkTypeOfCurOrThrow(code, IF);
        cond(code);
        checkTypeOfCurOrThrow(code, THEN);
        body(code);
    }

    private void forStatement(ProgramCode code) throws SyntaxAnalyzerException {
        checkTypeOfCurOrThrow(code, FOR);
        assign(code);
        Lexeme cur = getCurOrThrow(code);
        checkTypeOfCurOrThrow(code, cur::isToOrDownTo);
        expression(code);
        checkTypeOfCurOrThrow(code, DO);
        body(code);
    }

//    private void expression(ProgramCode code) throws SyntaxAnalyzerException {
//        Lexeme cur = getCurOrThrow(code);
//        if (cur.isTypeOf(UNARY_SUBTRACTION)) {
//            curInd++;
//            expression(code);
//        } else if (cur.isTypeOf(OPENBRACE) || cur.isAtom()) {
//            curInd++;
//            if (cur.isTypeOf(OPENBRACE)) {
//                expression(code);
//                checkTypeOfCurOrThrow(code, CLOSEBRACE);
//            } else {
//                if (cur.isTypeOf(VARIABLE)) {
//                    cur = getCurOrThrow(code);
//                    if (cur.isTypeOf(OPENBRACE)) {
//                        curInd++;
//                        callFunction(code);
//                    }
//                }
//            }
//
//            cur = getCurOrThrow(code);
//            while (cur.isExprOperation()) {
//                curInd++;
//                if (checkTypeOfCur(code, OPENBRACE)) {
//                    curInd++;
//                    expression(code);
//                    checkTypeOfCurOrThrow(code, CLOSEBRACE);
//                } else {
//                    expression(code);
//                }
//                cur = getCurOrThrow(code);
//            }
//        } else {
//            throwError(cur);
//        }
//    }

    private void expression(ProgramCode code) throws SyntaxAnalyzerException {
        Lexeme cur = getCurOrThrow(code);
        if(cur.isTypeOf(ADDITION) || cur.isTypeOf(SUBTRACTION)) {
            curInd++;
        }
        expressionWithoutSign(code);
    }

    private void expressionWithoutSign(ProgramCode code) throws SyntaxAnalyzerException {
        term(code);
        Lexeme cur = getCurOrThrow(code);
        if(cur.isTypeOf(ADDITION) || cur.isTypeOf(SUBTRACTION)) {
            curInd++;
            expressionWithoutSign(code);
        }
    }

    private void term(ProgramCode code) throws SyntaxAnalyzerException {
        factor(code);
        Lexeme cur = getCurOrThrow(code);
        if(cur.isTypeOf(MULTIPLICATION) || cur.isTypeOf(DIVISION)) {
            curInd++;
            term(code);
        }
    }

    private void factor(ProgramCode code) throws SyntaxAnalyzerException {
        Lexeme cur = getCurOrThrow(code);
        if(cur.isTypeOf(OPENBRACE)) {
            curInd++;
            expression(code);
            checkTypeOfCurOrThrow(code, CLOSEBRACE);
        } else if(cur.isAtom()) {
            curInd++;
            if(cur.isTypeOf(VARIABLE)) {
                cur = getCurOrThrow(code);
                if(cur.isTypeOf(OPENBRACE)) {
                    curInd++;
                    callFunction(code);
                }
            }
        } else {
            throwError(cur);
        }
    }

//    private void cond(ProgramCode code) throws SyntaxAnalyzerException {
//        Lexeme cur = getCurOrThrow(code);
//        if(cur.isTypeOf(NOT)) {
//            curInd++;
//            cur = getCurOrThrow(code);
//            if(cur.isTypeOf(OPENBRACE)) {
//                curInd++;
//                cond(code);
//                checkTypeOfCurOrThrow(code, CLOSEBRACE);
//            } else {
//                cond(code);
//            }
//        } else if(cur.isTypeOf(OPENBRACE) || cur.isBool() || cur.isAtom() || cur.isTypeOf(UNARY_SUBTRACTION)) {
//            if(cur.isBool()) {
//                curInd++;
//                cur = getCurOrThrow(code);
//                while(cur.isCondOperation()) {
//                    curInd++;
//                    if(checkTypeOfCur(code, OPENBRACE)) {
//                        curInd++;
//                        cond(code);
//                        checkTypeOfCurOrThrow(code, CLOSEBRACE);
//                    } else {
//                        cond(code);
//                    }
//                    cur = getCurOrThrow(code);
//                }
//            } else if(cur.isTypeOf(UNARY_SUBTRACTION)) {
//                expression(code);
//                checkTypeOfCurOrThrow(code, cur::isCompareOperation);
//                expression(code);
//                // AND OR
//            } else if(cur.isAtom()) {
//                curInd++;
//                cur = getCurOrThrow(code);
//                if(cur.isTypeOf(OPENBRACE)) {
//                    callFunction(code);
//                    checkTypeOfCurOrThrow(code, CLOSEBRACE);
//                }
//                cur = getCurOrThrow(code);
//                if(cur.isCompareOperation()) {
//                    curInd++;
//                    expression(code);
//                }
//
//                // AND OR
//            }
//        }
//    }

    private void cond(ProgramCode code) throws SyntaxAnalyzerException {
        if(checkTypeOfCur(code, NOT)) {
            curInd++;
        }
        condition(code);
    }

    private void condition(ProgramCode code) throws SyntaxAnalyzerException {
        compareCond(code);
        Lexeme cur = getCurOrThrow(code);
        if(cur.isCondOperation()) {
            curInd++;
            condition(code);
        }
    }

    private void compareCond(ProgramCode code) throws SyntaxAnalyzerException {
        checkTypeOfCurOrThrow(code, OPENBRACE);
        expression(code);
        Lexeme cur = getCurOrThrow(code);
        checkTypeOfCurOrThrow(code, cur::isCompareOperation);
        expression(code);
        checkTypeOfCurOrThrow(code, CLOSEBRACE);
    }

    private void body(ProgramCode code) throws SyntaxAnalyzerException {
        Lexeme cur = getCurOrThrow(code);
        if(cur.isTypeOf(BEGIN)) {
            curInd++;
            cur = getCurOrThrow(code);
            if(cur.isTypeOf(END)) {
                curInd++;
            } else {
                statementList(code);
                checkTypeOfCurOrThrow(code, END);
            }
            checkTypeOfCurOrThrow(code, SEMICOLON);
        } else {
            statement(code);
        }
    }




    private boolean checkTypeOfCur(ProgramCode code, LexemeType type) throws SyntaxAnalyzerException {
        Lexeme cur = getCurOrThrow(code);
        return cur.isTypeOf(type);
    }

    private void checkTypeOfCurOrThrow(ProgramCode code, BooleanSupplier cond) throws SyntaxAnalyzerException {
        Lexeme cur = getCurOrThrow(code);
        throwIfNotTypeOf(cur, cond);
        curInd++;
    }

    private void checkTypeOfCurOrThrow(ProgramCode code, LexemeType type) throws SyntaxAnalyzerException {
        Lexeme cur = getCurOrThrow(code);
        throwIfNotTypeOf(cur, type);
        curInd++;
    }

    private Lexeme getCurOrThrow(ProgramCode code) throws SyntaxAnalyzerException {
        if (curInd >= code.size()) {
            throw new SyntaxAnalyzerException(PREMATURE_END);
        }
        return code.get(curInd);
    }

    private void throwIfNotTypeOf(@NotNull Lexeme lexeme, @NotNull LexemeType type) throws SyntaxAnalyzerException {
        throwIfNotTypeOf(lexeme, () -> lexeme.isTypeOf(type));
    }

    private void throwIfNotTypeOf(@NotNull Lexeme lexeme, BooleanSupplier cond) throws SyntaxAnalyzerException {
        if (!cond.getAsBoolean()) {
            throwError(lexeme);
        }
    }

    private void throwError(Lexeme lexeme) throws SyntaxAnalyzerException {
        throw new SyntaxAnalyzerException(ERROR, lexeme.toString(), lexeme.getLineNum());
    }
}
