package de.dfki.eliza.files.parsers;

import de.dfki.eliza.files.models.Message;
import de.dfki.eliza.files.models.Textable;
import de.dfki.eliza.files.parsers.dialog.Dialog;
import de.dfki.eliza.files.parsers.dialog.DialogLine;
import de.dfki.eliza.files.parsers.dialog.ValueLine;
import de.dfki.eliza.files.parsers.factories.ConversationFactory;

/**
 * Created by alvaro on 3/13/17.
 */
public class SystemLineParser extends Dialog {
    String systemName = "{Name}";

    @Override
    public boolean parseLine(String line) {
        getSystemName();
        IntSeparatorParser parser = new IntSeparatorParser(VALUE_TOPIC_SEPARATOR, line, COUNT_PIPE_SEPARATOR_DEPRECATED);
        valueLine = new ValueLine(parser);
        dialogLine = new DialogLine(line, systemName);
        return line.startsWith(systemName);

    }

    void getSystemName() {
        systemName = ConversationFactory.getInstance().getConversation().getSystemName();
    }

    @Override
    public void postParsed() {
        String authorName = systemName+ ":";
        Textable m = Message.createAgentMessage(authorName, dialogLine.getText(),
                valueLine.getIntAt(Dialog.VALUE_INDEX),
                valueLine.getIntAt(Dialog.TOPIC_INDEX));
        m.isUser(false);
        conversationFactory.getConversation().addMessage(m);
    }


}
