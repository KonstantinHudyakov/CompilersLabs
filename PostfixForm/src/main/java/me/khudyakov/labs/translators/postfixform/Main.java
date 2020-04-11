package me.khudyakov.labs.translators.postfixform;

import me.khudyakov.labs.translators.postfixform.components.FormulaMember;
import me.khudyakov.labs.translators.postfixform.components.Variable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private static final String FILE_NAME = "values5.txt";

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));

        String expr = reader.readLine();
        Map<Character, Double> values = readValues(reader);
        reader.close();

        System.out.println("formula: " + expr);

        FormulaParser parser = new FormulaParser();
        Formula formula = null;
        try {
            formula = parser.parse(expr);
        } catch (Exception ex) {
            System.out.println("Ошибка! Формула некорректна.");
            return;
        }

        try {
            substitute(formula, values);
        } catch (Exception e) {
            System.out.println("Ошибка! Значения переменных некорректны.");
            return;
        }

        //System.out.println("formula: " + formula.toString());
        System.out.println("substitution: " + formula.toValueString());

        Formula postfix = null;
        PostfixConverter postfixConverter = new PostfixConverter();
        try {
            postfix = postfixConverter.toPostfixForm(formula);
        } catch (Exception ex) {
            System.out.println("Ошибка! Формула некорректна.");
            return;
        }

        System.out.println("postfix form: " + postfix);

        double val = 0;

        try {
            val = postfixConverter.calculate(postfix);
        } catch (Exception ex) {
            System.out.println("Ошибка! Формула некорректна.");
            return;
        }
        if (val != Double.NEGATIVE_INFINITY && val != Double.POSITIVE_INFINITY) {
            System.out.println("calculated value: " + val);
        } else {
            System.out.println("Ошибка! Деление на 0.");
        }
    }


    private static Map<Character, Double> readValues(BufferedReader reader) throws IOException {
        Map<Character, Double> values = new HashMap<>();
        String line = reader.readLine();
        while (line != null) {
            String[] nums = line.split(" ");
            values.put(nums[0].charAt(0), Double.parseDouble(nums[2]));
            line = reader.readLine();
        }
        return values;
    }

    private static void substitute(Formula formula, Map<Character, Double> values) throws Exception {
        for (int i = 0; i < formula.size(); i++) {
            FormulaMember cur = formula.get(i);
            if (cur instanceof Variable) {
                Double val = values.get(((Variable) cur).getName());
                if (val == null) {
                    throw new Exception();
                }
                ((Variable) cur).setValue(val);
            }
        }
    }
}
