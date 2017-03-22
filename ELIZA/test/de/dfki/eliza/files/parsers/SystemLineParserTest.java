package de.dfki.eliza.files.parsers;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by alvaro on 3/22/17.
 */
public class SystemLineParserTest {
    private SystemLineParser parser;

    @Test
    public void test_parse_Scenario_Behavior() {
        makeFakeParser();
        ((FakeSystemLineParser)parser).systemN = "{Name}";
        String expected = "Hallo welt";
        String line = "{Name}: " + expected;
        parser.parse(line);
        String res = parser.getText();
        assertEquals(expected, res);
    }

    void makeFakeParser() {
        parser = new FakeSystemLineParser();
    }

    private class FakeSystemLineParser extends SystemLineParser{
        public FakeSystemLineParser(){
            super();
        }
        public String systemN;
        void getSystemName(){
            systemName = systemN;
        }
    }
}

