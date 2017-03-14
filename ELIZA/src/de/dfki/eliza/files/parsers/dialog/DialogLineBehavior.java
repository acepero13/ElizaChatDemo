package de.dfki.eliza.files.parsers.dialog;

/**
 * Created by alvaro on 3/14/17.
 */
public interface DialogLineBehavior {
    String getText();
    void parseText();
    String getCharacterName();
}
