package de.dfki.eliza.files.models;

/**
 * Created by alvaro on 3/13/17.
 */
public class DefenseStrategy {
    private int value;
    private boolean isPinned;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public void setPinned() {
        isPinned = true;
    }

    public void unsetPinned() {
        isPinned = false;
    }
}
