/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dfki.eliza;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author EmpaT
 */
public class PlayerController implements Initializable{
    
    @FXML
    private Button openFileButton;
    @FXML
    private Button playButton;
    @FXML
    private Spinner<Integer> playerSpinner;
    
    private final int spinnerInitalValue = 1;
    
    private int spinnerCurrentValue = spinnerInitalValue;
    
    private boolean isPlayButtonClicked = false;
    String imagePath;
    Image image;
    ImageView playImageView;
    
    private Stage chatStage;
    private AnchorPane chatRoot;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        imagePath = getClass().getClassLoader().getResource("play.png").toExternalForm();
        image = new Image(imagePath);
        playImageView = new ImageView(image);
        playButton.setGraphic(playImageView);
        
        playButton.setOnAction((event) -> {
            if(!isPlayButtonClicked)
            {
                isPlayButtonClicked = !isPlayButtonClicked;
                imagePath = getClass().getClassLoader().getResource("stop.png").toExternalForm();
                
            }
            else
            {
                isPlayButtonClicked = !isPlayButtonClicked;
                imagePath = getClass().getClassLoader().getResource("play.png").toExternalForm();
            }
            
            image = new Image(imagePath);
            playImageView = new ImageView(image);
            playButton.setGraphic(playImageView);
            
        });
        
        openFileButton.setOnAction((event) -> {
            OpenChat();
        });
        
        SpinnerValueFactory<Integer> valueFactory = 
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, spinnerInitalValue);
        playerSpinner.setValueFactory(valueFactory);
        
        playerSpinner.setOnMouseClicked((event) -> {
            spinnerCurrentValue = playerSpinner.getValue();
        });
    }
    
    private void OpenChat()
    {
        chatStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ElizaChatPlayer.class.getResource("/de/dfki/eliza/chat/Chat.fxml"));
        try {
            chatRoot = (AnchorPane) loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        Scene scene = new Scene(chatRoot);
        chatStage.setScene(scene);
        chatStage.setX(600);
        chatStage.show();
    }

    public int getSpinnerCurrentValue() {
        return spinnerCurrentValue;
    }
    
}
