package de.dfki.eliza.files.parsers;

import de.dfki.eliza.files.builders.ChatParser;
import de.dfki.eliza.files.models.Info;
import de.dfki.eliza.files.models.Textable;
import de.dfki.eliza.files.parsers.dialog.*;
import de.dfki.eliza.files.utils.NameRegexFinder;



/**
 * Created by alvaro on 3/13/17.
 */
public class UserInfoLineParser extends Dialog {
    public static final String INFO_LINE = "info:";
    private NameRegexFinder regexFinder = new NameRegexFinder();
    private String agentName;


    @Override
    public boolean parseLine(String line) {
        dialogLine = new DialogLine(line, INFO_LINE);
        valueLine = new NoValueLine();
        boolean hasName = regexFinder.parse(line);
        agentName = regexFinder.getName();
        return isParseable(line, hasName);

    }

    @Override
    public void postParsed() {
        Textable info = new Info(dialogLine.getText());
        conversationFactory.getConversation().addMessage(info);
    }


    public String getAgentName() {
        return agentName;
    }

    private boolean isParseable(String line, boolean hasName) {
        return hasName && line.startsWith(INFO_LINE);
    }


}
