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

	// Elementos de la interfaz de usuario
    @FXML public Text operationText;
    @FXML public TextField resultTextField;
    @FXML public Text correctText;
    @FXML public Text timeText;
    @FXML public Button sendButton;
    @FXML public Button backButton;
    @FXML public Button startButton;

    // Variables de juego
    public int attempts = 0;
    public long startTime;
    public boolean gameStarted = false;
    public List<String> operations = new ArrayList<>();
    public List<Integer> results = new ArrayList<>();
    public Random random = new Random();
    public Thread timerThread;

 // Escenario y escena
    public Stage stage;
    public Scene scene;

    /**
     * Cambia al menú principal del juego.
     * @param event El evento de acción que desencadena este método.
     */
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

    /**
    * Inicializa el controlador del juego.
    */
    public void initialize() {
        disableControls();
    }

    /**
     * Inicia el juego.
     * @param event El evento de acción que desencadena este método.
     */
    @FXML
    public void startGame(ActionEvent event) {
    	resetGame();
        gameStarted = true;
        startButton.setText("Start Over");
        generateOperations();
        startTime = System.currentTimeMillis();
        enableControls();
        startTimer();
    }

    /**
     * Envía el resultado introducido por el jugador.
     * @param event El evento de acción que desencadena este método.
     */
    @FXML
    public void sendResult(ActionEvent event) {
    	
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

    /**
     * Genera las operaciones matemáticas aleatorias para el juego.
     */
    public void generateOperations() {
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

    /**
     * Genera la operación matemática siguiente
     */
    public void generateNextOperation() {
        if (attempts < results.size()) {
            operationText.setText(operations.get(attempts));
            resultTextField.clear();
            correctText.setText("");
        }
    }

    /**
     * Reinicia todos los parámetros del juego
     */
    public void resetGame() {
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

    /**
     * Finaliza el juego y muestra los resultados en un Alert, con opción de jugar de nuevo.
     */
    public void endGame() {
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

    /**
     * Habilita los controles del juego.
     */
    public void enableControls() {
        operationText.setVisible(true);
        resultTextField.setVisible(true);
        sendButton.setDisable(false);
        backButton.setDisable(false);
    }

    /**
     * Deshabilita los controles del juego.
     */
    public void disableControls() {
        operationText.setVisible(false);
        resultTextField.setVisible(false);
        sendButton.setDisable(true);
    }

    /**
     * Inicia el temporizador del juego.
     */
    public void startTimer() {
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