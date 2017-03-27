package de.dfki.eliza;

import de.dfki.eliza.files.exceptions.NoValidConversation;
import de.dfki.eliza.files.models.Conversation;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * Created by alvaro on 3/24/17.
 */
public class ChatManagerTest {
    private ChatManager chat;

    @Test
    public void test_getTotalConversation_EmptyList_Zero() {
        makeConversation(new LinkedList<>());
        int res = chat.getTotalConversations();
        assertEquals(0, res);
    }

    void makeConversation(LinkedList<Conversation> conversations) {
        chat = new ChatManager(conversations);
    }

    @Test
    public void test_getNextConversation_TwoConversationsGetSecond_SecondConversation() throws NoValidConversation {
        LinkedList<Conversation> conversations = new LinkedList<>();
        makeTwoConversationChat(conversations);
        Conversation expected = conversations.get(1);
        makeConversation(conversations);
        chat.getNextConversation();
        Conversation res = chat.getNextConversation();
        assertEquals(expected, res);
    }

    Conversation makeTwoConversationChat(LinkedList<Conversation> conversations) {
        Conversation c1 = new Conversation();
        Conversation c2 = new Conversation();
        conversations.add(c1);
        conversations.add(c2);
        return c1;
    }

    @Test
    public void test_getNextConversation_TwoConversations_FirstConversation() throws NoValidConversation {
        LinkedList<Conversation> conversations = new LinkedList<>();
        Conversation c1 = makeTwoConversationChat(conversations);
        makeConversation(conversations);
        Conversation res = chat.getNextConversation();
        assertEquals(c1, res);
    }

    @Test(expected = NoValidConversation.class)
    public void test_getNextConversation_NoMoreConversations_Excpetion() throws NoValidConversation {
        makeConversation(new LinkedList<Conversation>());
        chat.getNextConversation();
    }

    @Test
    public void test_goToConversation_TwoConversationsGotToSecond_SecondConversation() throws NoValidConversation {
        LinkedList<Conversation> conversations = new LinkedList<>();
        makeTwoConversationChat(conversations);
        makeConversation(conversations);
        Conversation expected = conversations.get(1);
        Conversation res = chat.goToConversation(2);
        assertEquals(expected, res);
    }

    @Test(expected = NoValidConversation.class)
    public void test_goToConversation_TwoConversationOutOfIndex_Exception() throws NoValidConversation {
        LinkedList<Conversation> conversations = new LinkedList<>();
        makeTwoConversationChat(conversations);
        makeConversation(conversations);
        Conversation res = chat.goToConversation(10);
    }

    @Test
    public void test_getPrevious_GoToSecondConversation_First() throws NoValidConversation {
        LinkedList<Conversation> conversations = new LinkedList<>();
        makeTwoConversationChat(conversations);
        makeConversation(conversations);
        chat.goToConversation(2);
        Conversation res = chat.getPreviousConversation();
        Conversation expected = conversations.get(0);
        assertEquals(expected, res);
    }

    @Test(expected = NoValidConversation.class)
    public void test_getPreviousConversation_WasInFirst_Exception() throws NoValidConversation {
        LinkedList<Conversation> conversations = new LinkedList<>();
        makeTwoConversationChat(conversations);
        makeConversation(conversations);
        chat.getPreviousConversation();
    }

    @Test
    public void test_hasNext_Empty_False() {
        makeConversation(new LinkedList<>());
        boolean res = chat.hastNext();
        assertFalse(res);
    }

    @Test
    public void test_hasNext_TwoElementPositionedInTheFirst_True() throws NoValidConversation {
        LinkedList<Conversation> conversations = new LinkedList<>();
        makeTwoConversationChat(conversations);
        makeConversation(conversations);
        chat.getNextConversation();
        boolean res = chat.hastNext();
        assertTrue(res);
    }

    @Test
    public void test_hasPrevious_EmptyChat_False() {
        makeConversation(new LinkedList<>());
        boolean res = chat.hasPrevious();
        assertFalse(res);
    }

    @Test
    public void test_hasPrevious_TwoElementPositionedInTheFirst_True() throws NoValidConversation {
        LinkedList<Conversation> conversations = new LinkedList<>();
        makeTwoConversationChat(conversations);
        makeConversation(conversations);
        chat.getNextConversation();
        boolean res = chat.hasPrevious();
        assertTrue(res);
    }

}