package de.dfki.eliza.files.models;

import de.dfki.eliza.files.filestystem.Writable;

/**
 * Created by alvaro on 3/13/17.
 */
public class Annotation implements Writable{
    private int value = -1;
    private boolean isPinned = false;
    private int assesment = -1;


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

    public int getAssesment() {
        return assesment;
    }

    public void setAssesment(int assesment) {
        this.assesment = assesment;
    }

    @Override
    public String write() {
        return "";
    }

    public String getPinnedString(){
        if(isPinned())
            return "1";
        return "0";

    }
}
