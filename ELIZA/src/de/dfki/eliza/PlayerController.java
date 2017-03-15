/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dfki.eliza;

import de.dfki.eliza.chat.ChatController;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author EmpaT
 */
public class PlayerController implements Initializable {

    @FXML
    private Button openFileButton;
    @FXML
    private Button playButton;
    @FXML
    private Spinner<Integer> playerSpinner;
    @FXML
    private Label openedLabel;

    ElizaChatPlayer elizaChatPlayer;

    private final int spinnerInitalValue = 1;
    private int spinnerCurrentValue = spinnerInitalValue;

    private boolean isPlayButtonClicked = false;

    String imagePath;
    Image image;
    ImageView playImageView;

    private Stage chatStage;
    private AnchorPane chatRoot;
    private ChatController chatController;

    private ScrollPane chatScrollPane;
    private GridPane chatGridPane;
    Label messages;

    //****************TEST***************//
    String text = "Beka";
    int rowIndex = 0;
    int colIndex = 2;
    //****************TESTEND***************//

    private FadeTransition fadeMessage = new FadeTransition(Duration.millis(500));
    private FadeTransition fadePath = new FadeTransition(Duration.millis(500));
    ParallelTransition pt = new ParallelTransition();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        imagePath = getClass().getClassLoader().getResource("play.png").toExternalForm();
        image = new Image(imagePath);
        playImageView = new ImageView(image);
        playButton.setGraphic(playImageView);

        playButton.setOnAction((event) -> {
            openedLabel.setVisible(false);
            if (!isPlayButtonClicked) {
                isPlayButtonClicked = !isPlayButtonClicked;
                imagePath = getClass().getClassLoader().getResource("stop.png").toExternalForm();

            } else {
                isPlayButtonClicked = !isPlayButtonClicked;
                imagePath = getClass().getClassLoader().getResource("play.png").toExternalForm();
            }

            image = new Image(imagePath);
            playImageView = new ImageView(image);
            playButton.setGraphic(playImageView);

            handleChatMessages();
        });

        openFileButton.setOnAction((event) -> {
            handleOpen();
        });

        SpinnerValueFactory<Integer> valueFactory
                = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, spinnerInitalValue);
        playerSpinner.setValueFactory(valueFactory);

        playerSpinner.setOnMouseClicked((event) -> {
            spinnerCurrentValue = playerSpinner.getValue();
        });
    }

    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(elizaChatPlayer.getPrimaryStage());
        if (file != null) {
            String filename = file.getAbsolutePath();
            OpenChat();
            handleOpenedLabel();
        }
    }

    public int getSpinnerCurrentValue() {
        return spinnerCurrentValue;
    }

    public void setPlayerApp(ElizaChatPlayer elizaChatPlayer) {
        this.elizaChatPlayer = elizaChatPlayer;
    }

    private void OpenChat() {
        chatStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ElizaChatPlayer.class.getResource("/de/dfki/eliza/chat/Chat.fxml"));
        try {
            chatRoot = (AnchorPane) loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        chatController = loader.getController();
        Scene scene = new Scene(chatRoot);
        chatStage.setScene(scene);
        chatStage.setX(600);
        chatStage.show();
    }

    private void handleChatMessages() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isPlayButtonClicked) {
                    Path face;
                    chatScrollPane = chatController.getChatScrollPane();
                    chatGridPane = chatController.getChatGridPane();
                    chatScrollPane.setVvalue(1.0);
                    messages = new Label(text);
                    messages.setWrapText(true);
                    messages.setPadding(new Insets(5, 5, 5, 5));
                    messages.setMaxWidth(600);

                    colIndex = colIndex % 2;
//                    messages.setStyle("-fx-background-color: red; -fx-border-color: black;  -fx-border-radius: 10 10 10 10;\n"
//                            + "-fx-background-radius: 10 10 10 10;");

                    ColumnConstraints cc = new ColumnConstraints();

                    chatGridPane.getColumnConstraints().clear();
                    chatGridPane.getColumnConstraints().add(cc);

                    HBox box = new HBox();

                    if (colIndex == 0) {
                        messages.setStyle("-fx-background-color: #DEDEDE; "
                                + "-fx-border-color: #DEDEDE;  "
                                + "-fx-border-radius: 10 10 10 10;\n"
                                + "-fx-background-radius: 10 10 10 10;");
                        cc.setHalignment(HPos.LEFT);
                        box.setAlignment(Pos.CENTER_LEFT);
                        GridPane.setHalignment(box, HPos.LEFT);
                        face = createLeftFace(Color.rgb(222, 222, 222));
                        box.getChildren().addAll(face, messages);
                    } else {
                        messages.setStyle("-fx-background-color: #9FD9FF; "
                                + "-fx-border-color: #9FD9FF;  "
                                + "-fx-border-radius: 10 10 10 10;\n"
                                + "-fx-background-radius: 10 10 10 10;");
                        
                        box.setAlignment(Pos.CENTER_RIGHT);
                        GridPane.setHalignment(box, HPos.RIGHT);
                        cc.setHalignment(HPos.RIGHT);
                        face = creatRightFace(Color.rgb(159, 217, 255));
                        box.getChildren().addAll(messages, face);
                    }
                    cc.setFillWidth(true);
                    cc.setHgrow(Priority.ALWAYS);

                    messages.setVisible(false);
                    face.setVisible(false);

                    createFadeEffect(fadeMessage, messages);
                    createFadeEffect(fadePath, face);

                    pt.getChildren().clear();
                    pt.getChildren().addAll(fadeMessage, fadePath);

                    Platform.runLater(()
                            -> {
                        chatGridPane.add(box, colIndex, rowIndex);
                        messages.setVisible(true);
                        face.setVisible(true);
                        pt.play();
                    });

                    try {
                        Thread.sleep(spinnerCurrentValue * 1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    rowIndex++;
                    colIndex++;
                    text += "Beka ";
                }

            }
        });

        t.start();
    }

    public Path createLeftFace(Color color) {
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

    public Path creatRightFace(Color color) {
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

    private void createFadeEffect(FadeTransition fadeTransition, Node node) {
        fadeTransition.setNode(node);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(false);
    }
    
    private void handleOpenedLabel()
    {
        FadeTransition labelfade = new FadeTransition(Duration.millis(500));
        labelfade.setNode(openedLabel);
        labelfade.setFromValue(0.0);
        labelfade.setToValue(1.0);
        labelfade.setCycleCount(1);
        labelfade.setAutoReverse(false);
        openedLabel.setVisible(true);
        labelfade.play();
    }

}
