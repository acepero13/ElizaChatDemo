package de.dfki.eliza.files.models;

import de.dfki.eliza.files.filestystem.Writable;

import java.util.LinkedList;

/**
 * Created by alvaro on 3/14/17.
 */
public class Conversation implements Writable {
    public static final String CHAT_INIT_MARKER = "--------------------------";
    public static final String ANNOTATION_SEPARATOR = "#";
    public static final String NEW_LINE_SEPARATOR = "\n";
    private LinkedList<Textable> messages = new LinkedList<>();
    private Annotation annotation;
    private String systemName  = "{Name}";
    public void addMessage(Textable m){
        messages.add(m);
    }

    public int getTotalMessages(){
        return messages.size();
    }


    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public LinkedList<Textable> getMessages(){
        return messages;
    }

    @Override
    public String write() {
        String line = CHAT_INIT_MARKER + NEW_LINE_SEPARATOR;
        line = writeMessages(line);
        line += writeAnnotations();
        return line + NEW_LINE_SEPARATOR;
    }

    String writeAnnotations() {
        return ANNOTATION_SEPARATOR + annotation.getValue()
                + ANNOTATION_SEPARATOR + annotation.getPinnedString()
                + ANNOTATION_SEPARATOR + annotation.getAssesment();
    }

    String writeMessages(String line) {
        for (Textable message : messages) {
            Writable writableMessage = (Writable) message;
            line += writableMessage.write();
            line += NEW_LINE_SEPARATOR;
        }
        return line;
    }
}
