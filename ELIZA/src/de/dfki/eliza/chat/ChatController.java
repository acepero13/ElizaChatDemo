/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dfki.eliza.chat;

import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

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
    private ImageView emotionImageView;
    @FXML
    private Button testButton;
    
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
//        chatGridPane.prefHeightProperty().bind(chatScrollPane.heightProperty());
        chatGridPane.setStyle("-fx-background-color: linear-gradient(to top, #F0F9FF, #B6DDFF);");
        testButton.setOnAction((event) -> {
            chatScrollPane.setVvalue(1.0);
            label = new Label(text);
            label.setWrapText(true);
            label.setPadding(new Insets(5, 5, 5, 5));
            
            colIndex = colIndex % 2;
            label.setStyle("-fx-border-color: black;  -fx-border-radius: 10 10 10 10;\n" +
                                "-fx-background-radius: 10 10 10 10;");
            
            ColumnConstraints cc = new ColumnConstraints();
            
            
            chatGridPane.getColumnConstraints().clear();
            chatGridPane.getColumnConstraints().add(cc);
            
            HBox box = new HBox();
            
            Path p = new Path();
            p.getElements().add(new MoveTo(0, 0));
            p.getElements().add(new QuadCurveTo(5, 2, 10, -5));
            p.getElements().add(new LineTo(10, 10));
            p.getElements().add(new ClosePath());
            p.setTranslateX(2);
            p.setFill(Color.RED);
            
            if(colIndex == 0)
            {
                cc.setHalignment(HPos.LEFT);
                box.setAlignment(Pos.CENTER_LEFT);
                GridPane.setHalignment(box, HPos.LEFT);
                box.getChildren().addAll(p,label);
            }  
            else
            {
                box.setAlignment(Pos.CENTER_RIGHT);
                GridPane.setHalignment(box, HPos.RIGHT);
                cc.setHalignment(HPos.RIGHT);
                box.getChildren().addAll(label,p);
            }
            cc.setFillWidth(true);
            cc.setHgrow(Priority.ALWAYS);
                
            
            chatGridPane.add(box, colIndex, rowIndex);
            
            rowIndex++;
            colIndex++;
            text+="Beka ";
        });
    }

    public AnchorPane getChatRoot() {
        return chatRoot;
    }

    public ScrollPane getChatScrollPane() {
        return chatScrollPane;
    }

    public ImageView getEmotionImageView() {
        return emotionImageView;
    }

    public GridPane getChatGridPane() {
        return chatGridPane;
    }
    
    
    
}
