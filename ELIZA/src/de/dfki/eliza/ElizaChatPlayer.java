/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dfki.eliza;

import de.dfki.eliza.chat.ChatController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
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
        this.primaryStage.setTitle("Player");
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
        
        PlayerController controller = loader.getController();
        controller.setPlayerApp(this);
        
        Scene scene = new Scene(playerRoot);
        scene.getStylesheets().add("de/dfki/eliza/Style.css");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setX(0);
        primaryStage.show();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
