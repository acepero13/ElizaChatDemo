package de.dfki.eliza.files.parsers.factories;

import de.dfki.eliza.files.models.Conversation;

/**
 * Created by alvaro on 3/14/17.
 */
public class ConversationFactory {

    private Conversation conversation;
    private static ConversationFactory instance = null;
    private ConversationFactory(){
        conversation = new Conversation();
    }

    public static ConversationFactory getInstance(){
        if(instance == null)
            instance = new ConversationFactory();
        return instance;
    }

    public Conversation getConversation(){
        return  conversation;
    }

    public Conversation createConversation() {
        this.conversation = new Conversation();
        return this.conversation;
    }
}
