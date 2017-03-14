package de.dfki.eliza.files.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by alvaro on 3/14/17.
 */
public class NameRegexFinderTest {

    private NameRegexFinder regex;

    @Test
    public void test_parse_CorrectSimpleName_Name() {
        makeRegex();
        String expected = "{Name}";
        regex.parse("TEST " + expected);
        String res = regex.getName();
        assertEquals(expected, res);
    }

    @Test
    public void test_parse_CorrectComplexName_Name() {
        makeRegex();
        String expected = "{dict-entry}";
        regex.parse("TEST " + expected);
        String res = regex.getName();
        assertEquals(expected, res);
    }

    @Test
    public void test_parse_BadName_False() {
        makeRegex();
        String expected = "{dict+entry}";
        boolean res = regex.parse("TEST " + expected);
        assertFalse(res);
    }

    @Test
    public void test_parse_Emty_False() {
        makeRegex();
        boolean res = regex.parse("");
        assertFalse(res);
    }

    private void makeRegex() {
        regex = new NameRegexFinder();
    }

}