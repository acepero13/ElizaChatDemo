/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dfki.eliza;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author EmpaT
 */
public class ElizaChatPlayer extends Application {
    
    private Stage primaryStage;
    private AnchorPane playerRoot = null;
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        
        initStage();
    }
    
    private void initStage()
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ElizaChatPlayer.class.getResource("player.fxml"));
        try {
            playerRoot = (AnchorPane) loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        String image = getClass().getClassLoader().getResource("playerBG.jpg").toExternalForm();
        playerRoot.setStyle("-fx-background-image: url('" + image + "'); " +
                            "-fx-background-position: center center; " +
                            "-fx-background-repeat: stretch;");

        Scene scene = new Scene(playerRoot);
        scene.getStylesheets().add("de/dfki/eliza/Style.css");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
