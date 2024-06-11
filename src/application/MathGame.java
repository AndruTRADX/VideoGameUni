package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MathGame {

    @FXML
    private Text operationText;
    @FXML
    private TextField resultTextField;
    @FXML
    private Text correctText;
    @FXML
    private Text timeText;
    @FXML
    private Button sendButton;
    @FXML
    private Button backButton;
    @FXML
    private Button startButton;

    private int attempts = 0;
    
    private long startTime;
    
    private boolean gameStarted = false;

    private List<String> operations = new ArrayList<>();
    private List<Integer> results = new ArrayList<>();
    private Random random = new Random();
    private Thread timerThread;

    public Stage stage;
    public Scene scene;

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

    public void initialize() {
        disableControls();
    }

    @FXML
    private void startGame(ActionEvent event) {
    	resetGame();
        gameStarted = true;
        startButton.setText("Start Over");
        generateOperations();
        startTime = System.currentTimeMillis();
        enableControls();
        startTimer();
    }

    @FXML
    private void sendResult(ActionEvent event) {
    	
        String resultText = resultTextField.getText();
        try {
            int userResult = Integer.parseInt(resultText);
            int correctResult;
            if (attempts < results.size()) {
                correctResult = results.get(attempts);
            } else {
                return;
            }
            if (userResult == correctResult) {
                attempts++;
                correctText.setText("Correct!");
                if (attempts == 10) {
                    endGame();
                } else {
                    generateNextOperation();
                }
            } else {
                correctText.setText("Wrong");
            }
        } catch (NumberFormatException e) {
            correctText.setText("Please enter a valid number");
        }
    }


    private void generateOperations() {
        operations.clear();
        results.clear();
        for (int i = 0; i < 10; i++) {
            int num1 = random.nextInt(10) + 1;
            int num2 = random.nextInt(10) + 1;
            int result;
            String operation;
            switch (random.nextInt(4)) {
                case 0:
                    result = num1 + num2;
                    operation = num1 + " + " + num2;
                    break;
                case 1:
                    result = num1 - num2;
                    operation = num1 + " - " + num2;
                    break;
                case 2:
                    result = num1 * num2;
                    operation = num1 + " * " + num2;
                    break;
                case 3:
                    result = num1 / num2;
                    operation = num1 + " ÷ " + num2;
                    break;
                default:
                    result = 0;
                    operation = "";
                    break;
            }
            operations.add(operation);
            results.add(result);
        }
        generateNextOperation();
    }

    private void generateNextOperation() {
        if (attempts < results.size()) {
            operationText.setText(operations.get(attempts));
            resultTextField.clear();
            correctText.setText("");
        }
    }

    private void resetGame() {
        gameStarted = false;
        attempts = 0;
        correctText.setText("");
        timeText.setText("Time: 0.0 seconds");
        startButton.setText("Start");
        disableControls();
        results.clear();
        if (timerThread != null && timerThread.isAlive()) {
            timerThread.interrupt();
        }
    }

    private void endGame() {
        long endTime = System.currentTimeMillis();
        double totalTime = (endTime - startTime) / 1000.0;

        timeText.setText("Time: " + totalTime + " seconds");
        gameStarted = false;
        
        startButton.setText("Start");

        if (timerThread != null && timerThread.isAlive()) {
            timerThread.interrupt();
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Congratulations!");
        alert.setHeaderText(null);
        alert.setContentText("Time: " + totalTime + " seconds");

        ButtonType startAgainButton = new ButtonType("Start Again");
        ButtonType cancelButton = new ButtonType("Cancel");

        alert.getButtonTypes().setAll(startAgainButton, cancelButton);
        alert.showAndWait().ifPresent(response -> {
            if (response == startAgainButton) {
                resetGame();
                startGame(new ActionEvent());
            }
        });
    }

    private void enableControls() {
        operationText.setVisible(true);
        resultTextField.setVisible(true);
        sendButton.setDisable(false);
        backButton.setDisable(false);
    }

    private void disableControls() {
        operationText.setVisible(false);
        resultTextField.setVisible(false);
        sendButton.setDisable(true);
    }

    private void startTimer() {
        timerThread = new Thread(() -> {
            while (gameStarted) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    break;
                }
                long currentTime = System.currentTimeMillis();
                double elapsedTime = (currentTime - startTime) / 1000.0;
                timeText.setText("Time: " + elapsedTime + " seconds");
            }
        });
        timerThread.setDaemon(true);
        timerThread.start();
    }
}