package de.dfki.eliza.files.parsers;

import de.dfki.eliza.files.builders.ChatParser;
import de.dfki.eliza.files.parsers.dialog.Dialog;

/**
 * Created by alvaro on 3/13/17.
 */
public class SystemLineParser extends Dialog {

    @Override
    public boolean parseLine(String line) {
        return false;
    }

    @Override
    public void postParsed() {

    }


}
