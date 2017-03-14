package de.dfki.eliza.files.readers.eliza;

import de.dfki.eliza.files.exceptions.IncorrectFileExtension;
import de.dfki.eliza.files.filestystem.FileSystemAble;
import de.dfki.eliza.files.filestystem.eliza.ElizaFileSystem;
import de.dfki.eliza.files.models.Conversation;
import de.dfki.eliza.files.readers.FileReader;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * Created by alvaro on 3/13/17.
 */
public class ElizaReaderTest {
    private FileReader reader;

    @Test
    public void test_Open_WithExistingFilePathAndCorrectExtension_True() throws IncorrectFileExtension, FileNotFoundException {
        FileSystemAble fakeFS = new FakeFileSystem("/tmp/test.txt");
        makeReader("", fakeFS);
        boolean res = reader.open();
        assertTrue(res);
    }
    @Test
    public void test_Open_WithExistingFileAndWrongExtension_False() throws IncorrectFileExtension, FileNotFoundException {
        FakeFileSystem fakeFS = new FakeFileSystem("/tmp/test.txt");
        fakeFS.fileExtension = "rmdl";
        makeReader("", fakeFS);
        boolean res = reader.open();
        assertFalse(res);
    }

    @Test
    public void test_Open_WithNonExistingFileAndCorrectExtension_False() throws IncorrectFileExtension, FileNotFoundException {
        FakeFileSystem fakeFS = new FakeFileSystem("/tmp/test.txt");
        fakeFS.exists=false;
        makeReader("", fakeFS);
        boolean res = reader.open();
        assertFalse(res);
    }

    @Test
    public void test_read_FakeLines_ParsedConversationList() {
        FakeFileSystem fakeFS = new FakeFileSystem("/tmp/test.txt");
        makeReader("", fakeFS);
        reader.open();
        reader.read();
        int res = ((ElizaReader)reader).getTotalConversations();
        Conversation c = ((ElizaReader) reader).conversations.getFirst();
        assertEquals(1, res);
        assertNotNull(c.getDefenseStrategy());
        assertEquals(6, c.getTotalMessages());
    }

    private void makeReader(String fileName, FileSystemAble fs) {
        if(fileName.equals("")){
            reader = new ElizaReader( "",fs);
        }else {
            reader = new ElizaReader(fileName, fs);
        }
    }

    private class FakeFileSystem extends ElizaFileSystem {
        private boolean exists = true;
        private String fileExtension = "txt";

        public FakeFileSystem(String filename) {
            super(filename);
        }

        @Override
        public boolean fileExists() {
            return exists;
        }

        @Override
        public String getFileExtension() {
            return fileExtension;
        }

        public BufferedReader getBufferedReader() throws FileNotFoundException{
            InputStreamReader input = new InputStreamReader(System.in);
            return new FakeBufferReader(input);
        }

    }

    private class FakeBufferReader extends BufferedReader{

        public LinkedList<String> lines = new LinkedList();
        private int counter = 0;
        public FakeBufferReader(Reader in) {
            super(in);
            lines.add("--------------------------");
            lines.add("info: Sie sind nun im T-Mobile Beratungs-Chat. Ein Kundenberater wird sich in Kürze mit Ihnen verbinden und sich sofort um Ihre Fragen kümmern.");
            lines.add("info: Schön, dass wir miteinander verbunden sind, mein Name ist {Name}. Ich beantworte Ihnen gerne Ihre Fragen zu Ihrer Webbestellung im Privatkundenbereich. ");
            lines.add("Sie: Hallo {Name}, ich habe aktuell den Tarif {dict-entry} Max {Potentielle_ID} i ohne Bindung. ");
            lines.add("{Name}: Guten Tag werter Kunde |0|3|");
            lines.add("{Name}: Guten Tag werter Kunde |0|3|");
            lines.add("Sie: Vielen Dank für die Infos. Und wenn ich nun");
            lines.add("#31#1");

        }

        public String readLine(){
            if(counter >= lines.size()){
                counter = 0;
                return  null;
            }
            String value = lines.get(counter);
            counter++;
            return value;
        }
    }
}