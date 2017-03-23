package de.dfki.eliza.files.models;

import de.dfki.eliza.files.filestystem.Writable;
import de.dfki.eliza.files.parsers.UserLineParser;

/**
 * Created by alvaro on 3/14/17.
 */
public class Message implements Textable, Writable{
    public static final String PIPE_SEPARATOR = "|";
    private String text;
    private int value = -1;
    private int topic = -1;
    private boolean isUserMessage = false;
    private int assessment = -1;

    public String getAuthorName() {
        return authorName;
    }

    private String authorName = "";

    public Message( String authorName, String text, int value, int topic){
        this.text = text;
        this.value = value;
        this.topic = topic;
        this.authorName = authorName;
    }

    public static Message createAgentMessage(String authorName, String text, int value, int topic){
        return new Message(authorName, text, value, topic);
    }

    public static Message createUserMessage( String authorName, String text, int value, int topic, int assessment){
        Message m = new Message(authorName, text, value, topic);
        m.setAssessment(assessment);
        return m;
    }



    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getTopic() {
        return topic;
    }

    public void setTopic(int topic) {
        this.topic = topic;
    }

    @Override
    public void isUser(boolean b) {
        this.isUserMessage = b;
    }

    public boolean isIsUserMessage() {
        return isUserMessage;
    }

    @Override
    public String write() {
        if(getText().isEmpty())
            return "";
        String author = writeAuthorName();
        String textLine = writeTextWithValues();
        String line = author + textLine;

        return line;
    }

    String writeTextWithValues() {
        String line = getText() + " " + PIPE_SEPARATOR
                + getTopic() + PIPE_SEPARATOR
                + getValue() + PIPE_SEPARATOR
                + getAssessment() + PIPE_SEPARATOR ;
        return line.trim();
    }

    private String writeAuthorName() {
        return getAuthorName() + " ";
    }

    public int getAssessment() {
        return assessment;
    }

    public void setAssessment(int assessment) {
        this.assessment = assessment;
    }
}
