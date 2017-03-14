package de.dfki.eliza.files.parsers;

import de.dfki.eliza.files.builders.ChatParser;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by alvaro on 3/13/17.
 */
public class InfoLineTest {
    private ChatParser parser;

    @Test
    public void test_parse_LineStartWithInfo_True() {
        makeParser();
        boolean res = parser.parse("info: Sie sind nun im tele.ring Beratungs-Chat. ");
        assertTrue(res);
    }

    @Test
    public void test_parse_DoesNotStartWithInfo_False() {
        makeParser();
        boolean res = parser.parse("user: Hello world");
        assertFalse(res);
    }


    private void makeParser() {
        parser = new InfoLineParser();
    }
}