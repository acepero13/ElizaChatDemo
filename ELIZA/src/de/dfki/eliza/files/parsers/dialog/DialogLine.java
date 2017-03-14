package de.dfki.eliza.files.parsers.dialog;

import de.dfki.eliza.files.parsers.UserLineParser;

/**
 * Created by alvaro on 3/14/17.
 */
public class DialogLine implements DialogLineBehavior{

    private final String line;
    private  String characterName;
    private String text;

    public DialogLine(String line, String characterName){
        this.line = line;
        this.characterName = characterName;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void parseText() {
        String trimmed = line.trim();
        text = trimmed.substring(characterName.length()).trim();
        characterName = trimmed.substring(0, characterName.length());
        int separatorPos = text.indexOf(UserLineParser.VALUE_TOPIC_SEPARATOR);
        if(separatorPos > 0){
            text = text.substring(0, separatorPos).trim();
        }
    }

    @Override
    public String getCharacterName() {
        return characterName;
    }
}
