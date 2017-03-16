package de.dfki.eliza.files.parsers;

import de.dfki.eliza.files.models.Message;
import de.dfki.eliza.files.models.Textable;
import de.dfki.eliza.files.parsers.dialog.Dialog;
import de.dfki.eliza.files.parsers.dialog.DialogLine;
import de.dfki.eliza.files.parsers.dialog.ValueLine;

import java.util.LinkedList;

/**
 * Created by alvaro on 3/13/17.
 */
public class UserLineParser extends Dialog {
    private LinkedList<SeparatorParser> parsers = new LinkedList<>();

    @Override
    public boolean parseLine(String line) {
        SeparatorParser parserValueAndTopicDeprecated = new SeparatorParser(VALUE_TOPIC_SEPARATOR, line, COUNT_PIPE_SEPARATOR_DEPRECATED);
        SeparatorParser parserValueTopicAndAssesment = new SeparatorParser(VALUE_TOPIC_SEPARATOR, line, COUNT_PIPE_SEPARATOR);
        parsers.add(parserValueAndTopicDeprecated);
        parsers.add(parserValueTopicAndAssesment);
        valueLine = new ValueLine(parsers);
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
