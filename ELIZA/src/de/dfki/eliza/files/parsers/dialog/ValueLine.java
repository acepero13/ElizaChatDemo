package de.dfki.eliza.files.parsers.dialog;

import de.dfki.eliza.files.parsers.IntSeparatorParser;

import java.util.LinkedList;

/**
 * Created by alvaro on 3/14/17.
 */
public class ValueLine implements ValueLineBehavior {
    private IntSeparatorParser parser;
    LinkedList<IntSeparatorParser> parsers = new LinkedList<>();
    public ValueLine(IntSeparatorParser parser){
        this.parser = parser;
        parsers.add(parser);
    }

    public ValueLine(LinkedList<IntSeparatorParser> parsers) {
        parser = parsers.getFirst();
        this.parsers = parsers;
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
        int i = 0;
        boolean found = false;
        while (i < parsers.size() && !found){
            IntSeparatorParser p = parsers.get(i);
            found = isParsed(p);
            i++;
            parser = p;
        }

    }

    private boolean isParsed(IntSeparatorParser p) {
        boolean parsed =  p.parse();
        return parsed;
    }


}
