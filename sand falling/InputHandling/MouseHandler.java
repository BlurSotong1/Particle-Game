package InputHandling;

import Particles.SandParticle;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class MouseHandler extends MouseAdapter {

    private Map<Integer, Timer> timerMap = new HashMap<>();
    private AtomicInteger timerIdCounter = new AtomicInteger(0);
    private static int[] currentCoordinates = new int[2];
    private static int[] oldCoordinates = new int[2];
    private boolean isMousePressed = false;
    private SimulatorLogic simulatorLogic;

    private static int radius = 20;

    public MouseHandler(SimulatorLogic simulatorLogic) {
        this.simulatorLogic = simulatorLogic;

    }

    @Override
    public void mousePressed(MouseEvent e) {
        isMousePressed = true;
        currentCoordinates[0] = e.getX();
        currentCoordinates[1] = e.getY();
        System.out.println("pressed");

        Timer timer = new Timer(1, evt -> {
            if (isMousePressed) {
                // Update current coordinates within the timer action
                Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
                SwingUtilities.convertPointFromScreen(mouseLocation, e.getComponent());
                currentCoordinates[0] = mouseLocation.x;
                currentCoordinates[1] = mouseLocation.y;
                simulatorLogic.addParticles(currentCoordinates[0], currentCoordinates[1], new SandParticle(),radius);
            }
        });

        int timerId = timerIdCounter.incrementAndGet();
        timer.start();
        timerMap.put(timerId, timer);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isMousePressed = false;
        System.out.println("Mouse released");
        Timer timer = timerMap.remove(e.getID());
        if (timer != null && timer.isRunning()) {
            System.out.println("Timer stopped");
            timer.stop();
        }
    }

//    @Override
//    public void mouseMoved(MouseEvent e) {
//        currentCoordinates[0] = e.getX();
//        currentCoordinates[1] = e.getY();
//    }


}
