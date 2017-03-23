package de.dfki.eliza.files.parsers;

import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by alvaro on 3/13/17.
 */
public class IntSeparatorParserTest {

    private IntSeparatorParser parser;

    @Test
    public void test_parse_SeparatorHashNoValues_Empty() {
        makeParser("#", "", 2);
        parser.parse();
        LinkedList<Integer> values = parser.getValues();
        assertEquals(0, values.size());
    }


    @Test
    public void test_parse_SeparatorHashNoValuesMoreAppearances_Empty() {
        makeParser("#", "#31#1", 3);
        parser.parse();
        LinkedList<Integer> values = parser.getValues();
        assertEquals(2, values.size());
    }

    @Test
    public void test_parse_SeparatorHasTwoValues_ArrayWithTwoValues() {
        makeParser("#", "#31#1", 2);
        parser.parse();
        LinkedList<Integer> values = parser.getValues();
        assertEquals(2, values.size());
        assertEquals(31, parser.getIntAt(0));
        assertEquals("1", parser.getStringAt(1));
    }

    @Test
    public void test_parse_ValueAndTopic_ArrayWithTwoValues() {
        makeParser("\\|", "|1|2|", 2);
        parser.parse();
        LinkedList<Integer> values = parser.getValues();
        assertEquals(2, values.size());
        assertEquals(1, parser.getIntAt(0));
        assertEquals("2", parser.getStringAt(1));

    }

    @Test
    public void test_parse_TextWithPipesBadFormat_EmptyArray() {
        String text = "{dict-entry} iPhone 6S 16 GB = 618 € | iPhone 6S 64 GB = 826 € | iPhone SE 16 GB = 384 € | iPhone SE 64 GB = 486 €. Alles sind brutto-Angaben.";
        makeParser("\\|", text, 3);
        parser.parse();
        LinkedList<Integer> values = parser.getValues();
        assertEquals(IntSeparatorParser.DEFAULT_INT_VALUE, parser.getIntAt(0));
    }

    @Test
    public void test_parse_TextWithPipesBadFormatValuesAtTheEnd_ArrayWithValues() {
        String text = "{dict-entry} iPhone 6S 16 GB = 618 € | iPhone 6S 64 GB = 826 € | iPhone SE 16 GB = 384 € | iPhone SE 64 GB = 486 €. Alles sind brutto-Angaben.|1|2|";
        makeParser("\\|", text, 2);
        boolean res = parser.parse();
        assertTrue(res);
        LinkedList<Integer> values = parser.getValues();
        assertEquals(1, parser.getIntAt(0));
    }

    @Test
    public void test_parse_SeparatorHasThreeValues_ArrayWithThreeValues() {
        makeParser("#", "#31#0#3", 3);
        parser.parse();
        LinkedList<Integer> values = parser.getValues();
        assertEquals(3, values.size());
        assertEquals("31", parser.getStringAt(0));
        assertEquals(0, parser.getIntAt(1));
        assertEquals(3, parser.getIntAt(2));
    }

    @Test
    public void test_getIntAt_BiggerThanSize_ExceptionOutOfBound() {
        makeParser("#", "#31#1#3", 3);
        parser.parse();
        int res = parser.getIntAt(4);
        assertEquals(-1, res);

    }

    @Test
    public void test_getStringAt_BiggerThanSize_ExceptionOutOfBound() {
        makeParser("#", "#31#1#3", 3);
        parser.parse();
        String res = parser.getStringAt(4);
        assertEquals("", res);
    }

    private void makeParser(String separator, String text, int appearances) {
        parser = new IntSeparatorParser(separator, text, appearances);
    }
}