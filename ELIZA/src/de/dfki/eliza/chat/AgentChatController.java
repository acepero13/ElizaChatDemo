/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dfki.eliza.chat;

import de.dfki.connection.LiveSenderReceiver;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 *
 * @author EmpaT
 */
public class AgentChatController implements Initializable
{

    @FXML
    private AnchorPane agentChatRoot;
    @FXML
    private ScrollPane AgentChatScrollPane;
    @FXML
    private ImageView AgentEmoImageView;
    @FXML
    private TextArea agentInputtextArea;
    @FXML
    private Button agentSendButton;
    
    LiveSenderReceiver liveSenderReceiver;

    private GridPane chatGridPane;
    private HBox messageHBox;
    
    int rowIndex = 0;

    private FadeTransition fadeMessage = new FadeTransition(Duration.millis(500));
    private FadeTransition fadePath = new FadeTransition(Duration.millis(500));
    private ParallelTransition pt = new ParallelTransition();

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        liveSenderReceiver = new LiveSenderReceiver();
        liveSenderReceiver.start();
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (true)
                {                    
                    liveSenderReceiver.recvString();
                }
            }
        }).start();
        
        chatGridPane = new GridPane();
        AgentChatScrollPane.setContent(chatGridPane);
        AgentChatScrollPane.setStyle("-fx-background: #FFFFFF; -fx-border-color: #FFFFFF;");
        agentSendButton.setOnMouseClicked((event) ->
        {
            handleSendButton();
        });
        
        agentInputtextArea.setOnKeyPressed(new EventHandler<KeyEvent>()
    {
        @Override
        public void handle(KeyEvent ke)
        {
            if (ke.getCode().equals(KeyCode.ENTER))
            {
                handleSendButton();
                ke.consume();
            }
        }
    });
    }

    private void handleSendButton()
    {
        String text = agentInputtextArea.getText();
        Path face;
        if (!text.isEmpty())
        {
            Label message = new Label(text);
            message.setFont(new Font("Arial", 30));
            message.setWrapText(true);
            message.setPadding(new Insets(5, 5, 5, 5));
            message.setMaxWidth(800);
            message.setVisible(false);

            ColumnConstraints cc = new ColumnConstraints();
            cc.setFillWidth(true);
            cc.setHgrow(Priority.ALWAYS);
            chatGridPane.getColumnConstraints().clear();
            chatGridPane.getColumnConstraints().add(cc);
            chatGridPane.setVgap(10);

            AgentChatScrollPane.vvalueProperty().bind(chatGridPane.heightProperty());
            
            messageHBox = new HBox();

            createSystemMessageStyle(message);
            messageHBox.setAlignment(Pos.CENTER_RIGHT);
            GridPane.setHalignment(messageHBox, HPos.RIGHT);
            cc.setHalignment(HPos.RIGHT);
            face = creatRightFace(Color.rgb(255, 132, 202));
            face.setVisible(false);
            messageHBox.getChildren().addAll(message, face);
            createFadeEffect(fadeMessage, message);
            createFadeEffect(fadePath, face);
            pt.getChildren().clear();
            pt.getChildren().addAll(fadeMessage, fadePath);

            
                chatGridPane.add(messageHBox, 1, rowIndex);
                message.setVisible(true);
                face.setVisible(true);
                pt.play();
            
            
            rowIndex++;
            agentInputtextArea.setText("");
        }
    }

    private void createSystemMessageStyle(Label message)
    {
        message.setStyle("-fx-background-color: #FF84CA; "
                + "-fx-border-color: #FF84CA;  "
                + "-fx-border-radius: 10 10 10 10;\n"
                + "-fx-background-radius: 10 10 10 10;");
    }

    public Path creatRightFace(Color color)
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

    private void createFadeEffect(FadeTransition fadeTransition, Node node)
    {
        fadeTransition.setNode(node);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(false);
    }

}
