package de.dfki.eliza.files.parsers.dialog;

import de.dfki.eliza.files.parsers.SeparatorParser;

import java.util.LinkedList;

/**
 * Created by alvaro on 3/14/17.
 */
public class ValueLine implements ValueLineBehavior {
    private final SeparatorParser parser;
    LinkedList<String> values = new LinkedList<>();
    public ValueLine(SeparatorParser parser){
        this.parser = parser;
    }

    @Override
    public String getStringAt(int index) {
        return parser.getStringAt(index);
    }

    @Override
    public int getIntAt(int index) {
        return parser.getIntAt(index);
    }

    @Override
    public void parseText() {
        parser.parse();

    }



}
