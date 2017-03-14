package de.dfki.eliza.files.parsers.dialog;

/**
 * Created by alvaro on 3/14/17.
 */
public interface ValueLineBehavior {
    String getStringAt(int index);
    int getIntAt(int index);
    void parseText();
}
