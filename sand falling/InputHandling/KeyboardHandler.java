package InputHandling;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardHandler implements KeyListener {

    private SimulatorLogic simulatorLogic;

    public KeyboardHandler(SimulatorLogic simulatorLogic) {
        this.simulatorLogic = simulatorLogic;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char keyChar = e.getKeyChar();

        if (keyChar == ' ') {
            simulatorLogic.clearScreen();
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
