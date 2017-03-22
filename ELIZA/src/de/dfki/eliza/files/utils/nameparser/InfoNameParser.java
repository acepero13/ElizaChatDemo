package de.dfki.eliza.files.utils.nameparser;

/**
 * Created by alvaro on 3/21/17.
 */
public interface InfoNameParser {
    boolean parse(String line);
    String getName();
}
