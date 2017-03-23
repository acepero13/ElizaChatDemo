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

    private LinkedList<IntSeparatorParser> parsers = new LinkedList<>();

    @Override
    public boolean parseLine(String line) {
        initParsers(line);
        valueLine = new ValueLine(parsers);
        dialogLine = new DialogLine(line, USER_NAME);
        return line.startsWith(USER_NAME);
    }

    private void initParsers(String line) {
        parsers.clear();
        IntSeparatorParser parserValueAndTopicDeprecated = new IntSeparatorParser(VALUE_TOPIC_SEPARATOR, line, COUNT_PIPE_SEPARATOR_DEPRECATED);
        IntSeparatorParser parserValueTopicAndAssesment = new IntSeparatorParser(VALUE_TOPIC_SEPARATOR, line, COUNT_PIPE_SEPARATOR);
        parsers.add(parserValueAndTopicDeprecated);
        parsers.add(parserValueTopicAndAssesment);
    }

    public void postParsed(){
        Textable m = Message.createUserMessage(
                Dialog.USER_NAME,
                dialogLine.getText(),
                valueLine.getIntAt(VALUE_INDEX),
                valueLine.getIntAt(TOPIC_INDEX),
                valueLine.getIntAt(ASSESSMENT_INDEX));
        m.isUser(true);
        conversationFactory.getConversation().addMessage(m);
    }

    @Override
    public String getText() {
        return dialogLine.getText();
    }
}
