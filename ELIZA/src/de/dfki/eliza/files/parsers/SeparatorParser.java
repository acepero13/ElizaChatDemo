package de.dfki.eliza.files.parsers;

import java.util.LinkedList;

/**
 * Created by alvaro on 3/13/17.
 */
public class SeparatorParser {
    public static final int DEFAULT_INT_VALUE = -1;
    private final String separator;
    private final String text;
    private LinkedList<String> values = new LinkedList<>();
    private int appearances = 0;

    public SeparatorParser(String separator, String text, int appearances) {
        this.separator = separator;
        this.text = text;
        this.appearances = appearances;
    }

    public void parse() {

        if (countMatches(text, separator) == appearances) {
            addValues();
        }

    }

    private void addValues() {
        int counter = 0;
        int separatorPos = getFirstSeparatorPos();
        while (counter < appearances) {
            separatorPos = findValue(separatorPos);
            counter++;
        }
    }

    private int findValue(int separatorPos) {
        int nextSeparator = getNextSeparator(separatorPos);
        int textStartPos = separatorPos + 1;
        String value = text.substring(textStartPos, nextSeparator);
        separatorPos = nextSeparator;
        getValues().add(value);
        return separatorPos;
    }

    private int getFirstSeparatorPos() {
        return text.indexOf(separator);
    }

    private int getNextSeparator(int separatorPos) {
        int pos = text.indexOf(separator, separatorPos + 1);
        if (pos == -1) {
            return text.length();
        }
        return pos;
    }


    private int countMatches(String text, String findStr) {
        int lastIndex = 0;
        int count = 0;
        while (lastIndex != -1) {
            lastIndex = text.indexOf(findStr, lastIndex);
            if (lastIndex != -1) {
                count++;
                lastIndex += findStr.length();
            }
        }
        return count;
    }

    public LinkedList<String> getValues() {
        return values;
    }

    public String getStringAt(int pos) {
        if (pos < values.size()) {
            return values.get(pos);
        }
        return "";
    }

    public int getIntAt(int pos) {
        if (pos < values.size()) {
            return Integer.valueOf(values.get(pos));
        }
        return DEFAULT_INT_VALUE;
    }


}
