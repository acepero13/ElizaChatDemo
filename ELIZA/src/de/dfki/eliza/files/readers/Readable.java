package de.dfki.eliza.files.readers;

/**
 * Created by alvaro on 3/6/17.
 * Interfacee
 */
interface Readable {
    boolean open(String filname);

    boolean open();

    void read();

    void parse(String line);
}
