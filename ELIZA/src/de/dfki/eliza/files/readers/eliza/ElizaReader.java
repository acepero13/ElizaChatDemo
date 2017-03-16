package de.dfki.eliza.files.readers.eliza;

import de.dfki.eliza.files.filestystem.FileSystemAble;
import de.dfki.eliza.files.models.Conversation;
import de.dfki.eliza.files.parsers.*;
import de.dfki.eliza.files.parsers.dialog.Dialog;
import de.dfki.eliza.files.readers.FileReader;

import java.util.LinkedList;

/**
 * Created by alvaro on 3/13/17.
 */
public class ElizaReader extends FileReader {

    LinkedList<Conversation> conversations = new LinkedList<>();
    private Dialog firstDialogParser;

    public ElizaReader(String fileName, FileSystemAble fs) {
        fileSystem = fs;
        filename = fileName;
        firstDialogParser = new NewChatParser(conversations);
        Dialog infoUserDialog = new UserInfoLineParser();
        Dialog infoDialog = new InfoLineParser();
        Dialog userLine = new UserLineParser();
        Dialog systemLine = new SystemLineParser();
        Dialog defenseStratey = new DefenseStrategyParser();

        systemLine.setNextParser(defenseStratey);
        userLine.setNextParser(systemLine);
        infoDialog.setNextParser(userLine);
        infoUserDialog.setNextParser(infoDialog);
        firstDialogParser.setNextParser(infoUserDialog);

    }

    @Override
    public void parse(String line) {
        firstDialogParser.parse(line);
    }

    public int getTotalConversations(){
        return conversations.size();
    }

    public LinkedList<Conversation> getConversations(){
        return conversations;
    }
}
