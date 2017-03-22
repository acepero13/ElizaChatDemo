package de.dfki.eliza.files.parsers.dialog;

import de.dfki.eliza.files.parsers.TextValueSeparator;

/**
 * Created by alvaro on 3/14/17.
 */
public class DialogLine implements DialogLineBehavior{

    private final String line;
    private final TextValueSeparator textValueSeparator;
    private  String characterName;
    private String text;
    public DialogLine(String line, String characterName){
        this.line = line;
        this.characterName = characterName;
        textValueSeparator = new TextValueSeparator(Dialog.VALUE_TOPIC_SEPARATOR);
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void parseText() {
        String trimmed = line.trim();
        text = trimmed.substring(characterName.length()).trim();
        separateCharacterName(trimmed);
        textValueSeparator.parseLine(text);
        text = textValueSeparator.getText().trim();
        removeDialogDots();
    }

    void removeDialogDots() {
        if(text.startsWith(":")){
            text = text.substring(1).trim();
        }
    }

    private void separateCharacterName(String trimmed) {
        characterName = trimmed.substring(0, characterName.length());
    }


    @Override
    public String getCharacterName() {
        return characterName;
    }
}
