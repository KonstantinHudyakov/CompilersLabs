package me.khudyakov.labs.translators.syntaxanalyzer;

import me.khudyakov.labs.translators.syntaxanalyzer.entity.ProgramCode;
import me.khudyakov.labs.translators.syntaxanalyzer.service.CodeParser;
import me.khudyakov.labs.translators.syntaxanalyzer.service.CodeParserImpl;
import me.khudyakov.labs.translators.syntaxanalyzer.service.SyntaxAnalyzer;
import me.khudyakov.labs.translators.syntaxanalyzer.service.SyntaxAnalyzerImpl;
import me.khudyakov.labs.translators.syntaxanalyzer.util.IOUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SyntaxAnalyzerImplTest {

    private static String test1;

    private static final CodeParser codeParser = new CodeParserImpl();
    private static final SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzerImpl();

    static {
        try {
            test1 = IOUtil.getResourceFileAsString("test1.txt");
        } catch (IOException ex) {
            ex.printStackTrace();
            fail();
        }
    }

    @Test
    void checkOrThrow() {
        assertDoesNotThrow(() -> {
            ProgramCode code = codeParser.parse(test1);
            syntaxAnalyzer.checkOrThrow(code);
        });
    }
}