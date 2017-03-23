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
        Textable m = new Message(dialogLine.getText(), valueLine.getIntAt(0), valueLine.getIntAt(1));
        m.isUser(false);
        conversationFactory.getConversation().addMessage(m);
    }


}
