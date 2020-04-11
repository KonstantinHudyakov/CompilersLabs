package me.khudyakov.labs.translators.syntaxanalyzer;

import me.khudyakov.labs.translators.syntaxanalyzer.entity.ProgramCode;
import me.khudyakov.labs.translators.syntaxanalyzer.service.CodeParser;
import me.khudyakov.labs.translators.syntaxanalyzer.service.CodeParserImpl;
import me.khudyakov.labs.translators.syntaxanalyzer.util.CodeParserException;
import me.khudyakov.labs.translators.syntaxanalyzer.util.IOUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class CodeParserImplTest {

    private static String test1;

    private static final CodeParser codeParser = new CodeParserImpl();

    static {
        try {
            test1 = IOUtil.getResourceFileAsString("test1.txt");
        } catch (IOException ex) {
            ex.printStackTrace();
            fail();
        }
    }

    @Test
    void parse() throws CodeParserException {
        String expected = "[PROGRAM, prog322, ;, VAR, h2, ,, g6, :, INTEGER, ;, bool, :, BOOLEAN, ;, BEGIN, FOR, i, :=, (, 3, *, 2, ), -, (, (, arg, /, 3, ), *, bcd, ), TO, f, +, 5, DO, BEGIN, READ, (, b4, ,, F, ), ;, END, ;, bool, :=, false, ;, WRITE, (, ), ;, IF, (, ar, <, (, 43, *, 21, -, 43, ), /, 3, ), AND, (, g5r, >, 4, ), THEN, WRITE, (, g5, ), ;, WRITE, (, true, ), ;, END, .]";

        ProgramCode res = codeParser.parse(test1);
        assertEquals(expected, res.toString());
    }
}