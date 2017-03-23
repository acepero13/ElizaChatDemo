package de.dfki.eliza.files.utils.nameparser;

import de.dfki.eliza.files.exceptions.LineHasNoName;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by alvaro on 3/21/17.
 */
public class NameParserCollection {
    private LinkedList<InfoNameParser> parsers = new LinkedList<>();
    public void add(InfoNameParser parser){
        parsers.add(parser);
    }

    public String getName(String line){
        return  tryToParseName(line);
    }

    private String tryToParseName(String line)  {
        String name = "";
        try {
            name = parseName(line);
        } catch (LineHasNoName lineHasNoName) {
            lineHasNoName.printStackTrace();
        }
        return name;
    }

    private String parseName(String line) throws LineHasNoName {
        InfoNameParser parser = findMatchingParser(line);
        return parser.getName();
    }

    private InfoNameParser findMatchingParser(String line) throws LineHasNoName {
        boolean parsed = false;
        Iterator iterator = parsers.iterator();
        InfoNameParser parser = null;
        while (iterator.hasNext() && !parsed){
            parser = (InfoNameParser) iterator.next();
            parsed = parser.parse(line);
        }
        if(!parsed){
            throw new LineHasNoName("Line has no name");
        }
        return parser;
    }
}
