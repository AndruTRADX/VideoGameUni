package application;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MathGameTest {

    MathGame mathGame;

    @BeforeEach
    void setUp() {
        mathGame = new MathGame();
        mathGame.operations = new ArrayList<>();
        mathGame.results = new ArrayList<>();
    }

    @Test
    void testGenerateOperations() {
        mathGame.generateOperations();
        assertEquals(10, mathGame.operations.size(), "Debe generar 10 operaciones");
        assertEquals(10, mathGame.results.size(), "Debe generar 10 resultados");
    }

    @Test
    void testGenerateNextOperation() {
        mathGame.operations.add("1 + 1");
        mathGame.results.add(2);
        mathGame.attempts = 0;
        mathGame.generateNextOperation();
        assertEquals("1 + 1", mathGame.operationText.getText(), "Debe mostrar la primera operación");
    }

    @Test
    void testResetGame() {
        mathGame.attempts = 5;
        mathGame.correctText.setText("Wrong");
        mathGame.timeText.setText("Time: 10.0 seconds");
        mathGame.startButton.setText("Start Over");
        mathGame.results.add(1);

        mathGame.resetGame();

        assertFalse(mathGame.gameStarted, "El juego no debe estar iniciado");
        assertEquals(0, mathGame.attempts, "El número de intentos debe ser 0");
        assertEquals("", mathGame.correctText.getText(), "El texto de corrección debe estar vacío");
        assertEquals("Time: 0.0 seconds", mathGame.timeText.getText(), "El tiempo debe resetearse a 0.0 segundos");
        assertEquals("Start", mathGame.startButton.getText(), "El botón debe mostrar 'Start'");
        assertTrue(mathGame.results.isEmpty(), "La lista de resultados debe estar vacía");
    }
}
