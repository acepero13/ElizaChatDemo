package de.dfki.eliza.files.parsers;

import de.dfki.eliza.files.builders.ChatParser;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by alvaro on 3/13/17.
 */
public class UserLineParserTest {
    private UserLineParser parser;

    @Test
    public void test_parse_UserInLine_True() {
        makeParser();
        boolean res = parser.parse("Sie: Hallo {Name}, ich habe aktuell den Tarif");
        assertTrue(res);
    }

    @Test
    public void test_parse_UserNotInLine_False() {
        makeParser();
        boolean res = parser.parse(" Hallo, ich habe aktuell den Tarif");
        assertFalse(res);
    }

    @Test
    public void test_parse_UserInLineNotAtTheBegining_False() {
        makeParser();
        boolean res = parser.parse("Hallo {Name}, Sie:ich habe aktuell den Tarif");
        assertFalse(res);
    }

    @Test
    public void test_parse_UserLineWithTopicAndValue_TopicAndValue() {
        makeParser();
        String expected = "Hallo {Name}, ich habe aktuell den Tarif";
        parser.parse("Sie: " + expected + " |3|2|");
        String text = parser.getText();
        assertEquals(expected, text);
        int res = parser.getIntAt(0);
        assertEquals(res, 3);
    }

    @Test
    public void test_parse_UserLineWithTopicAndValueAndAssesment_TopicValueAndAssesMent() {
        makeParser();
        String expected = "Hallo {Name}, ich habe aktuell den Tarif";
        parser.parse("Sie: " + expected + " |3|2|4|");
        String text = parser.getText();
        int res = parser.getIntAt(0);
        assertEquals(res, 3);
    }


    private void makeParser() {
        parser = new UserLineParser();
    }
}