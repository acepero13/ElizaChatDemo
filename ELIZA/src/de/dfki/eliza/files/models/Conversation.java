package de.dfki.eliza.files.models;

import java.util.LinkedList;

/**
 * Created by alvaro on 3/14/17.
 */
public class Conversation {
    private LinkedList<Textable> messages = new LinkedList<>();
    private DefenseStrategy defenseStrategy;
    private String systemName  = "{Name}";
    public void addMessage(Textable m){
        messages.add(m);
    }

    public int getTotalMessages(){
        return messages.size();
    }


    public DefenseStrategy getDefenseStrategy() {
        return defenseStrategy;
    }

    public void setDefenseStrategy(DefenseStrategy defenseStrategy) {
        this.defenseStrategy = defenseStrategy;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
}
