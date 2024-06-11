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

	// Opciones del tablero
    @FXML public Button btn1;
    @FXML public Button btn2;
    @FXML public Button btn3;
    @FXML public Button btn4;
    @FXML public Button btn5;
    @FXML public Button btn6;
    @FXML public Button btn7;
    @FXML public Button btn8;
    @FXML public Button btn9;
    @FXML public Button btn10;
    @FXML public Button btn11;
    @FXML public Button btn12;
    @FXML public Button btn13;
    @FXML public Button btn14;
    @FXML public Button btn15;
    @FXML public Button btn16;
    
    // Boton de funcionalidad
    @FXML public Button startButton;
    
    // Indicatores y estado
    @FXML public Label attemptsLabel;
    @FXML public Label timerLabel;
    
    // Contadores y temporizador
    public int attempts = 0;
    public long startTime;
    public Timer timer = null;
    
    // Variables de la escena y el escenario
    public Stage stage;
    public Scene scene;
    public Parent parent;
    
    // Botones seleccionados
    public Button firstButton = null;
    public Button secondButton = null;
    
    // Mapas de botones y emojis
    public Map<Button, String> buttonEmojiMap = new HashMap<>();
    public String[] emojis = {"UwU", "<3", ">_<", "＼(ﾟｰﾟ＼)", "(^人^)", "T_T", "¯\\_(ツ)_/¯", "( ͡° ͜ʖ ͡°)"};
    
    /**
     * Cambia al menú principal del juego.
     * @param event El evento de acción que desencadena este método.
     */
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
    
    /**
     * Inicializa el controlador y configura el tablero del juego.
     */
    @FXML
    public void initialize() {
        disableBoard();
        attemptsLabel.setText("Attempts: 0");
        timerLabel.setText("Time: 0.00s");
    }
    
    /**
     * Inicializa el tablero del juego barajando y asignando emojis a los botones.
     */
    public void initializeBoard() {
        List<String> emojiList = new ArrayList<>();
        
        // Inicializa tablero
        for (String emoji : emojis) {
            emojiList.add(emoji);
            emojiList.add(emoji);
        }
        
        
        // Ordena al azar los elementos del tablero
        Collections.shuffle(emojiList);
        
        
        // Asigna los botones y su valor correspondiente
        Button[] buttons = {btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16};
        for (int i = 0; i < buttons.length; i++) {
            buttonEmojiMap.put(buttons[i], emojiList.get(i));
            buttons[i].setText("");
            buttons[i].setDisable(false);
        }
    }
    
    
    /**
     * Maneja la pulsación de un botón por parte del jugador.
     * @param event El evento de acción que desencadena este método.
     */
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
            
            // Al selecionar dos parejas, añade intento
            attempts++;
            attemptsLabel.setText("Attempts: " + attempts);
            checkForMatch();
        }
    }
    
    /**
     * Revela el emoji del botón seleccionado.
     * @param button El botón a revelar.
     */
    public void revealButton(Button button) {
        button.setText(buttonEmojiMap.get(button));
        button.setDisable(true);
    }
    
    /**
     * Verifica si los botones seleccionados forman un par.
     */
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
                	// Espera un segundo antes de ocultar el valor de los botones
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
    
    /**
     * Verifica si el jugador ha encontrado todas las parejas.
     * @return true si el jugador ha ganado, false de lo contrario.
     */
    public boolean checkIfWon() {
        for (Button button : buttonEmojiMap.keySet()) {
            if (button.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Finaliza el juego mostrando un mensaje de victoria y el tiempo total de intentos y tiempo.
     */
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
    
    /**
     * Inicia un nuevo juego, reiniciando los contadores y el tablero.
     */
    @FXML
    public void startGame() {
        attempts = 0;
        attemptsLabel.setText("Attempts: 0");
        startTime = System.currentTimeMillis();
        startButton.setText("Start");
        initializeBoard();
        startTimer();
    }
    
    /**
     * Deshabilita todos los botones del tablero.
     */
    public void disableBoard() {
        Button[] buttons = {btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16};
        for (Button button : buttons) {
            button.setDisable(true);
        }
    }
    
    /**
     * Inicia el temporizador del juego.
     */
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
}
