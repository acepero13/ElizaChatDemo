package de.dfki.eliza.files.parsers.dialog;

import de.dfki.eliza.files.builders.ChatParser;
import de.dfki.eliza.files.models.Conversation;
import de.dfki.eliza.files.parsers.factories.ConversationFactory;

/**
 * Created by alvaro on 3/14/17.
 */
public abstract class Dialog implements ChatParser {
    public static final String INFO_LINE = "info:";
    public static final String USER_NAME = "Sie:";
    public static final String VALUE_TOPIC_SEPARATOR = "|";
    public static final int COUNT_PIPE_SEPARATOR_DEPRECATED = 3;
    public static final int COUNT_PIPE_SEPARATOR = 4;

    protected DialogLineBehavior dialogLine;
    protected ValueLineBehavior valueLine;
    protected abstract boolean parseLine(String line);
    protected ConversationFactory conversationFactory = ConversationFactory.getInstance();
    private Dialog nextParser;

    public boolean parse(String line){
        if(parseLine(line)){
            dialogLine.parseText();
            valueLine.parseText();
            postParsed();
            return true;
        }else if(nextParser != null){
            return nextParser.parse(line);
        }
        return false;

    }

    public abstract void postParsed();

    public String getText(){
        return dialogLine.getText();
    }

    public String getCharacterName(){
        return dialogLine.getCharacterName();
    }

    public String getStringAt(int index) {
        return valueLine.getStringAt(index);
    }

    public int getIntAt(int index) {
        return valueLine.getIntAt(index);
    }

    public void setNextParser(Dialog nextParser) {
        this.nextParser = nextParser;
    }
}
