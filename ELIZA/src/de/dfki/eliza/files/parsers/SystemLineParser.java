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
    private String SystemName = "{Name}";

    @Override
    public boolean parseLine(String line) {
        SystemName = ConversationFactory.getInstance().getConversation().getSystemName();
        IntSeparatorParser parser = new IntSeparatorParser(VALUE_TOPIC_SEPARATOR, line, COUNT_PIPE_SEPARATOR_DEPRECATED);
        valueLine = new ValueLine(parser);
        dialogLine = new DialogLine(line, SystemName);
        return line.startsWith(SystemName);

    }

    @Override
    public void postParsed() {
        Textable m = new Message(dialogLine.getText(), valueLine.getIntAt(0), valueLine.getIntAt(1));
        m.isUser(false);
        conversationFactory.getConversation().addMessage(m);
    }


}
