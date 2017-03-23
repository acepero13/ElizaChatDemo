package de.dfki.eliza.files.utils;

import de.dfki.eliza.files.utils.nameparser.NameParserCollection;
import de.dfki.eliza.files.utils.nameparser.NameRegexFinder;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by alvaro on 3/21/17.
 */
public class NameParserCollectionTest {
    private NameParserCollection parserCollection;
    @Test
    public void test_getName_Emptyline_EmptyText()  {

    }

    @Test
    public void test_hasName_ParserReturnFalse_False() {
        makeParserCollection();
        FakeNameRegexParser p1 = new FakeNameRegexParser();
        p1.parsed = false;
        parserCollection.add(p1);
        String res = parserCollection.getName("");
        assertEquals("", res);
    }

    @Test
    public void test_hasName_ParserReturnTrue_True() {
        makeParserCollection();
        FakeNameRegexParser p1 = new FakeNameRegexParser();
        p1.parsed = true;
        p1.name = "Test";
        parserCollection.add(p1);
        String res = parserCollection.getName("");
        assertEquals(p1.name, res);
    }

    private void makeParserCollection() {
        parserCollection = new NameParserCollection();
    }

}

class FakeNameRegexParser extends NameRegexFinder{
    public boolean parsed = false;
    public String name;

    public boolean parse(String line){
        return parsed;
    }

    public String getName(){

        return name;
    }
}