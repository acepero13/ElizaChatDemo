package de.dfki.eliza.renders;

import de.dfki.eliza.files.models.Textable;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;

/**
 * Created by alvaro on 3/29/17.
 */
public class UserRender extends MessageRender {

    public UserRender(GridPane gridPane) {
        super(gridPane);
    }

    @Override
    protected void customRender(int i, Textable textable) {
        Path face;
        createUserMessageStyle(messages);

        hBox.setAlignment(Pos.BASELINE_LEFT);
        GridPane.setHalignment(hBox, HPos.LEFT);
        columnConstraints.setHalignment(HPos.LEFT);
        face = createLeftFace(Color.rgb(222, 222, 222));
        face.setVisible(false);
        hBox.getChildren().addAll(face, messages);
        createFadeEffect(fadeMessage, messages);
        createFadeEffect(fadePath, face);
        pt.getChildren().clear();
        pt.getChildren().addAll(fadeMessage, fadePath);
        Platform.runLater(()->
        {
            chatGridPane.add(hBox, 0, i);
            messages.setVisible(true);
            face.setVisible(true);
            pt.play();
        });
    }

    private void createUserMessageStyle(Label message)
    {
        message.setStyle("-fx-background-color: #DEDEDE; "
                + "-fx-border-color: #DEDEDE;  "
                + "-fx-border-radius: 10 10 10 10;\n"
                + "-fx-background-radius: 10 10 10 10;");
    }
}
