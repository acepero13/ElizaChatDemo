package de.dfki.eliza.files.parsers;

import de.dfki.eliza.files.builders.ChatParser;
import de.dfki.eliza.files.models.DefenseStrategy;
import de.dfki.eliza.files.parsers.dialog.Dialog;
import de.dfki.eliza.files.parsers.dialog.DialogLine;
import de.dfki.eliza.files.parsers.dialog.NoDialogLine;
import de.dfki.eliza.files.parsers.dialog.ValueLine;

/**
 * Created by alvaro on 3/13/17.
 */
public class DefenseStrategyParser extends Dialog {
    public static final String DEFENSE_SEPARATOR = "#";
    public static final int POSSIBLE_VALUES = 2;
    private DefenseStrategy defenseStrategy = new DefenseStrategy();

    @Override
    public boolean parseLine(String line) {
        SeparatorParser parser = new SeparatorParser(DEFENSE_SEPARATOR, line, POSSIBLE_VALUES);
        valueLine =  new ValueLine(parser);
        dialogLine = new NoDialogLine();
        return line.startsWith(DEFENSE_SEPARATOR);
    }


    public void postParsed() {
        defenseStrategy.setValue(valueLine.getIntAt(0));
        int pinnedInt = valueLine.getIntAt(1);
        if (pinnedInt == 1) {
            defenseStrategy.setPinned();
        } else {
            defenseStrategy.unsetPinned();
        }
        conversationFactory.getConversation().setDefenseStrategy(defenseStrategy);

    }


    public DefenseStrategy getDefenseStrategy() {
        return defenseStrategy;
    }
}
