package LogicHandling;

import InputHandling.KeyboardHandler;
import InputHandling.MouseHandler;
import Particles.Particle;

import javax.swing.*;
import java.awt.*;

public class SimulatorScreen extends JFrame {
    /**
     * width of the window im creating
     */
    private final int width_x;

    /**
     * similarly, height of the interactive window
     */
    private final int height_y;

    /**
     * holds all pixels information.
     */
    private final Particle[] allPixels;
    /**
     * scale factor of making each pixel bigger
     */
    private final int scaleFactor;

    /**
     * Renderer logic to call when drawing
     */
    private final RenderLogic renderLogic;

    SimulatorLogic simulatorLogic;

    /**
     * constructor, creates all pixel and clears them to null as default.
     *
     * @param width_x  is the width of the window
     * @param height_y height of the window
     */
    public SimulatorScreen(int width_x, int height_y, int scaleFactor) {
        this.width_x = width_x;
        this.height_y = height_y;
        this.scaleFactor = scaleFactor;
        allPixels = new Particle[width_x * height_y];
        simulatorLogic = new SimulatorLogic(this);
        renderLogic = new RenderLogic(this);
        clearScreen();
        startSimulation();

    }


    /**
     * Clears the screen, resetting everything back to white, by setting all particles to null.
     */
    public void clearScreen() {
        for (int i = 0; i < width_x * height_y; i++) {
            if (allPixels[i] != null) {
                allPixels[i] = null;
            }
        }
        repaint();
    }

    /**
     * call this function to place the particle on that pixel
     */
    public void AddParticleToPixel(int x, int y, Particle particle) {
        allPixels[getPixelIndex(x / scaleFactor, y / scaleFactor)] = particle;
        renderLogic.markPixelModified(x / scaleFactor, y / scaleFactor, -1, -1);
        simulatorLogic.setOccupied(y / scaleFactor);
    }

    public void SwapPixels(int p1, int p2) {
        final Particle temp = allPixels[p2];
        allPixels[p2] = allPixels[p1];
        allPixels[p1] = temp;
        renderLogic.markPixelModified(p2 % width_x, p2 / width_x, p1 % width_x, p1 / width_x);
        simulatorLogic.setOccupied(p2 / width_x);
    }


    protected Color getParticleColour(int x, int y) {
        if (allPixels[getPixelIndex(x, y)] != null) {
            return allPixels[getPixelIndex(x, y)].getParticleColour();
        }
        return Color.WHITE;

    }


    /**
     * checks if that pixel is null.
     *
     * @param x is the x coordinates of cursor -> divide by scale factor to get true pixel index
     * @param y is the y coordinates -> divide by scale factor to get true pixel index
     * @return true if null pixel
     */
    public boolean isEmptyPixelForCursor(int x, int y) {
        if (x > maxDimensions()[0]
                || x < 0
                || y < 0
                || y > maxDimensions()[1])
            return false;

        try {
            return allPixels[
                    getPixelIndex(x / scaleFactor, y / scaleFactor)
                    ] == null;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean isEmptyPixel(int index) {
        if (index >= width_x * height_y || index < 0) return false;
        return allPixels[index] == null;
    }

    protected int getPixelIndex(int x, int y) {
        return y * width_x + x;
    }


    private void startSimulation() {
        setTitle("Interactive Window");
        setSize(width_x * scaleFactor, height_y * scaleFactor); // Adjusted size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a custom JPanel to render pixels (override paintComponent method)
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                for (int y = 0; y < height_y; y++) {
                    for (int x = 0; x < width_x; x++) {
                        // Check if the pixel is not empty
                        g.setColor(getParticleColour(x, y));
                        g.fillRect(x * scaleFactor, y * scaleFactor, scaleFactor, scaleFactor);

                    }
                }

            }
        };

        MouseHandler mouseHandler = new MouseHandler(simulatorLogic);
        panel.addMouseListener(mouseHandler);
        panel.addMouseMotionListener(mouseHandler);
        KeyboardHandler keyboardHandler = new KeyboardHandler(simulatorLogic);
        panel.setFocusable(true); // Ensure that the panel can receive keyboard events
        panel.addKeyListener(keyboardHandler);

        getContentPane().add(panel);
        setVisible(true);

        /*
         * timer for 45 fps
         */
        // 45 fps
        int targetFPS = 45; // Adjust target frame rate as needed
        long targetFrameTime = 1000 / targetFPS; // Target time per frame in milliseconds


        Timer timer = new Timer(0, e -> {
            long startTime = System.currentTimeMillis(); // Record start time
            simulatorLogic.updateParticles();
            renderLogic.updateModifiedPixels(getGraphics());
            long endTime = System.currentTimeMillis(); // Record end time
            long elapsedTime = endTime - startTime; // Calculate elapsed time

            long sleepTime = targetFrameTime - elapsedTime; // Calculate sleep time to achieve target FPS
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime); // Sleep to achieve desired frame rate
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            // Calculate and display actual frame rate
            if (sleepTime < 0)
            System.out.println(sleepTime);
        });

        timer.start();
    }


    protected int[] maxDimensions() {
        int[] max = new int[2];
        max[0] = width_x * scaleFactor;
        max[1] = height_y * scaleFactor;
        return max;
    }

    public int getHeight_y() {
        return height_y;
    }

    public int getWidth_x() {
        return width_x;
    }

    public Particle[] getAllPixels() {
        return allPixels;
    }


    public int getScaleFactor() {
        return scaleFactor;
    }

}
