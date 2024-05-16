package LogicHandling;

import Particles.Particle;

import java.util.Random;

public class SimulatorLogic {

    private final SimulatorScreen simulatorScreen;
    private final boolean[] occupied;


    public SimulatorLogic (SimulatorScreen screen) {
        simulatorScreen = screen;
        this.occupied = new boolean[screen.getHeight_y()];
    }

    public void setOccupied(int y) {
        occupied[y] = true;
        occupied[y-1] = true;
        occupied[y-2] = true;
    }


    public void drawCircularParticles(int centerX, int centerY,
                                      Particle particle, int radius) {

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
                            rand = random.nextInt(1, 500);
                            if (rand > 495) {
                                simulatorScreen.AddParticleToPixel(drawX, drawY, particle);

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
            if (!occupied[k]) continue;
            occupied[k] = false;

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

                if (allPixels[i] != null) {

                    below = i + simulatorScreen.getWidth_x();
                    belowRight = i + simulatorScreen.getWidth_x() + 1;
                    belowLeft = i + simulatorScreen.getWidth_x() - 1;

                    if (simulatorScreen.isEmptyPixel(below)) {
                        simulatorScreen.SwapPixels(i,below);
                    }


                    else if (simulatorScreen.isEmptyPixel(belowLeft)
                            && i % simulatorScreen.getWidth_x() != 0) {

                        simulatorScreen.SwapPixels(i,belowLeft);
                    }
                    //left was not empty
                    else if (simulatorScreen.isEmptyPixel(belowRight)
                            && i % simulatorScreen.getWidth_x() != simulatorScreen.getWidth_x() -1) {
                        simulatorScreen.SwapPixels(i,belowRight);
                    }

                }
            }
        }

    }



    public void clearScreen() {
        simulatorScreen.clearScreen();
    }

}

