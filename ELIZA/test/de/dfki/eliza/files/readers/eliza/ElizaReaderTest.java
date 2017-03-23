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
        LinkedList<String> conversations = makeNamedConversation("{Name}");
        FakeFileSystem fakeFS = makeFileSystem(conversations);
        makeReader("", fakeFS);
        reader.open();
        reader.read();
        int res = ((ElizaReader)reader).getTotalConversations();
        Conversation c = ((ElizaReader) reader).conversations.getFirst();
        assertEquals(1, res);
        assertNotNull(c.getAnnotation());
        assertEquals(6, c.getTotalMessages());
    }

    @Test
    public void test_read_FakeLinesWithDictEntryAsSystemName_ParsedConversationList() {
        LinkedList<String> conversations = makeNamedConversation("{dict-entry}");
        FakeFileSystem fakeFS = makeFileSystem(conversations);
        makeReader("", fakeFS);
        reader.open();
        reader.read();
        int res = ((ElizaReader)reader).getTotalConversations();
        Conversation c = ((ElizaReader) reader).conversations.getFirst();
        assertEquals(1, res);
        assertNotNull(c.getAnnotation());
        assertEquals(6, c.getTotalMessages());
    }

    @Test
    public void test_write_FakeLinesWithDictEntryAsSystemName_WrittenLines() {
        String name = "{dict-entry}";
        LinkedList<String> conversations = makeNamedConversation(name);
        FakeFileSystem fakeFS = makeFileSystem(conversations);
        makeReader("", fakeFS);
        reader.open();
        reader.read();
        ((ElizaReader)reader).getTotalConversations();
        Conversation c = ((ElizaReader) reader).conversations.getFirst();
        String res = ((ElizaReader) reader).write();
        String expected = "--------------------------\n" +
                            "info: Sie sind nun im T-Mobile Beratungs-Chat. Ein Kundenberater wird sich in Kürze mit Ihnen verbinden und sich sofort um Ihre Fragen kümmern.\n"
                        +   "info: Schön, dass wir miteinander verbunden sind, mein Name ist " + name +". Ich beantworte Ihnen gerne Ihre Fragen zu Ihrer Webbestellung im Privatkundenbereich.\n"
                        +   "Sie: Hallo " + name +", ich habe aktuell den Tarif {dict-entry} Max {Potentielle_ID} i ohne Bindung. |-1|-1|-1|\n"
                        +   name + ": Guten Tag werter Kunde |0|3|-1|\n"
                        +   name+ ": Guten Tag werter Kunde |0|3|-1|\n"
                        +   "Sie: Vielen Dank für die Infos. Und wenn ich nun |-1|-1|-1|\n"
                        +   "#31#1#-1\n" ;
        assertEquals(expected, res);

    }

    private FakeFileSystem makeFileSystem(LinkedList<String> conversations) {
        FakeFileSystem fakeFS = new FakeFileSystem("/tmp/test.txt");
        InputStreamReader input = new InputStreamReader(System.in);
        FakeBufferReader bufferedReader = new FakeBufferReader(input);
        bufferedReader.lines = conversations;
        fakeFS.bufferReader = bufferedReader;
        return fakeFS;
    }

    private LinkedList<String> makeNamedConversation(String name) {
        LinkedList<String> lines = new LinkedList<>();
        lines.add("--------------------------");
        lines.add("info: Sie sind nun im T-Mobile Beratungs-Chat. Ein Kundenberater wird sich in Kürze mit Ihnen verbinden und sich sofort um Ihre Fragen kümmern.");
        lines.add("info: Schön, dass wir miteinander verbunden sind, mein Name ist " + name +". Ich beantworte Ihnen gerne Ihre Fragen zu Ihrer Webbestellung im Privatkundenbereich. ");
        lines.add("Sie: Hallo " + name +", ich habe aktuell den Tarif {dict-entry} Max {Potentielle_ID} i ohne Bindung. ");
        lines.add(name + ": Guten Tag werter Kunde |0|3|");
        lines.add(name + ": Guten Tag werter Kunde |0|3|");
        lines.add("Sie: Vielen Dank für die Infos. Und wenn ich nun|-1|-1|-1|");
        lines.add("#31#1");
        return lines;
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
        public BufferedReader bufferReader;


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
            if(bufferReader == null){
                bufferReader = new FakeBufferReader(input);
            }

            return bufferReader;
        }

    }

    private class FakeBufferReader extends BufferedReader{

        public LinkedList<String> lines = new LinkedList();
        private int counter = 0;
        public FakeBufferReader(Reader in) {
            super(in);

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