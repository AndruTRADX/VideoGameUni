package application;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MemoryGame {

	// Botones del juego
    @FXML public Button btn1;
    @FXML public Button btn2;
    @FXML public Button btn3;
    @FXML public Button btn4;
    @FXML public Button btn5;
    @FXML public Button btn6;
    @FXML public Button btn7;
    @FXML public Button btn8;
    @FXML public Button btn9;

    // Listas para almacenar el patrón del juego y el patrón ingresado por el jugador
    public List<Integer> patron = new ArrayList<>();
    public List<Integer> patronPlayer = new ArrayList<>();

    // Generador de números aleatorios
    public Random random = new Random();
    
    // Variables de la escena y el escenario
    public Stage stage;
	public Scene scene;
    
	/**
     * Cambia al menú principal del juego.
     * @param event El evento de acción que desencadena este método.
     */
    public void switchToMainScene(ActionEvent event)
	{
		try {
			Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

    /**
     * Inicializa el controlador y asigna manejadores de eventos a los botones.
     */
    @FXML
    public void initialize() {
    	// Asigna manejadores de eventos a los botones
        btn1.setOnAction(e -> handleButtonPress(1));
        btn2.setOnAction(e -> handleButtonPress(2));
        btn3.setOnAction(e -> handleButtonPress(3));
        btn4.setOnAction(e -> handleButtonPress(4));
        btn5.setOnAction(e -> handleButtonPress(5));
        btn6.setOnAction(e -> handleButtonPress(6));
        btn7.setOnAction(e -> handleButtonPress(7));
        btn8.setOnAction(e -> handleButtonPress(8));
        btn9.setOnAction(e -> handleButtonPress(9));
    }

    /**
     * Inicia el juego, limpiando los patrones anteriores y comenzando un nuevo patrón.
     */
    @FXML
    public void startGame() {
        patron.clear();
        patronPlayer.clear();
        addRandomToPatron();
        displayPatronWithAnimation();
    }

    /**
     * Añade un número aleatorio al patrón del juego.
     */
    public void addRandomToPatron() {
        int nextNumber = random.nextInt(9) + 1;
        patron.add(nextNumber);
    }

    /**
     * Muestra el patrón del juego con una animación.
     */
    public void displayPatronWithAnimation() {
        Timeline timeline = new Timeline();
        for (int i = 0; i < patron.size(); i++) {
            int num = patron.get(i);
            Button button = getButtonByNumber(num);
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.seconds(i), new KeyValue(button.styleProperty(), "-fx-background-color: #f9fafb; -fx-border-color: #d1d5db; -fx-min-width: 100px; -fx-min-height: 100px;")),
                    new KeyFrame(Duration.seconds(i + 0.1), new KeyValue(button.styleProperty(), "-fx-background-color: #cffafe; -fx-border-color: #67e8f9; -fx-min-width: 100px; -fx-min-height: 100px;")),
                    new KeyFrame(Duration.seconds(i + 0.9), new KeyValue(button.styleProperty(), "-fx-background-color: #f9fafb; -fx-border-color: #d1d5db; -fx-min-width: 100px; -fx-min-height: 100px;"))
            );
        }
        disableButtons();
        timeline.setOnFinished(e -> enableButtons());
        timeline.play();
    }

    /**
     * Deshabilita todos los botones.
     */
    public void disableButtons() {
        btn1.setDisable(true);
        btn2.setDisable(true);
        btn3.setDisable(true);
        btn4.setDisable(true);
        btn5.setDisable(true);
        btn6.setDisable(true);
        btn7.setDisable(true);
        btn8.setDisable(true);
        btn9.setDisable(true);
    }

    /**
     * Habilita todos los botones.
     */
    public void enableButtons() {
        btn1.setDisable(false);
        btn2.setDisable(false);
        btn3.setDisable(false);
        btn4.setDisable(false);
        btn5.setDisable(false);
        btn6.setDisable(false);
        btn7.setDisable(false);
        btn8.setDisable(false);
        btn9.setDisable(false);
    }

    /**
     * Maneja la pulsación de un botón por parte del jugador.
     * @param num El número del botón que fue presionado.
     */
    public void handleButtonPress(int num) {
        patronPlayer.add(num);
        if (patronPlayer.size() == patron.size()) {
            checkPatron();
        } else if (!patronPlayer.equals(patron.subList(0, patronPlayer.size()))) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Incorrecto");
            alert.setHeaderText(null);
            alert.setContentText("¡Patrón incorrecto! Inténtalo de nuevo.");
            alert.showAndWait();
            patronPlayer.clear();
            enableButtons();
        }
    }

    /**
     * Verifica si el patrón ingresado por el jugador es correcto.
     */
    public void checkPatron() {
        if (!patron.equals(patronPlayer)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("¡Perdiste!");
            alert.setHeaderText(null);
            alert.setContentText("¡Has perdido! Inténtalo de nuevo.");
            alert.showAndWait();
        } else {
            patronPlayer.clear();
            addRandomToPatron();
            displayPatronWithAnimation();
        }
    }

    /**
     * Obtiene el botón correspondiente al número dado.
     * @param num El número del botón.
     * @return El botón correspondiente.
     */
    public Button getButtonByNumber(int num) {
        switch (num) {
            case 1: return btn1;
            case 2: return btn2;
            case 3: return btn3;
            case 4: return btn4;
            case 5: return btn5;
            case 6: return btn6;
            case 7: return btn7;
            case 8: return btn8;
            case 9: return btn9;
            default: return null;
        }
    }
}
