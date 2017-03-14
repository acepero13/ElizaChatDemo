package de.dfki.eliza.files.parsers;

import de.dfki.eliza.files.builders.ChatParser;
import de.dfki.eliza.files.models.Conversation;
import de.dfki.eliza.files.parsers.dialog.Dialog;
import de.dfki.eliza.files.parsers.dialog.NoDialogLine;
import de.dfki.eliza.files.parsers.dialog.NoValueLine;

import java.util.LinkedList;

/**
 * Created by alvaro on 3/13/17.
 */
public class NewChatParser extends Dialog {

    private final LinkedList<Conversation> conversations;

    public NewChatParser(LinkedList<Conversation> conversations){
        this.conversations = conversations;
    }

    public static final String NEW_CHAT_MARKER = "------";

    @Override
    public boolean parseLine(String line) {
        dialogLine = new NoDialogLine();
        valueLine = new NoValueLine();
        return line.startsWith(NEW_CHAT_MARKER);
    }

    public void postParsed(){
        Conversation conversation = conversationFactory.createConversation();
        conversations.add(conversation);
    }


}
