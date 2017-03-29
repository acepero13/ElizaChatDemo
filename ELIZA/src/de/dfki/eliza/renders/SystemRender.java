package de.dfki.eliza.renders;

import de.dfki.eliza.files.models.Textable;
import de.dfki.eliza.renderer.Renderable;
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
public class SystemRender extends MessageRender {
    public SystemRender(GridPane gridPane){
        super(gridPane);
    }



    @Override
    protected void customRender(int i, Textable textable) {
        Path face;
        createSystemMessageStyle(messages);
        columnConstraints.setHalignment(HPos.RIGHT);
        hBox.setAlignment(Pos.CENTER_RIGHT);
        GridPane.setHalignment(hBox, HPos.RIGHT);
        face = creatRightFace(Color.rgb(255, 132, 202));
        face.setVisible(false);
        hBox.getChildren().addAll(messages, face);
        createFadeEffect(fadeMessage, messages);
        createFadeEffect(fadePath, face);
        pt.getChildren().clear();
        pt.getChildren().addAll(fadeMessage, fadePath);
        Platform.runLater(()->
        {
            chatGridPane.add(hBox, 1, i);
            messages.setVisible(true);
            face.setVisible(true);
            pt.play();
        });
    }

    private void createSystemMessageStyle(Label message)
    {
        message.setStyle("-fx-background-color: #FF84CA; "
                + "-fx-border-color: #FF84CA;  "
                + "-fx-border-radius: 10 10 10 10;\n"
                + "-fx-background-radius: 10 10 10 10;");
    }
}
