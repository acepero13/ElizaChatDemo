package de.dfki.eliza.files.parsers;

import de.dfki.eliza.files.builders.ChatParser;
import de.dfki.eliza.files.models.Conversation;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * Created by alvaro on 3/13/17.
 */
public class NewChatParserTest {
    private ChatParser parser;

    @Test
    public void test_parse_NewChatLine_True() {
        LinkedList<Conversation> c = new LinkedList<>();
        makeParser(c);
        String line =  "------------";
        boolean res = parser.parse(line);
        assertTrue(res);
    }

    @Test
    public void test_parser_NewLine_NewConversation() {
        LinkedList<Conversation> c = new LinkedList<>();
        makeParser(c);
        String line =  "------------";
        parser.parse(line);
        assertEquals(1, c.size());
    }

    private void makeParser(LinkedList<Conversation> conversations) {
        parser = new NewChatParser(conversations);
    }


}