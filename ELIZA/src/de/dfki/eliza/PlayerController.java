/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dfki.eliza;

import de.dfki.connection.Sender;
import de.dfki.eliza.chat.ChatController;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dfki.eliza.files.filestystem.FileSystemAble;
import de.dfki.eliza.files.filestystem.eliza.ElizaFileSystem;
import de.dfki.eliza.files.models.Conversation;
import de.dfki.eliza.files.models.Message;
import de.dfki.eliza.files.models.Textable;
import de.dfki.eliza.files.readers.eliza.ElizaReader;
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
import javafx.scene.text.Font;
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
    Image emoImage;
    ImageView playImageView;

    private Stage chatStage;
    private AnchorPane chatRoot;
    private ChatController chatController;

    private ScrollPane chatScrollPane;
    private GridPane chatGridPane;
    Label messages;
    File file;

    Sender messageSender;

    //****************TEST***************//
    String text = "Beka";
    int rowIndex = 0;
    int colIndex = 2;
    //****************TESTEND***************//

    private FadeTransition fadeMessage = new FadeTransition(Duration.millis(500));
    private FadeTransition fadePath = new FadeTransition(Duration.millis(500));
    ParallelTransition pt = new ParallelTransition();
    private ElizaReader elizaReader;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messageSender = new Sender();
        imagePath = getClass().getClassLoader().getResource("play.png").toExternalForm();
        image = new Image(imagePath);
        playImageView = new ImageView(image);
        playButton.setGraphic(playImageView);

        playButton.setOnAction((event) -> {
            openedLabel.setVisible(false);
            if (file != null) {
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
            }

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
        file = fileChooser.showOpenDialog(elizaChatPlayer.getPrimaryStage());
        if (file != null) {
            String filename = file.getAbsolutePath();
            OpenChat();
            handleOpenedLabel();
            loadFile(filename);
        }
    }

    private void loadFile(String filename) {
        FileSystemAble fileSystem = new ElizaFileSystem(filename);
        elizaReader = new ElizaReader(filename, fileSystem);
        elizaReader.open();
        elizaReader.read();
        int a = 0;
        /*conversations = reader.getConversations();
        addFirstConversationIntoChatFrame(conversations);*/
    }

    public int getSpinnerCurrentValue() {
        return spinnerCurrentValue;
    }

    public void setPlayerApp(ElizaChatPlayer elizaChatPlayer) {
        this.elizaChatPlayer = elizaChatPlayer;
    }

    private void OpenChat() {
        chatStage = new Stage();
        chatStage.setTitle("chat");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ElizaChatPlayer.class.getResource("/de/dfki/eliza/chat/Chat.fxml"));
        try {
            chatRoot = loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        chatController = loader.getController();
        Scene scene = new Scene(chatRoot);
        chatStage.setScene(scene);
        chatStage.setX(600);
        chatStage.show();
    }
    private boolean isUser = false;

    private void handleChatMessages() {
        LinkedList<Conversation> conversations = elizaReader.getConversations();
        Iterator<Conversation> conversationIterator = conversations.iterator();
        Conversation conversation = conversationIterator.next();
        Iterator<Textable> messageIterator = null;
        if (conversation != null && conversation.getTotalMessages() > 0) {
            messageIterator = conversation.getMessages().iterator();
        }

        Iterator<Textable> finalMessageIterator = messageIterator;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Iterator<Textable> messagesIt = finalMessageIterator;
                while (isPlayButtonClicked) {

                    Path face;
                    chatScrollPane = chatController.getChatScrollPane();
                    chatGridPane = chatController.getChatGridPane();
                    chatScrollPane.vvalueProperty().bind(chatGridPane.heightProperty());

                    if (messagesIt != null) {
                        Textable m = messagesIt.next();
                        text = m.getText();
                        try {
                            isUser = ((Message) m).isIsUserMessage();
                        } catch (ClassCastException ex) {
                            text = "";
                        }
                        messageSender.send(text);
                        if (conversationIterator.hasNext() && !messagesIt.hasNext()) {
                            messagesIt = conversationIterator.next().getMessages().iterator();
                        }
                    }

                    if (!text.equalsIgnoreCase("")) {
                        messages = new Label(text);
                        messages.setFont(new Font("Arial", 30));
                        messages.setWrapText(true);
                        messages.setPadding(new Insets(5, 5, 5, 5));
                        messages.setMaxWidth(800);
                        messages.setVisible(false);

                        ColumnConstraints cc = new ColumnConstraints();
                        cc.setFillWidth(true);
                        cc.setHgrow(Priority.ALWAYS);
                        chatGridPane.getColumnConstraints().clear();
                        chatGridPane.getColumnConstraints().add(cc);
                        chatGridPane.setVgap(10);

                        HBox box = new HBox();

                        if (!isUser) {
                            createSystemMessageStyle(messages);
                            cc.setHalignment(HPos.LEFT);
                            box.setAlignment(Pos.CENTER_LEFT);
                            GridPane.setHalignment(box, HPos.LEFT);
                            face = createLeftFace(Color.rgb(255, 132, 202));
                            face.setVisible(false);
                            box.getChildren().addAll(face, messages);
                            createFadeEffect(fadeMessage, messages);
                            createFadeEffect(fadePath, face);
                            pt.getChildren().clear();
                            pt.getChildren().addAll(fadeMessage, fadePath);
                            Platform.runLater(()
                                    -> {
                                chatGridPane.add(box, 0, rowIndex);
                                messages.setVisible(true);
                                face.setVisible(true);
                                pt.play();
                            });
                            rowIndex++;
                            String emoImagePath = getClass().getClassLoader().getResource("smile.png").toExternalForm();
                            emoImage = new Image(emoImagePath);
                            ImageView emoImageView = chatController.getEmotionImageView();
                            emoImageView.setImage(emoImage);
                        } else {
                            createUserMessageStyle(messages);

                            box.setAlignment(Pos.CENTER_RIGHT);
                            GridPane.setHalignment(box, HPos.RIGHT);
                            cc.setHalignment(HPos.RIGHT);
                            face = creatRightFace(Color.rgb(222, 222, 222));
                            face.setVisible(false);
                            box.getChildren().addAll(messages, face);
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
                            rowIndex++;
                            String emoImagePath = getClass().getClassLoader().getResource("sad.png").toExternalForm();
                            emoImage = new Image(emoImagePath);
                            ImageView emoImageView = chatController.getEmotionImageView();
                            emoImageView.setImage(emoImage);
                        }
                    }

                    try {
                        Thread.sleep(spinnerCurrentValue * 1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
                    }
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

    private void handleOpenedLabel() {
        FadeTransition labelfade = new FadeTransition(Duration.millis(500));
        labelfade.setNode(openedLabel);
        labelfade.setFromValue(0.0);
        labelfade.setToValue(1.0);
        labelfade.setCycleCount(1);
        labelfade.setAutoReverse(false);
        openedLabel.setVisible(true);
        labelfade.play();
    }

    private void createSystemMessageStyle(Label message) {
        message.setStyle("-fx-background-color: #FF84CA; "
                + "-fx-border-color: #FF84CA;  "
                + "-fx-border-radius: 10 10 10 10;\n"
                + "-fx-background-radius: 10 10 10 10;");
    }

    private void createUserMessageStyle(Label message) {
        message.setStyle("-fx-background-color: #DEDEDE; "
                + "-fx-border-color: #DEDEDE;  "
                + "-fx-border-radius: 10 10 10 10;\n"
                + "-fx-background-radius: 10 10 10 10;");
    }

}
