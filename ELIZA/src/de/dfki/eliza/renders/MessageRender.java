package de.dfki.eliza.renders;

import de.dfki.eliza.files.models.Textable;
import de.dfki.eliza.renderer.Renderable;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * Created by alvaro on 3/29/17.
 */
public abstract class MessageRender implements Renderable {
    protected GridPane chatGridPane;
    protected ColumnConstraints columnConstraints;
    protected HBox hBox;
    protected FadeTransition fadeMessage = new FadeTransition(Duration.millis(500));
    protected FadeTransition fadePath = new FadeTransition(Duration.millis(500));
    protected ParallelTransition pt = new ParallelTransition();

    public MessageRender(GridPane gridPane){
        this.chatGridPane = gridPane;
    }


    protected Label messages;

    private void createBasePanels(String text){
        messages = new Label(text);
        messages.setFont(new Font("Arial", 30));
        messages.setWrapText(true);
        messages.setPadding(new Insets(5, 5, 5, 5));
        messages.setMaxWidth(800);
        messages.setVisible(false);
        columnConstraints = new ColumnConstraints();
        columnConstraints.setFillWidth(true);
        columnConstraints.setHgrow(Priority.ALWAYS);
        chatGridPane.getColumnConstraints().clear();
        chatGridPane.getColumnConstraints().add(columnConstraints);
        chatGridPane.setVgap(10);
        hBox = new HBox();
    }

    @Override
    public void render(int i, Textable textable){
        createBasePanels(textable.getText());
        customRender(i, textable);
    }

    protected abstract void customRender(int i, Textable textable);

    protected Path createLeftFace(Color color)
    {
        Path p = new Path();
        p.getElements().add(new MoveTo(0, 0));
        p.getElements().add(new QuadCurveTo(5, 2, 10, -5));
        p.getElements().add(new LineTo(10, 10));
        p.getElements().add(new ClosePath());
        p.setTranslateX(2);
        p.setFill(color);
        p.setStroke(color);

        return p;
    }

    protected Path creatRightFace(Color color)
    {
        Path p = new Path();
        p.getElements().add(new MoveTo(0, 0));
        p.getElements().add(new QuadCurveTo(-5, 2, -10, -5));
        p.getElements().add(new LineTo(-10, 10));
        p.getElements().add(new ClosePath());
        p.setTranslateX(-2);
        p.setFill(color);
        p.setStroke(color);

        return p;
    }

    protected void createFadeEffect(FadeTransition fadeTransition, Node node)
    {
        fadeTransition.setNode(node);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(false);
    }
}
