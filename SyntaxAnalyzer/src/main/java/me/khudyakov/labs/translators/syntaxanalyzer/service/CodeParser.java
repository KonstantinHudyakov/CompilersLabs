package me.khudyakov.labs.translators.syntaxanalyzer.service;

import com.sun.istack.internal.NotNull;
import me.khudyakov.labs.translators.syntaxanalyzer.entity.ProgramCode;
import me.khudyakov.labs.translators.syntaxanalyzer.util.CodeParserException;

public interface CodeParser {

    @NotNull
    ProgramCode parse(@NotNull String text) throws CodeParserException;
}
