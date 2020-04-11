package me.khudyakov.labs.translators.syntaxanalyzer.service;

import com.sun.istack.internal.NotNull;
import me.khudyakov.labs.translators.syntaxanalyzer.entity.Lexeme;
import me.khudyakov.labs.translators.syntaxanalyzer.entity.LexemeType;
import me.khudyakov.labs.translators.syntaxanalyzer.entity.ProgramCode;
import me.khudyakov.labs.translators.syntaxanalyzer.util.CodeParserException;

import java.util.*;

import static me.khudyakov.labs.translators.syntaxanalyzer.entity.LexemeType.*;

public class CodeParserImpl implements CodeParser {

    private static final Set<String> PROGRAM_KEYWORDS = new HashSet<>();

    static {
        PROGRAM_KEYWORDS.addAll(Arrays.asList("PROGRAM", "VAR", "BEGIN", "END", "READ", "WRITE", "IF",
                "THEN", "FOR", "TO", "DOWNTO", "DO", "INTEGER", "BOOLEAN", "AND", "OR", "NOT", "TRUE", "FALSE"));
    }

    @Override
    public @NotNull
    ProgramCode parse(@NotNull String text) throws CodeParserException {
        List<Lexeme> lexemes = new ArrayList<>();

        text = text.trim().replaceAll("\t+", " ");
        String[] lines = text.split("[\r\n]+");
        for(int i = 0; i < lines.length; i++) {
            lines[i] = lines[i].trim();
            String[] tokens  = lines[i].split(" +");
            for(String token : tokens) {
                token = token.trim();
                parseToken(token, lexemes, i + 1);
            }
        }

        return new ProgramCode(lexemes);
    }

    public void parseToken(@NotNull String token, @NotNull List<Lexeme> lexemes, int lineNum) throws CodeParserException {
        int n = token.length();
        char[] arr = token.toCharArray();

        for(int i = 0; i < n; i++) {
            Lexeme cur = null;
            switch (arr[i]) {
                case '+': {
                    cur = new Lexeme("+", ADDITION, lineNum);
                    break;
                }
                case '-': {
                    cur = new Lexeme("-", SUBTRACTION, lineNum);
                    break;
                }
                case '*': {
                    cur = new Lexeme("*", MULTIPLICATION, lineNum);
                    break;
                }
                case '/': {
                    cur = new Lexeme("/", DIVISION, lineNum);
                    break;
                }
                case ':': {
                    if(i + 1 < n && arr[i + 1] == '=') {
                        cur = new Lexeme(":=", ASSIGN, lineNum);
                        i++;
                    } else {
                        cur = new Lexeme(":", COLON, lineNum);
                    }
                    break;
                }
                case '=': {
                    cur = new Lexeme("=", EQUAL, lineNum);
                    break;
                }
                case '>': {
                    if(i + 1 < n && arr[i + 1] == '=') {
                        cur = new Lexeme(">=", EGREATER, lineNum);
                        i++;
                    } else {
                        cur = new Lexeme(">", GREATER, lineNum);
                    }
                    break;
                }
                case '<': {
                    if(i + 1 < n && arr[i + 1] == '=') {
                        cur = new Lexeme("<=", ELESS, lineNum);
                    } else if(i + 1 < n && arr[i + 1] == '>') {
                        cur = new Lexeme("<>", NOT_EQUAL, lineNum);
                    } else {
                        cur = new Lexeme("<", LESS, lineNum);
                    }
                    break;
                }
                case '.': {
                    cur = new Lexeme(".", POINT, lineNum);
                    break;
                }
                case ',': {
                    cur = new Lexeme(",", COMMA, lineNum);
                    break;
                }
                case ';': {
                    cur = new Lexeme(";", SEMICOLON, lineNum);
                    break;
                }
                case '(': {
                    cur = new Lexeme("(", OPENBRACE, lineNum);
                    break;
                }
                case ')': {
                    cur = new Lexeme(")", CLOSEBRACE, lineNum);
                    break;
                }
                default: {
                    if(Character.isDigit(arr[i])) {
                        StringBuilder numBuilder = new StringBuilder();
                        numBuilder.append(arr[i]);
                        while(i + 1 < n && Character.isDigit(arr[i + 1])) {
                            numBuilder.append(arr[i + 1]);
                            i++;
                        }
                        cur = new Lexeme(numBuilder.toString(), CONSTANT, lineNum);
                    } else if(Character.isLetter(arr[i])) {
                        StringBuilder wordBuilder = new StringBuilder();
                        wordBuilder.append(arr[i]);
                        while(i + 1 < n && Character.isLetterOrDigit(arr[i + 1])) {
                            wordBuilder.append(arr[i + 1]);
                            i++;
                        }
                        String word = wordBuilder.toString();
                        LexemeType type = getWordLexemeType(word);
                        cur = new Lexeme(word, type, lineNum);
                    } else {
                        throw new CodeParserException(String.format("Error! Unknown character '%s' in line %d", arr[i], lineNum));
                    }
                }
            }

            lexemes.add(cur);
        }
    }


    private @NotNull LexemeType getWordLexemeType(String word) {
        return PROGRAM_KEYWORDS.contains(word) ? LexemeType.valueOf(word) : VARIABLE;
    }
}
