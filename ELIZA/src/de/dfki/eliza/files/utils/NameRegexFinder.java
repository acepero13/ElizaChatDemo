package de.dfki.eliza.files.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by alvaro on 3/14/17.
 */
public class NameRegexFinder {
    private String name = "";
    private Pattern p;
    private boolean found = false;

    public boolean parse(String txt) {

        String re1 = ".*?";    // Non-greedy match on filler
        String re2 = "(\\{)";    // Any Single Character 1
        String re3 = "((?:[a-z][a-z]+))";    // Word 1
        String re4 = "(-)*";    // Any Single Character 2
        String re5 = "((?:[a-z][a-z]+))";    // Word 2
        String re6 = "(\\})";    // Any Single Character 3
        p = Pattern.compile(re1 + re2 + re3 + re4 + re5 + re6, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(txt);
        found = m.find();
        if (found) {
            buildName(m);
        }
        return found;
    }

    private String buildName(Matcher m) {
        String c1 = m.group(1);
        String word1 = m.group(2);
        String c2 = m.group(3);
        String word2 = m.group(4);
        String c3 = m.group(5);
        if (c2 != null) {
            name = c1 + word1 + c2 + word2 + c3;
        } else {
            name = c1 + word1 + word2 + c3;
        }
        return getName();
    }

    public String getName() {
        return name;
    }

}
