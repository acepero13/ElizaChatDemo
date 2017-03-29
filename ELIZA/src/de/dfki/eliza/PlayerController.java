/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dfki.eliza;

import de.dfki.connection.Sender;
import de.dfki.eliza.chat.AgentChatController;
import de.dfki.eliza.chat.ChatController;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dfki.eliza.chat.ChatManager;
import de.dfki.eliza.files.exceptions.NoValidConversation;
import de.dfki.eliza.files.filestystem.FileSystemReadable;
import de.dfki.eliza.files.filestystem.eliza.ElizaFileSystem;
import de.dfki.eliza.files.models.Conversation;
import de.dfki.eliza.files.models.Textable;
import de.dfki.eliza.files.readers.eliza.ElizaReader;
import de.dfki.eliza.renderer.DummyRender;
import de.dfki.eliza.renderer.Renderable;
import de.dfki.eliza.renders.SystemRender;
import de.dfki.eliza.renders.UserRender;
import de.dfki.eliza.saSimulate.SA_Simulate;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author EmpaT
 */
public class PlayerController implements Initializable
{

    @FXML
    private Button openFileButton;
    @FXML
    private Button playButton;
    @FXML
    private Button liveChatButton;
    @FXML
    private Spinner<Integer> playerSpinner;
    @FXML
    private Label openedLabel;

    private ElizaChatPlayer elizaChatPlayer;
    private ElizaReader elizaReader;
    private SA_Simulate sa_Simulate;
    private Boolean buttonLimit = true;

    private final int spinnerInitalValue = 1;
    private int spinnerCurrentValue = spinnerInitalValue;

    private boolean isPlayButtonClicked = false;

    private String imagePath;
    private Image image;
    private Image emoImage;
    private ImageView playImageView;

    private Stage chatStage;
    private AnchorPane chatRoot;
    private ChatController chatController;
    
    private Stage liveChatStage;
    private AnchorPane agentChatRoot;
    private AgentChatController agentChatController;

    private ScrollPane chatScrollPane;
    private GridPane chatGridPane;
    Label messages;
    File file;

    Sender messageSender;
    String text = "";
    private boolean isUser = false;
    private ChatManager chatManager;
    private int rowIndex = 0;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        messageSender = new Sender(9876);
        imagePath = getClass().getClassLoader().getResource("play.png").toExternalForm();
        image = new Image(imagePath);
        playImageView = new ImageView(image);
        playButton.setGraphic(playImageView);

        playButton.setOnAction((event) ->
        {
            openedLabel.setVisible(false);
            if (file != null)
            {
                handlePlayButton();
            }

        });
        
        liveChatButton.setOnAction((event) ->
        {
            if (buttonLimit){
            sa_Simulate = new SA_Simulate();
            sa_Simulate.initial();
            handleLiveChatButton();
            buttonLimit = false;
            }
        });

        openFileButton.setOnAction((event) ->
        {
            handleOpen();
        });

        SpinnerValueFactory<Integer> valueFactory
                = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, spinnerInitalValue);
        playerSpinner.setValueFactory(valueFactory);

        playerSpinner.setOnMouseClicked((event) ->
        {
            spinnerCurrentValue = playerSpinner.getValue();
        });

    }

    private void handlePlayButton()
    {
        if (!isPlayButtonClicked)
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

        handleChatMessages();
    }

    private void handleOpen()
    {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open file dialog
        file = fileChooser.showOpenDialog(elizaChatPlayer.getPrimaryStage());
        if (file != null)
        {
            String filename = file.getAbsolutePath();
            OpenChat();
            handleOpenedLabel();
            loadFile(filename);
        }
    }

    private void loadFile(String filename)
    {

        chatScrollPane = chatController.getChatScrollPane();
        chatGridPane = chatController.getChatGridPane();
        chatScrollPane.vvalueProperty().bind(chatGridPane.heightProperty());

        FileSystemReadable fileSystem = new ElizaFileSystem(filename);
        Renderable systemRender = new SystemRender(chatGridPane);
        Renderable userRender = new UserRender(chatGridPane);
        Renderable infoRender = new DummyRender();
        elizaReader = new ElizaReader(fileSystem, infoRender, userRender, systemRender);
        elizaReader.open();
        elizaReader.read();
        chatManager = new ChatManager(elizaReader.getConversations());
    }

    public int getSpinnerCurrentValue()
    {
        return spinnerCurrentValue;
    }

    public void setPlayerApp(ElizaChatPlayer elizaChatPlayer)
    {
        this.elizaChatPlayer = elizaChatPlayer;
    }
    
    private void OpenChat()
    {
        chatStage = new Stage();
        chatStage.setTitle("chat");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ElizaChatPlayer.class.getResource("/de/dfki/eliza/chat/Chat.fxml"));
        try
        {
            chatRoot = loader.load();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        chatController = loader.getController();
        Scene scene = new Scene(chatRoot);
        chatStage.setScene(scene);
        chatStage.setX(600);
        chatStage.show();
    }

    private void handleLiveChatButton()
    {
        liveChatStage = new Stage();
        liveChatStage.setTitle("chat");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ElizaChatPlayer.class.getResource("/de/dfki/eliza/chat/AgentLiveChat.fxml"));
        try
        {
            agentChatRoot = loader.load();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        agentChatController = loader.getController();
        Scene scene = new Scene(agentChatRoot);
        liveChatStage.setScene(scene);
        liveChatStage.setX(600);
        liveChatStage.show();
    }

    private void handleChatMessages()
    {
        ChatRunner chatRunner = new ChatRunner();
        Thread t = new Thread(chatRunner);
        t.start();
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


    private class ChatRunner implements Runnable{
        private Conversation conversation = null;
        private Iterator<Textable> messages = null;

        @Override
        public void run() {

            while (isPlayButtonClicked) {
                getConversation();
                processConversations(rowIndex);
                rowIndex++;
            }
        }

        void processConversations(int rowIndex) {
            try {
                Textable message =  messages.next();
                message.render(rowIndex, message);
                Thread.sleep(spinnerCurrentValue * 1000);
            }
            catch (InterruptedException ex) {
                Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private void getConversation()  {
            if(canMoveToNextConversation()) {
                try {
                    conversation = chatManager.getNextConversation();
                    messages = conversation.getMessages().iterator();
                } catch (NoValidConversation noValidConversation) {
                    noValidConversation.printStackTrace();
                }
            }
        }

        private boolean canMoveToNextConversation() {
            return (conversation == null || !messages.hasNext()) && chatManager.hastNext();
        }
    }


}
