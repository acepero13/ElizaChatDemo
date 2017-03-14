package de.dfki.eliza.files.parsers.dialog;

/**
 * Created by alvaro on 3/14/17.
 */
public class NoDialogLine implements DialogLineBehavior {
    @Override
    public String getText() {
        return "";
    }

    @Override
    public void parseText() {

    }

    @Override
    public String getCharacterName() {
        return "";
    }
}
