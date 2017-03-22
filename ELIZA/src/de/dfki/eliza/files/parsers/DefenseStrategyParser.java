package de.dfki.eliza.files.parsers;

import de.dfki.eliza.files.models.Annotation;
import de.dfki.eliza.files.parsers.dialog.Dialog;
import de.dfki.eliza.files.parsers.dialog.NoDialogLine;
import de.dfki.eliza.files.parsers.dialog.ValueLine;

/**
 * Created by alvaro on 3/13/17.
 */
public class DefenseStrategyParser extends Dialog {
    public static final String DEFENSE_SEPARATOR = "#";
    public static final int POSSIBLE_VALUES = 2;
    private Annotation annotation = new Annotation();

    @Override
    public boolean parseLine(String line) {
        IntSeparatorParser parser = new IntSeparatorParser(DEFENSE_SEPARATOR, line, POSSIBLE_VALUES);
        valueLine =  new ValueLine(parser);
        dialogLine = new NoDialogLine();
        return line.startsWith(DEFENSE_SEPARATOR);
    }


    public void postParsed() {
        annotation.setValue(valueLine.getIntAt(0));
        int pinnedInt = valueLine.getIntAt(1);
        if (pinnedInt == 1) {
            annotation.setPinned();
        } else {
            annotation.unsetPinned();
        }
        conversationFactory.getConversation().setAnnotation(annotation);

    }


    public Annotation getAnnotation() {
        return annotation;
    }
}
