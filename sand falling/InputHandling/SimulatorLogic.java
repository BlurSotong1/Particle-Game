package InputHandling;

import Particles.Particle;

import java.util.Random;

public class SimulatorLogic {

    SimulatorScreen simulatorScreen;


    public SimulatorLogic (SimulatorScreen screen) {
        simulatorScreen = screen;
    }

    public void addParticles(int centerX, int centerY, Particle particle, int radius) {
        Random random = new Random();

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                if (x * x + y * y <= radius * radius) { // Check if (x,y) is within the circle
                    int drawX = centerX + x;
                    int drawY = centerY + y;
                    if (simulatorScreen.isEmptyPixelForCursor(drawX, drawY)) {
                        if (random.nextInt(1,100) > 95)
                            simulatorScreen.addParticle(drawX, drawY, particle);
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

        for (int i = simulatorScreen.getWidth_x() * (simulatorScreen.getHeight_y()-1); i > 0 ; i--) {
            if (allPixels[i] != null) {

                below = i + simulatorScreen.getWidth_x();
                belowRight = i + simulatorScreen.getWidth_x() + 1;
                belowLeft = i + simulatorScreen.getWidth_x() - 1;

                if (simulatorScreen.isEmpty(below)) {
                    simulatorScreen.swap(i,below);
                }

                else if (i % simulatorScreen.getWidth_x() == 0) { // is the most left pixel, only check for right

                    if (simulatorScreen.isEmpty(belowRight)) {
                        simulatorScreen.swap(i,belowRight);
                    }
                }

                else if (i % (simulatorScreen.getWidth_x()-1) == 0) { // is the most right pixel, only check for left
                    if (simulatorScreen.isEmpty(belowLeft)) {
                        simulatorScreen.swap(i,belowLeft);
                    }

                }


                else { //neither, center block. choose random go left or right if neither is occupied
                    if (simulatorScreen.isEmpty(belowLeft) ) {

                        if (simulatorScreen.isEmpty(belowRight)) { //both are avail so choose random
                            Random random = new Random();
                            if (random.nextInt(1,1000) > 500) {
                                simulatorScreen.swap(i,belowLeft);
                            } else {
                                simulatorScreen.swap(i,belowRight);
                            }
                            continue;
                        }
                        //right is not empty go left
                        simulatorScreen.swap(i,belowLeft);
                        continue;
                    }
                    //left was not empty
                    if (simulatorScreen.isEmpty(belowRight)) {
                        simulatorScreen.swap(i,belowRight);
                    }

                }
            }
        }

    }

    public void clearScreen() {
        simulatorScreen.clearScreen();
    }




}

