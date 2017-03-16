package de.dfki.eliza.files.parsers;

import de.dfki.eliza.files.models.Message;
import de.dfki.eliza.files.models.Textable;
import de.dfki.eliza.files.parsers.dialog.Dialog;
import de.dfki.eliza.files.parsers.dialog.DialogLine;
import de.dfki.eliza.files.parsers.dialog.ValueLine;

/**
 * Created by alvaro on 3/13/17.
 */
public class UserLineParser extends Dialog {


    @Override
    public boolean parseLine(String line) {
        SeparatorParser parser = new SeparatorParser(VALUE_TOPIC_SEPARATOR, line, COUNT_PIPE_SEPARATOR);
        valueLine = new ValueLine(parser);
        dialogLine = new DialogLine(line, USER_NAME);
        return line.startsWith(USER_NAME);
    }

    public void postParsed(){
        Textable m = new Message(dialogLine.getText(), valueLine.getIntAt(0), valueLine.getIntAt(1));
        conversationFactory.getConversation().addMessage(m);
    }

    @Override
    public String getText() {
        return dialogLine.getText();
    }
}
