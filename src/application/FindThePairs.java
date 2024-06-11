package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class FindThePairs {

    @FXML private Button btn1;
    @FXML private Button btn2;
    @FXML private Button btn3;
    @FXML private Button btn4;
    @FXML private Button btn5;
    @FXML private Button btn6;
    @FXML private Button btn7;
    @FXML private Button btn8;
    @FXML private Button btn9;
    @FXML private Button btn10;
    @FXML private Button btn11;
    @FXML private Button btn12;
    @FXML private Button btn13;
    @FXML private Button btn14;
    @FXML private Button btn15;
    @FXML private Button btn16;
    @FXML private Button startButton;
    @FXML private Label attemptsLabel;
    @FXML private Label timerLabel;
    
    private int attempts = 0;
    private long startTime;
    private Timer timer;
    
    public Stage stage;
    public Scene scene;
    public Parent parent;
    
    private Button firstButton = null;
    private Button secondButton = null;
    private Map<Button, String> buttonEmojiMap = new HashMap<>();
    private String[] emojis = {"UwU", "<3", ">_<", "X_X", "^_~", "T_T", "¯\\_(ツ)_/¯", "( ͡° ͜ʖ ͡°)"};
    
    @FXML
    public void initialize() {
        disableBoard();
        attemptsLabel.setText("Attempts: 0");
        timerLabel.setText("Time: 0.00s");
    }
    
    public void initializeBoard() {
        List<String> emojiList = new ArrayList<>();
        for (String emoji : emojis) {
            emojiList.add(emoji);
            emojiList.add(emoji);
        }
        
        
        Collections.shuffle(emojiList);
        
        Button[] buttons = {btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16};
        for (int i = 0; i < buttons.length; i++) {
            buttonEmojiMap.put(buttons[i], emojiList.get(i));
            buttons[i].setText("");
            buttons[i].setDisable(false);
        }
    }
    
    @FXML
    public void handleButtonClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        
        if (firstButton != null && secondButton != null) {
            return;
        }
        
        revealButton(clickedButton);
        
        if (firstButton == null) {
            firstButton = clickedButton;
        } else if (secondButton == null) {
            secondButton = clickedButton;
            attempts++;
            attemptsLabel.setText("Attempts: " + attempts);
            checkForMatch();
        }
    }
    
    public void revealButton(Button button) {
        button.setText(buttonEmojiMap.get(button));
        button.setDisable(true);
    }
    
    public void checkForMatch() {
        if (buttonEmojiMap.get(firstButton).equals(buttonEmojiMap.get(secondButton))) {
            firstButton = null;
            secondButton = null;
            if (checkIfWon()) {
                endGame();
            }
        } else {
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    firstButton.setText("");
                    firstButton.setDisable(false);
                    secondButton.setText("");
                    secondButton.setDisable(false);
                    firstButton = null;
                    secondButton = null;
                });
            }).start();
        }
    }
    
    public boolean checkIfWon() {
        for (Button button : buttonEmojiMap.keySet()) {
            if (button.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }
    
    public void endGame() {
        timer.cancel();
        long endTime = System.currentTimeMillis();
        double totalTime = (endTime - startTime) / 1000.0;
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("You Won!!!");
        alert.setHeaderText(null);
        alert.setContentText("Total Attempts: " + attempts + "\nTime: " + totalTime + " seconds");
        
        ButtonType startAgain = new ButtonType("Start Again");
        ButtonType cancel = new ButtonType("Cancel");
        alert.getButtonTypes().setAll(startAgain, cancel);
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == startAgain) {
            startGame();
        } else {
            disableBoard();
            startButton.setText("Start");
        }
    }
    
    @FXML
    public void startGame() {
        attempts = 0;
        attemptsLabel.setText("Attempts: 0");
        startTime = System.currentTimeMillis();
        startButton.setText("Start");
        initializeBoard();
        startTimer();
    }
    
    public void disableBoard() {
        Button[] buttons = {btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16};
        for (Button button : buttons) {
            button.setDisable(true);
        }
    }
    
    public void startTimer() {
        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                long elapsed = System.currentTimeMillis() - startTime;
                double elapsedSeconds = elapsed / 1000.0;
                Platform.runLater(() -> timerLabel.setText(String.format("Time: %.2f s", elapsedSeconds)));
            }
        }, 0, 100);
    }
    
    @FXML
    public void switchToMainScene(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
