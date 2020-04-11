package me.khudyakov.labs.translators.syntaxanalyzer.service;

import com.sun.istack.internal.NotNull;
import me.khudyakov.labs.translators.syntaxanalyzer.entity.ProgramCode;
import me.khudyakov.labs.translators.syntaxanalyzer.util.SyntaxAnalyzerException;

public interface SyntaxAnalyzer {

    void checkOrThrow(@NotNull ProgramCode programCode) throws SyntaxAnalyzerException;
}
