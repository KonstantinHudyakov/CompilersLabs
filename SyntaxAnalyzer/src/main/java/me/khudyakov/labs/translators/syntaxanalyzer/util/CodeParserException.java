package me.khudyakov.labs.translators.syntaxanalyzer.util;

public class CodeParserException extends Exception {

    public CodeParserException() {
    }

    public CodeParserException(String message) {
        super(message);
    }

    public CodeParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public CodeParserException(Throwable cause) {
        super(cause);
    }

    public CodeParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
