package de.dfki.eliza.files.parsers;

import de.dfki.eliza.files.builders.ChatParser;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by alvaro on 3/13/17.
 */
public class UserInfoLineParserTest {
    private UserInfoLineParser parser;

    @Test
    public void test_parse_LineWithInfoHeaderAndUserInfo_True() {
        makeParser();
        String line = "info: Schön, dass wir miteinander verbunden sind, mein Name ist {Name}. Ich beantworte Ihnen gerne Ihre Fragen ";
        boolean res = parser.parse(line);
        assertTrue(res);
    }

    @Test
    public void test_parse_LineWithInfoHeaderAndNoUserInfo_False() {
        makeParser();
        String line = "info: Schön, dass wir miteinander verbunden sind, mein. Ich beantworte Ihnen gerne Ihre";
        boolean res = parser.parse(line);
        assertFalse(res);
    }

    @Test
    public void test_parse_LineWithNoInfoHeaderAndUserInfo_False() {
        makeParser();
        String line = "Schön, dass wir miteinander verbunden sind, mein Name ist {Name}. Ich beantworte Ihnen gerne Ihre";
        boolean res = parser.parse(line);
        assertFalse(res);
    }

    @Test
    public void test_parse_LineWithNoInfoHeaderAndUserInfo_True() {
        makeParser();
        String line = "Schön, dass wir miteinander verbunden sind, mein Name ist {Name}. Ich beantworte Ihnen gerne Ihre Fragen ";
        boolean res = parser.parse(line);
        assertFalse(res);
    }

    @Test
    public void test_getName_LineWithInfoHeaderAndUserInfo_Name() {
        makeParser();
        String expectedName = "{Name}";
        String line = "info: Schön, dass wir miteinander verbunden sind, mein Name ist " + expectedName +". Ich beantworte Ihnen gerne Ihre Fragen ";
        parser.parse(line);
        String name = parser.getAgentName();
        assertEquals(expectedName, name);
    }

    @Test
    public void test_getName_LineWithInfoHeaderAndComplexUserInfo_Name() {
        makeParser();
        String expectedName = "{dict-entry}";
        String line = "info: Schön, dass wir miteinander verbunden sind, mein Name ist " + expectedName +". Ich beantworte Ihnen gerne Ihre Fragen ";
        parser.parse(line);
        String name = parser.getAgentName();
        assertEquals(expectedName, name);
    }

    @Test
    public void test_parse_GoodTextLine_OnlyText() {
        makeParser();
        String expected = "Schön, dass wir miteinander verbunden sind, mein Name ist {Name}. Ich beantworte Ihnen gerne Ihre";
        String line = "info: " + expected;
        parser.parse(line);
        String res = parser.getText();
        assertEquals(expected, res);
    }

    @Test
    public void test_parse_GoodTextLineWithNameWithoutBracketsAndWithEndingPoint_OnlyText() {
        makeParser();
        String expected = "Hallo, guten tag, mein Name ist Mandy. es ist ein Vergnügen, Sie kennen zu lernen";
        String line = "info: " + expected;
        parser.parse(line);
        String res = parser.getText();
        assertEquals(expected, res);
    }


    private void makeParser() {
        parser = new UserInfoLineParser();
    }
}