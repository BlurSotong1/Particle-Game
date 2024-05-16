package InputHandling;

import LogicHandling.SimulatorLogic;
import Particles.SandParticle;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class MouseHandler extends MouseAdapter {

    private Timer activeTimer = null;
    private static final int[] currentCoordinates = new int[2];
    private boolean isMousePressed = false;
    private final SimulatorLogic simulatorLogic;

    private static final int radius = 5;

    public MouseHandler(SimulatorLogic simulatorLogic) {
        this.simulatorLogic = simulatorLogic;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!isMousePressed) {
            isMousePressed = true;
            currentCoordinates[0] = e.getX();
            currentCoordinates[1] = e.getY();

            activeTimer = new Timer(25, evt -> {
                if (isMousePressed) {
                    // Update current coordinates within the timer action
                    Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
                    SwingUtilities.convertPointFromScreen(mouseLocation, e.getComponent());
                    currentCoordinates[0] = mouseLocation.x;
                    currentCoordinates[1] = mouseLocation.y;
                    simulatorLogic.drawCircularParticles(currentCoordinates[0], currentCoordinates[1], new SandParticle(), radius);
                }
            });

            activeTimer.start();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isMousePressed = false;

        if (activeTimer != null && activeTimer.isRunning()) {
            activeTimer.stop();
            activeTimer = null;
        }
    }
}
