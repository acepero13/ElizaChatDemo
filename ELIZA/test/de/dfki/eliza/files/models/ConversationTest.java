package de.dfki.eliza.files.models;

import de.dfki.eliza.files.parsers.dialog.Dialog;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by alvaro on 3/23/17.
 */
public class ConversationTest {
    private Conversation conversation;

    @Test
    public void test_write_InfoMessageAndUserMessageAndSystemMeesage_WrittenLine() {
        makeConversation();
        Textable info = new Info("Info text");
        Textable user = Message.createUserMessage(Dialog.USER_NAME, "User Text", 1, 2, 3);
        Textable system = Message.createAgentMessage("{Name}:", "System text", 1,2);
        conversation.addMessage(info);
        conversation.addMessage(user);
        conversation.addMessage(system);
        Annotation annotation = new Annotation();
        annotation.setValue(1);
        annotation.setAssesment(3);
        annotation.setPinned();
        conversation.setAnnotation(annotation);
        String expected = "--------------------------\n" +
                "info: Info text\n" +
                "Sie: User Text |2|1|3|\n" +
                "{Name}: System text |2|1|-1|\n" +
                "#1#1#3\n";
        String res = conversation.write();
        assertEquals(expected, res);

    }

    @Test
    public void test_write_InfoMessageAndUserMessageAndSystemMeesageUnpinned_WrittenLine() {
        makeConversation();
        Textable info = new Info("Info text");
        Textable user = Message.createUserMessage(Dialog.USER_NAME, "User Text", 1, 2, 3);
        Textable system = Message.createAgentMessage("{Name}:", "System text", 1,2);
        conversation.addMessage(info);
        conversation.addMessage(user);
        conversation.addMessage(system);
        Annotation annotation = new Annotation();
        annotation.setValue(1);
        annotation.setAssesment(3);
        annotation.unsetPinned();
        conversation.setAnnotation(annotation);
        String expected = "--------------------------\n" +
                "info: Info text\n" +
                "Sie: User Text |2|1|3|\n" +
                "{Name}: System text |2|1|-1|\n" +
                "#1#0#3\n";
        String res = conversation.write();
        assertEquals(expected, res);

    }

    void makeConversation() {
        conversation = new Conversation();
    }
}