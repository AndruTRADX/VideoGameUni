package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

class FindThePairsTest {

    FindThePairs findThePairs;

    @BeforeEach
    void setUp() {
        findThePairs = new FindThePairs();
        // Inicializar botones
        findThePairs.btn1 = new Button();
        findThePairs.btn2 = new Button();
        findThePairs.btn3 = new Button();
        findThePairs.btn4 = new Button();
        findThePairs.btn5 = new Button();
        findThePairs.btn6 = new Button();
        findThePairs.btn7 = new Button();
        findThePairs.btn8 = new Button();
        findThePairs.btn9 = new Button();
        findThePairs.btn10 = new Button();
        findThePairs.btn11 = new Button();
        findThePairs.btn12 = new Button();
        findThePairs.btn13 = new Button();
        findThePairs.btn14 = new Button();
        findThePairs.btn15 = new Button();
        findThePairs.btn16 = new Button();
        // Inicializar labels
        findThePairs.attemptsLabel = new Label();
        findThePairs.timerLabel = new Label();
    }

    @Test
    void testInitialize() {
        findThePairs.initialize();
        assertEquals("Attempts: 0", findThePairs.attemptsLabel.getText(), "El contador de intentos debe estar en 0");
        assertEquals("Time: 0.00s", findThePairs.timerLabel.getText(), "El temporizador debe estar en 0.00s");
    }

    @Test
    void testInitializeBoard() {
        findThePairs.initializeBoard();
        // Verificar que todos los botones tengan emojis asignados
        for (Button button : findThePairs.buttonEmojiMap.keySet()) {
            assertNotNull(findThePairs.buttonEmojiMap.get(button), "Cada botón debe tener asignado un emoji");
        }
    }

    @Test
    void testHandleButtonClick() {
        findThePairs.initializeBoard();
        findThePairs.handleButtonClick(new ActionEvent(findThePairs.btn1, null));
        assertEquals(1, findThePairs.attempts, "Debe incrementar el contador de intentos");
    }

    @Test
    void testRevealButton() {
        findThePairs.initializeBoard();
        Button btn = findThePairs.btn1;
        findThePairs.revealButton(btn);
        assertTrue(btn.isDisable(), "El botón debe estar deshabilitado después de revelarse");
        assertNotNull(btn.getText(), "El botón debe tener un emoji asignado");
    }

    @Test
    void testCheckForMatch() {
        findThePairs.initializeBoard();
        findThePairs.firstButton = findThePairs.btn1;
        findThePairs.secondButton = findThePairs.btn2;
        findThePairs.buttonEmojiMap.put(findThePairs.btn1, "UwU");
        findThePairs.buttonEmojiMap.put(findThePairs.btn2, "UwU");
        findThePairs.checkForMatch();
        assertTrue(findThePairs.btn1.isDisable() && findThePairs.btn2.isDisable(), "Los botones deben permanecer deshabilitados después de una coincidencia");
        assertNull(findThePairs.firstButton, "El primer botón debe ser nulo después de una coincidencia");
        assertNull(findThePairs.secondButton, "El segundo botón debe ser nulo después de una coincidencia");
    }

    @Test
    void testCheckIfWon() {
        findThePairs.initializeBoard();
        assertFalse(findThePairs.checkIfWon(), "El juego no debe estar ganado al inicio");
    }

    @Test
    void testEndGame() {
        findThePairs.initializeBoard();
        findThePairs.startGame();

        // Simular finalización del juego
        findThePairs.firstButton = findThePairs.btn1;
        findThePairs.secondButton = findThePairs.btn2;
        findThePairs.buttonEmojiMap.put(findThePairs.btn1, "UwU");
        findThePairs.buttonEmojiMap.put(findThePairs.btn2, "UwU");
        findThePairs.checkForMatch();
        findThePairs.endGame();
        assertEquals("Start", findThePairs.startButton.getText(), "El botón de inicio debe cambiar a 'Start'");
    }

    @Test
    void testStartGame() {
        findThePairs.startGame();
        assertNotNull(findThePairs.timer, "Debe haber un temporizador iniciado después de comenzar el juego");
        assertTrue(findThePairs.attemptsLabel.getText().contains("Attempts: 0"), "El contador de intentos debe reiniciarse");
        assertTrue(findThePairs.timerLabel.getText().contains("Time: 0.00s"), "El temporizador debe reiniciarse");
    }

    @Test
    void testDisableBoard() {
        findThePairs.disableBoard();
        assertTrue(findThePairs.btn1.isDisable(), "Los botones deben estar deshabilitados");
        assertTrue(findThePairs.btn16.isDisable(), "Los botones deben estar deshabilitados");
    }

    @Test
    void testStartTimer() {
        findThePairs.startTimer();
        assertNotNull(findThePairs.timer, "Debe haber un temporizador iniciado");
    }
}
