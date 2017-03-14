package de.dfki.eliza.files.parsers.dialog;

/**
 * Created by alvaro on 3/14/17.
 */
public class NoValueLine implements ValueLineBehavior {
    @Override
    public String getStringAt(int index) {
        return "";
    }

    @Override
    public int getIntAt(int index) {
        return 0;
    }

    @Override
    public void parseText() {

    }
}
