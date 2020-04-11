package me.khudyakov.labs.translators.syntaxanalyzer.editor;

import me.khudyakov.labs.translators.syntaxanalyzer.entity.ProgramCode;
import me.khudyakov.labs.translators.syntaxanalyzer.service.CodeParser;
import me.khudyakov.labs.translators.syntaxanalyzer.service.CodeParserImpl;
import me.khudyakov.labs.translators.syntaxanalyzer.service.SyntaxAnalyzer;
import me.khudyakov.labs.translators.syntaxanalyzer.service.SyntaxAnalyzerImpl;
import me.khudyakov.labs.translators.syntaxanalyzer.util.CodeParserException;
import me.khudyakov.labs.translators.syntaxanalyzer.util.SyntaxAnalyzerException;

import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class AnalyzeMenuListener implements MenuListener {

    private final Document document;
    private final CodeParser codeParser = new CodeParserImpl();
    private final SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzerImpl();

    public AnalyzeMenuListener(Document document) {
        this.document = document;
    }

    @Override
    public void menuSelected(MenuEvent e) {
        String text = getText();
        OutputAreaWriter.clear();
        try {
            ProgramCode code = codeParser.parse(text);
            syntaxAnalyzer.checkOrThrow(code);

            OutputAreaWriter.println("Ok! This code belong to the language.");
        } catch (CodeParserException | SyntaxAnalyzerException ex) {
            OutputAreaWriter.println(ex.getMessage());
            OutputAreaWriter.println("This code does not belong to the language.");
        }
    }

    @Override
    public void menuDeselected(MenuEvent e) {

    }

    @Override
    public void menuCanceled(MenuEvent e) {

    }

    public String getText() {
        String text = "";
        try {
            text = document.getText(0, document.getLength());
        } catch (BadLocationException e) {
            // never happens
        }
        return text;
    }
}
