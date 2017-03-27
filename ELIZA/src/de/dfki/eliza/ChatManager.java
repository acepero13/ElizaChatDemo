package de.dfki.eliza;

import de.dfki.eliza.files.exceptions.NoValidConversation;
import de.dfki.eliza.files.models.Conversation;

import java.util.LinkedList;

/**
 * Created by alvaro on 3/24/17.
 */
public class ChatManager {
    private int currentPosition = 0;
    private  LinkedList<Conversation> conversations = new LinkedList<>();

    public ChatManager(LinkedList<Conversation> conversations){
        this.conversations = conversations;
    }

    public int getTotalConversations() {
        return this.conversations.size();
    }


    public Conversation getNextConversation() throws NoValidConversation {
        int oldPosition  = currentPosition;
        isInvalidPosition(oldPosition);
        Conversation conversation = conversations.get(currentPosition);
        currentPosition++;
        return conversation;
    }

    void isInvalidPosition(int oldPosition) throws NoValidConversation {
        if(currentPosition >= getTotalConversations() || currentPosition < 0 ){
            currentPosition = oldPosition;
            throw new NoValidConversation("This is a non valid conversation");
        }
    }

    public Conversation goToConversation(int conversationId) throws NoValidConversation {
        int oldPosition = currentPosition;
        currentPosition = conversationId -1;
        isInvalidPosition(oldPosition);
        return conversations.get(currentPosition);
    }

    public Conversation getPreviousConversation() throws NoValidConversation {
        int oldPosition = currentPosition;
        currentPosition--;
        isInvalidPosition(oldPosition);
        return conversations.get(currentPosition);
    }

    public boolean hastNext() {
        return currentPosition < conversations.size();
    }

    public boolean hasPrevious() {
        return  currentPosition > 0;
    }


}
