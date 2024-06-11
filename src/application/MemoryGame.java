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

    @FXML private Button btn1;
    @FXML private Button btn2;
    @FXML private Button btn3;
    @FXML private Button btn4;
    @FXML private Button btn5;
    @FXML private Button btn6;
    @FXML private Button btn7;
    @FXML private Button btn8;
    @FXML private Button btn9;

    public List<Integer> patron = new ArrayList<>();
    public List<Integer> patronPlayer = new ArrayList<>();

    private Random random = new Random();
    
    public Stage stage;
	public Scene scene;
    
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

    @FXML
    private void initialize() {
        // Add event listeners to buttons
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

    @FXML
    private void startGame() {
        patron.clear();
        patronPlayer.clear();
        addRandomToPatron();
        displayPatronWithAnimation();
    }

    private void addRandomToPatron() {
        int nextNumber = random.nextInt(9) + 1;
        patron.add(nextNumber);
    }

    private void displayPatronWithAnimation() {
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

    private void disableButtons() {
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

    private void enableButtons() {
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

    private void handleButtonPress(int num) {
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

    private void checkPatron() {
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

    private Button getButtonByNumber(int num) {
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
