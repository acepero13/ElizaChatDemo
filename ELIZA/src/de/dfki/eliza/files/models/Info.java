package de.dfki.eliza.files.models;

/**
 * Created by alvaro on 3/14/17.
 */
public class Info implements Textable {
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
}
