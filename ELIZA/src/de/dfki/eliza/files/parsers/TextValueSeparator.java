package de.dfki.eliza.files.parsers;

import java.util.LinkedList;

public class TextValueSeparator {
    private final String separator;
    private LinkedList<Integer> values = new LinkedList<>();
    private LinkedList<String> text = new LinkedList<>();
    private String[] splitted;

    public TextValueSeparator(String separator){
        this.separator = separator;
    }

    public void parseLine(String text) {
        splitted = text.split(separator);
        for (String sValue: splitted ) {
            parse(sValue);
        }
    }

    private void parse(String sValue) {
        try {
            addValue(sValue);
        } catch (NumberFormatException e) {
            text.add(sValue);
        }
    }

    void addValue(String sValue) {
        Integer value = Integer.parseInt(sValue);
        values.add(value);
    }

    public int countMatches() {
        return values.size();
    }

    public LinkedList<Integer> getValues(){
        return values;
    }

    public LinkedList<String> getTextList(){
        return text;
    }

    public String getText(){
        StringBuilder builder = new StringBuilder();
        for (String value: text ) {
            builder.append(value);
        }
        return builder.toString();
    }
}