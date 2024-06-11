package application;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.control.Button;

class MemoryGameTest {

    MemoryGame memoryGame;

    @BeforeEach
    void setUp() {
        memoryGame = new MemoryGame();
        memoryGame.patron = new ArrayList<>();
        memoryGame.patronPlayer = new ArrayList<>();

        // Inicializar botones
        memoryGame.btn1 = new Button();
        memoryGame.btn2 = new Button();
        memoryGame.btn3 = new Button();
        memoryGame.btn4 = new Button();
        memoryGame.btn5 = new Button();
        memoryGame.btn6 = new Button();
        memoryGame.btn7 = new Button();
        memoryGame.btn8 = new Button();
        memoryGame.btn9 = new Button();
    }

    @Test
    void testAddRandomToPatron() {
        memoryGame.addRandomToPatron();
        assertEquals(1, memoryGame.patron.size(), "Debe añadir un elemento al patrón");
    }

    @Test
    void testDisableButtons() {
        memoryGame.disableButtons();
        assertTrue(memoryGame.btn1.isDisable(), "El botón 1 debe estar deshabilitado");
        assertTrue(memoryGame.btn2.isDisable(), "El botón 2 debe estar deshabilitado");
        assertTrue(memoryGame.btn3.isDisable(), "El botón 3 debe estar deshabilitado");
        assertTrue(memoryGame.btn4.isDisable(), "El botón 4 debe estar deshabilitado");
        assertTrue(memoryGame.btn5.isDisable(), "El botón 5 debe estar deshabilitado");
        assertTrue(memoryGame.btn6.isDisable(), "El botón 6 debe estar deshabilitado");
        assertTrue(memoryGame.btn7.isDisable(), "El botón 7 debe estar deshabilitado");
        assertTrue(memoryGame.btn8.isDisable(), "El botón 8 debe estar deshabilitado");
        assertTrue(memoryGame.btn9.isDisable(), "El botón 9 debe estar deshabilitado");
    }

    @Test
    void testEnableButtons() {
        memoryGame.enableButtons();
        assertFalse(memoryGame.btn1.isDisable(), "El botón 1 debe estar habilitado");
        assertFalse(memoryGame.btn2.isDisable(), "El botón 2 debe estar habilitado");
        assertFalse(memoryGame.btn3.isDisable(), "El botón 3 debe estar habilitado");
        assertFalse(memoryGame.btn4.isDisable(), "El botón 4 debe estar habilitado");
        assertFalse(memoryGame.btn5.isDisable(), "El botón 5 debe estar habilitado");
        assertFalse(memoryGame.btn6.isDisable(), "El botón 6 debe estar habilitado");
        assertFalse(memoryGame.btn7.isDisable(), "El botón 7 debe estar habilitado");
        assertFalse(memoryGame.btn8.isDisable(), "El botón 8 debe estar habilitado");
        assertFalse(memoryGame.btn9.isDisable(), "El botón 9 debe estar habilitado");
    }

    @Test
    void testHandleButtonPress() {
        memoryGame.patron.add(1);
        memoryGame.patron.add(2);
        memoryGame.patron.add(3);

        memoryGame.handleButtonPress(1);
        assertEquals(List.of(1), memoryGame.patronPlayer, "El patrón del jugador debe contener 1");

        memoryGame.handleButtonPress(2);
        assertEquals(List.of(1, 2), memoryGame.patronPlayer, "El patrón del jugador debe contener 1 y 2");

        memoryGame.handleButtonPress(3);
        assertEquals(List.of(1, 2, 3), memoryGame.patronPlayer, "El patrón del jugador debe contener 1, 2 y 3");
    }

    @Test
    void testCheckPatron() {
        memoryGame.patron.add(1);
        memoryGame.patron.add(2);
        memoryGame.patron.add(3);

        memoryGame.patronPlayer.add(1);
        memoryGame.patronPlayer.add(2);
        memoryGame.patronPlayer.add(3);

        memoryGame.checkPatron();
        assertEquals(4, memoryGame.patron.size(), "El patrón debe tener un nuevo elemento añadido");
        assertTrue(memoryGame.patronPlayer.isEmpty(), "El patrón del jugador debe estar vacío después de una verificación exitosa");
    }

    @Test
    void testGetButtonByNumber() {
        assertEquals(memoryGame.btn1, memoryGame.getButtonByNumber(1), "Debe devolver el botón 1");
        assertEquals(memoryGame.btn2, memoryGame.getButtonByNumber(2), "Debe devolver el botón 2");
        assertEquals(memoryGame.btn3, memoryGame.getButtonByNumber(3), "Debe devolver el botón 3");
        assertEquals(memoryGame.btn4, memoryGame.getButtonByNumber(4), "Debe devolver el botón 4");
        assertEquals(memoryGame.btn5, memoryGame.getButtonByNumber(5), "Debe devolver el botón 5");
        assertEquals(memoryGame.btn6, memoryGame.getButtonByNumber(6), "Debe devolver el botón 6");
        assertEquals(memoryGame.btn7, memoryGame.getButtonByNumber(7), "Debe devolver el botón 7");
        assertEquals(memoryGame.btn8, memoryGame.getButtonByNumber(8), "Debe devolver el botón 8");
        assertEquals(memoryGame.btn9, memoryGame.getButtonByNumber(9), "Debe devolver el botón 9");
    }
}
