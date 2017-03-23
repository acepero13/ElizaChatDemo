package de.dfki.eliza.files.models;

import de.dfki.eliza.files.filestystem.Writable;
import de.dfki.eliza.files.parsers.UserInfoLineParser;

/**
 * Created by alvaro on 3/14/17.
 */
public class Info implements Textable, Writable {
    private String text;
    public Info(String text){
          this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void isUser(boolean b) {
        
    }

    @Override
    public String write() {
        String line = "";
        if(!getText().isEmpty()){
            line = UserInfoLineParser.INFO_LINE + " " + getText();
        }
        return line;
    }
}
