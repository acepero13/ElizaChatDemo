package de.dfki.eliza.files.utils.nameparser;

/**
 * Created by alvaro on 3/21/17.
 */
public class DotParser implements InfoNameParser {

    public static final String NAME_START_SENTENCE = "mein Name ist";
    private static final int NAME_WORD_LIMIT = 3;
    private String name = "";

    @Override
    public boolean parse(String line) {
        boolean hasName = false;
        if(line.contains(NAME_START_SENTENCE)){
            hasName = findName(line);
        }
        return hasName;
    }

    private boolean findName(String line) {
        int indexName = line.indexOf(NAME_START_SENTENCE);
        return findDot(line, indexName);
    }

    private boolean findDot(String line, int indexName) {
        if(indexName < 0)
            return false;
        int dotPosition = line.indexOf('.', indexName);
        return parseName(line, indexName, dotPosition);
    }

    private boolean parseName(String line, int indexName, int dotPosition) {
        if(dotPosition < 0)
            return false;
        int startNamePos = indexName + NAME_START_SENTENCE.length();
        name = line.substring(startNamePos, dotPosition);
        int howManyWordsInName = countWordsInName();
        return howManyWordsInName < NAME_WORD_LIMIT;
    }

    private int countWordsInName(){
        String trim = name.trim();
        if (trim.isEmpty())
            return 0;
        return trim.split("\\s+").length;
    }

    @Override
    public String getName() {
        return name.trim();
    }
}
