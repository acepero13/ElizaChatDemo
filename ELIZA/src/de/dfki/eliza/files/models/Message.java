package de.dfki.eliza.files.models;

/**
 * Created by alvaro on 3/14/17.
 */
public class Message implements Textable{
    private String text;
    private int value;
    private int topic;
    private boolean isUserMessage = false;

    public Message(String text, int value, int topic){
        this.text = text;
        this.value = value;
        this.topic = topic;
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
    
}
