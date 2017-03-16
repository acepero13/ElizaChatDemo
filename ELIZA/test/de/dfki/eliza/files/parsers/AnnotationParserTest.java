package de.dfki.eliza.files.parsers;

import de.dfki.eliza.files.builders.ChatParser;
import de.dfki.eliza.files.models.Annotation;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by alvaro on 3/13/17.
 */
public class AnnotationParserTest {
    private  ChatParser parser;


    @Test
    public void test_parse_LineWithDefenseMarker_True() {
        makeParser();
        boolean res = parser.parse("#31#1");
        assertTrue(res);
    }

    @Test
    public void test_parse_LineWithNoDefenseMarker_False() {
        makeParser();
        boolean res = parser.parse("Hello world");
        assertFalse(res);
    }

    @Test
    public void test_parse_DefenseLineValueAndNotPinned_DefenseStrategyObject() {
        makeParser();
        parser.parse("#31#1");
        Annotation annotation = ((DefenseStrategyParser)parser).getAnnotation();
        assertEquals(31, annotation.getValue());
        assertEquals(true, annotation.isPinned());
    }

    private void makeParser() {
        parser = new DefenseStrategyParser();
    }
}