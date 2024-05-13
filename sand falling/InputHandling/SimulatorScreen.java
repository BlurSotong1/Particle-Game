package InputHandling;

import Particles.Particle;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class SimulatorScreen extends JFrame {
    /**
     * width of the window im creating
     */
    private int width_x = 0;

    /**
     * similarly, height of the interactive window
     */
    private int height_y = 0;

    /**
     * holds all pixels information.
     */
    private Particle[] allPixels;
    /**
     * scale factor of making each pixel bigger
     */
    private int scaleFactor;
    /**
     * calculate how long one frame takes
     */
    private long frameTime;
    /**
     * Renderer logic to call when drawing
     */
    private RenderLogic renderLogic;

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
    protected void clearScreen() {
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
    protected void addParticle(int x, int y, Particle particle) {
        allPixels[getPixelIndex(x / scaleFactor, y / scaleFactor)] = particle;
        renderLogic.markPixelModified(x / scaleFactor, y / scaleFactor, -1, -1);
    }

    protected void swap(int p1, int p2) {
        allPixels[p2] = allPixels[p1];
        allPixels[p1] = null;
        renderLogic.markPixelModified(p2 % width_x, p2 / width_x, p1 % width_x, p1 / width_x);
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
     * @param x is the x coordinates
     * @param y is the y coordinates
     * @return true if null pixel
     */
    protected boolean isEmptyPixelForCursor(int x, int y) {
        if (x > maxDimensions()[0] || x < 0 || y < 0 || y > maxDimensions()[1]) return false;

        try {
            if (allPixels[getPixelIndex(x / scaleFactor, y / scaleFactor)] == null) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }

    }

    protected boolean isEmpty(int index) {
        if (index >= width_x * height_y || index < 0) return false;
        if (allPixels[index] == null) return true;
        return false;
    }


    protected int getPixelIndex(int x, int y) {
        return y * width_x + x;
    }

    protected void clearPixel(int x, int y) {
        allPixels[getPixelIndex(x, y)] = null;
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

        /**
         * timer for x fps
         */
        // 100 fps
        int frameDelay = 1000 / 60;
        Timer timer = new Timer(frameDelay, e -> {

            simulatorLogic.updateParticles();
            renderLogic.updateModifiedPixels(panel.getGraphics());

        });

        timer.start();
    }


    protected int[] maxDimensions() {
        int[] max = new int[2];
        max[0] = width_x * scaleFactor;
        max[1] = height_y * scaleFactor;
        return max;
    }

    protected int getHeight_y() {
        return height_y;
    }

    protected int getWidth_x() {
        return width_x;
    }

    protected Particle[] getAllPixels() {
        return allPixels;
    }


    public int getScaleFactor() {
        return scaleFactor;
    }
}
