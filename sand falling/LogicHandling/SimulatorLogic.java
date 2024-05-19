package LogicHandling;

import Particles.Particle;
import Particles.SandParticle;

import java.awt.*;
import java.util.Random;

public class SimulatorLogic {

    private final SimulatorScreen simulatorScreen;
    private final RenderLogic renderLogic;

    private final boolean[] occupied;


    public SimulatorLogic (SimulatorScreen screen, RenderLogic renderLogic) {
        simulatorScreen = screen;
        this.occupied = new boolean[screen.getHeight_y() * screen.getWidth_x()];
        this.renderLogic = renderLogic;
        SandParticle.setMaxVelocity(10.0f);

    }

    public void needsUpdate(int x, int y) {
        int width = simulatorScreen.getWidth_x();
        occupied[y * width + x] = true;
    try {
        occupied[(y-1) * width + x - 1] = true;
        occupied[(y-1) * width + x    ] = true;
        occupied[(y-1) * width + x + 1] = true;

        occupied[(y-2) * width + x - 2] = true;
        occupied[(y-2) * width + x - 1] = true;
        occupied[(y-2) * width + x + 1] = true;
        occupied[(y-2) * width + x + 2] = true;
    } catch (IndexOutOfBoundsException ignored) {

    }


    }


    public void drawCircularParticles(int centerX, int centerY, int radius) {

        if (simulatorScreen.isEmptyPixelForCursor(centerX,centerY)) {
            Random random = new Random();
            centerX -= 1;
            centerY += 21;

            radius *= simulatorScreen.getScaleFactor();

            int rand;
            int drawX, drawY;
            for (int x = -radius; x < radius; x++) {

                for (int y = -radius; y < radius; y++) {

                    if (x * x + y * y <= radius * radius) {
                        // Check if (x,y) is within the circle
                        drawX = centerX + x;
                        drawY = centerY + y;

                        if (simulatorScreen.isEmptyPixelForCursor(drawX, drawY)) {
                            rand = random.nextInt(1, 80);
                            if (rand > 78) {
                                simulatorScreen.AddParticleToPixel(drawX, drawY, new SandParticle());

                            }

                        }
                    }
                }
            }
        }
    }


    public void updateParticles() {
        Particle[] allPixels = simulatorScreen.getAllPixels();

        int below;
        int belowLeft;
        int belowRight;

        int starting;
        int maxOrMin;
        int counter;

        int i;

        Graphics graphics = simulatorScreen.getGraphics();

        Random random = new Random();
        for (int y = (simulatorScreen.getHeight_y()-2); y >= 3; y--) {

            if (random.nextBoolean()) {
                starting = 0;
                maxOrMin = simulatorScreen.getWidth_x();
                counter = 1;
            } else {
                starting = simulatorScreen.getWidth_x();
                maxOrMin = 0;
                counter = -1;
            }

            for (int x = starting; x != maxOrMin; x+= counter) {
                i = y * simulatorScreen.getWidth_x() + x;

                if (!occupied[i]) continue;

                occupied[i] = false;

                if (allPixels[i] != null) {
                    final float velocity = allPixels[i].getVelocity();

                    if (velocity == 0) {
                        allPixels[i].increaseVelocity();
                        occupied[i] = true;
                        continue;
                    }
                    int changingCoordinates = i;


                    allPixels[changingCoordinates].increaseVelocity();

                    for (int p = 0; p < velocity; p++) {
                        renderLogic.updateModifiedPixels(graphics);
                        below = changingCoordinates + simulatorScreen.getWidth_x();
                        belowRight = changingCoordinates + simulatorScreen.getWidth_x() + 1;
                        belowLeft = changingCoordinates + simulatorScreen.getWidth_x() - 1;


                        if (simulatorScreen.isEmptyPixel(below)) {

                            simulatorScreen.SwapPixels(changingCoordinates,below);

                            changingCoordinates = below;

                        }


                        else if (simulatorScreen.isEmptyPixel(belowLeft)
                                && changingCoordinates % simulatorScreen.getWidth_x() != 0) {

                            simulatorScreen.SwapPixels(changingCoordinates,belowLeft);
                            changingCoordinates = belowLeft;

                        }

                        //left was not empty
                        else if (simulatorScreen.isEmptyPixel(belowRight)
                                && changingCoordinates % simulatorScreen.getWidth_x() != simulatorScreen.getWidth_x() -1) {

                            simulatorScreen.SwapPixels(changingCoordinates,belowRight);
                            changingCoordinates = belowRight;

                        } else {
                            allPixels[changingCoordinates].dampenVelocity();
                            break;
                        }
                    }
                }
            }
        }

    }





    public void clearScreen() {
        simulatorScreen.clearScreen();
    }

    public int getRadius () {

        return ((simulatorScreen.getHeight_y() + simulatorScreen.getWidth_x()) / 2)
                / 100 * (10 - simulatorScreen.getScaleFactor());
    }


}

