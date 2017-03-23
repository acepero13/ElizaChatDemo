package de.dfki.eliza.files.utils.nameparser;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by alvaro on 3/21/17.
 */
public class DotParserTest {
    private InfoNameParser parser;

    @Test
    public void test_parse_EmptyLine_False() {
        makeParser();
        String line = "";
        boolean res = parser.parse(line);
        assertFalse(res);
    }

    @Test
    public void test_parse_LineWithDot_True() {
        makeParser();
        String line = "Hallo welt, mein Name ist Mandy. Es freut mich kennenzulernen";
        boolean res = parser.parse(line);
        assertTrue(res);
    }

    @Test
    public void test_getName_LineWithDotAndName_Name() {
        makeParser();
        String line = "Hallo welt, mein Name ist Mandy. Es freut mich kennenzulernen";
        parser.parse(line);
        String res = parser.getName();
        assertEquals("Mandy", res);
    }

    @Test
    public void test_parse_LineWithoutDot_False() {
        makeParser();
        String line = "Hallo welt, mein Name ist Mandy Es freut mich kennenzulernen";
        boolean res = parser.parse(line);
        assertFalse(res);
    }

    @Test
    public void test_parse_LineWithDotAtTheEndFalseName_False() {
        makeParser();
        String line = "Hallo welt, mein Name ist Mandy Es freut mich kennenzulernen.";
        boolean res = parser.parse(line);
        assertFalse(res);
    }

    private void makeParser() {
        parser = new DotParser();
    }
}