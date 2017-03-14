package de.dfki.eliza.files.builders;

/**
 * Created by alvaro on 3/13/17.
 */
public abstract class ChatHandler {
    private ChatHandler nextHandler = null;
    private ChatParser parser = null;

    public void setNextHandler(ChatHandler handler) {
        nextHandler = handler;
    }

    protected abstract void write();
}
