/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dfki.eliza.chat;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 *
 * @author EmpaT
 */
public class ChatController implements Initializable{
    
    @FXML
    private AnchorPane chatRoot;
    @FXML
    private ScrollPane chatScrollPane;
    @FXML
    private ImageView emoImageView;
    @FXML
    private Label emoLabel1;
    @FXML
    private Label emoLabel2;
    @FXML
    private Label emoLabel3;
    @FXML
    private Label emoLabel4;
    
    private GridPane chatGridPane;
    
    private int labelPadding;
    String text = "Beka";
    int rowIndex = 0;
    int colIndex = 2;
    Label label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chatGridPane = new GridPane();
        
        chatScrollPane.setContent(chatGridPane);
        chatScrollPane.setStyle("-fx-background: #FFFFFF; -fx-border-color: #FFFFFF;");
    }

    public AnchorPane getChatRoot() {
        return chatRoot;
    }

    public ScrollPane getChatScrollPane() {
        return chatScrollPane;
    }

    public ImageView getEmotionImageView() {
        return emoImageView;
    }

    public GridPane getChatGridPane() {
        return chatGridPane;
    }

    public Label getEmoLabel1()
    {
        return emoLabel1;
    }

    public Label getEmoLabel2()
    {
        return emoLabel2;
    }

    public Label getEmoLabel3()
    {
        return emoLabel3;
    }

    public Label getEmoLabel4()
    {
        return emoLabel4;
    }
    
    
    
}
