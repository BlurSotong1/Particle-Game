package LogicHandling;

import Particles.Particle;
import Particles.SandParticle;

import java.util.Random;

public class SimulatorLogic {

    private final SimulatorScreen simulatorScreen;

    private final boolean[] occupied;


    public SimulatorLogic (SimulatorScreen screen) {
        simulatorScreen = screen;
        this.occupied = new boolean[screen.getHeight_y() * screen.getWidth_x()];
        SandParticle.setMaxVelocity(8.0f);

    }

    public void setOccupied(int x, int y) {
        int width = simulatorScreen.getWidth_x();
        occupied[y * width + x] = true;

        occupied[(y-1) * width + x - 1] = true;
        occupied[(y-1) * width + x    ] = true;
        occupied[(y-1) * width + x + 1] = true;

        occupied[(y-2) * width + x - 2] = true;
        occupied[(y-2) * width + x - 1] = true;
        occupied[(y-2) * width + x + 1] = true;
        occupied[(y-2) * width + x + 2] = true;

    }


    public void drawCircularParticles(int centerX, int centerY, int radius) {

        if (simulatorScreen.isEmptyPixelForCursor(centerX,centerY)) {
            Random random = new Random();
            centerX -= 1;
            centerY += 21;

            radius *= simulatorScreen.getScaleFactor();

            int rand;
            for (int x = -radius; x < radius; x++) {

                for (int y = -radius; y < radius; y++) {

                    if (x * x + y * y <= radius * radius) {
                        // Check if (x,y) is within the circle
                        int drawX = centerX + x;
                        int drawY = centerY + y;

                        if (simulatorScreen.isEmptyPixelForCursor(drawX, drawY)) {
                            rand = random.nextInt(1, 10);
                            if (rand > 8) {
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


        Random random = new Random();
        for (int k = (simulatorScreen.getHeight_y()-2); k >= 3; k--) {

            if (random.nextBoolean()) {
                starting = 0;
                maxOrMin = simulatorScreen.getWidth_x();
                counter = 1;
            } else {
                starting = simulatorScreen.getWidth_x();
                maxOrMin = 0;
                counter = -1;
            }

            for (int j = starting; j != maxOrMin; j+= counter) {
                i = k * simulatorScreen.getWidth_x() + j;

                if (!occupied[i]) continue;

                occupied[i] = false;

                if (allPixels[i] != null) {
                    int changingCoordinates = i;
                    final float velocity = allPixels[i].getVelocity();

                    for (int x = 0; x < velocity; x++) {
                        below = changingCoordinates + simulatorScreen.getWidth_x();
                        belowRight = changingCoordinates + simulatorScreen.getWidth_x() + 1;
                        belowLeft = changingCoordinates + simulatorScreen.getWidth_x() - 1;


                        if (simulatorScreen.isEmptyPixel(below)) {

                            simulatorScreen.SwapPixels(changingCoordinates,below);

                            changingCoordinates = below;
                            allPixels[changingCoordinates].increaseVelocity();
                        }


                        else if (simulatorScreen.isEmptyPixel(belowLeft)
                                && changingCoordinates % simulatorScreen.getWidth_x() != 0) {

                            simulatorScreen.SwapPixels(changingCoordinates,belowLeft);
                            changingCoordinates = belowLeft;
                            allPixels[changingCoordinates].increaseVelocity();
                        }

                        //left was not empty
                        else if (simulatorScreen.isEmptyPixel(belowRight)
                                && changingCoordinates % simulatorScreen.getWidth_x() != simulatorScreen.getWidth_x() -1) {
                            simulatorScreen.SwapPixels(changingCoordinates,belowRight);

                            changingCoordinates = belowRight;
                            allPixels[changingCoordinates].increaseVelocity();
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

