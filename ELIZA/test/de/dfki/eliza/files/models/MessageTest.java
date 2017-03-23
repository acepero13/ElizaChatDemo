package de.dfki.eliza.files.models;

import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Created by alvaro on 3/23/17.
 */
public class MessageTest {
    private Message message;

    @Test
    public void test_write_AgentGoodLine_WrittenLine() {
        String text = "Hello world";
        message = Message.createAgentMessage("{Name}:",text, 2, 1);
        String expected = "{Name}: " + text + " |1|2|-1|";
        String res = message.write();
        assertEquals(expected, res);
    }

    @Test
    public void test_write_UserGoodLine_WrittenLine() {
        String text = "Hello world";
        message = Message.createUserMessage("Sie:",text, 2, 1, 5);
        String expected = "Sie: " + text + " |1|2|5|";
        String res = message.write();
        assertEquals(expected, res);
    }

    @Test
    public void test_write_EmptyLine_Empty() {
        String text = "";
        message = Message.createAgentMessage("{Name}:",text, 2, 1);
        String expected = "";
        String res = message.write();
        assertEquals(expected, res);
    }
}