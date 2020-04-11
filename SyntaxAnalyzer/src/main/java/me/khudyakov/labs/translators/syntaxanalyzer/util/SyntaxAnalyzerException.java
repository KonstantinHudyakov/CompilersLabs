package me.khudyakov.labs.translators.syntaxanalyzer.util;

public class SyntaxAnalyzerException extends Exception {

    public static final String PREMATURE_END = "Error! Premature end of program";
    public static final String ERROR = "Error with lexeme \"%s\", line %d";

    public SyntaxAnalyzerException(String formatStr, Object... args) {
        super(String.format(formatStr, args));
    }

    public SyntaxAnalyzerException() {
    }

    public SyntaxAnalyzerException(String message) {
        super(message);
    }

    public SyntaxAnalyzerException(String message, Throwable cause) {
        super(message, cause);
    }

    public SyntaxAnalyzerException(Throwable cause) {
        super(cause);
    }

    public SyntaxAnalyzerException(String message, Throwable cause, boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
