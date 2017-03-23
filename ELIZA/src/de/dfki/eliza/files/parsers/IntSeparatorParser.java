package de.dfki.eliza.files.parsers;

import java.util.LinkedList;

/**
 * Created by alvaro on 3/13/17.
 */
public class IntSeparatorParser {
    public static final int DEFAULT_INT_VALUE = -1;
    private final String separator;
    private final String text;
    private final TextValueSeparator textValueSeparator;
    private int appearances;


    public IntSeparatorParser(String separator, String text, int appearances) {
        this.separator = separator;
        this.text = text;
        this.appearances = appearances;
        textValueSeparator = new TextValueSeparator(separator);
    }

    public boolean parse() {
        textValueSeparator.parseLine(text);
        return textValueSeparator.countMatches() == appearances;
    }


    public LinkedList<Integer> getValues() {
        return textValueSeparator.getValues();
    }

    public String getStringAt(int pos) {
        LinkedList<Integer> values = getValues();
        if (pos < values.size()) {
            Integer val = values.get(pos);
            return val.toString();
        }
        return "";
    }

    public int getIntAt(int pos) {
        LinkedList<Integer> values = getValues();
        if (pos < values.size()) {
            return Integer.valueOf(values.get(pos));
        }
        return DEFAULT_INT_VALUE;
    }

}
