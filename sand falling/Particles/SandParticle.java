package Particles;

import java.awt.*;
import java.util.Random;

public class SandParticle implements Particle{


    final private Color colour = getRandomColour();

    private boolean settled = false;

    /**
     * universal constructor for particles, overridden for sand
     * @param x is the x coordinate of the mouse
     * @param y is the y coordinate of the mouse
     */
    @Override
    public void createParticle(int x, int y) {
    }

    /**
     * varies the sand colour from default:
     * 	R: 194, G: 178, B: 128, adjusting the rgb slightly for aesthetics.
     * @return varied sand colour.
     */
    @Override
    public Color getRandomColour() {
        Random random = new Random();

        int red   = 194 + random.nextInt(-10,10);
        int green = 178 + random.nextInt(-10,10);
        int blue  = 128 + random.nextInt(-10,10);
        return new Color( red , green , blue );

    }


    @Override
    public Color getParticleColour() {
        return colour;
    }

    @Override
    public int[] getCoordinates() {
        return coordinates;
    }

    @Override
    public void setCoordinates(int[] coordinates) {
        this.coordinates[0] = coordinates[0];
        this.coordinates[1] = coordinates[1];
    }

    @Override
    public boolean isParticleSettled() {
        return settled;
    }

    @Override
    public void updateSettled() {
        settled = true;
    }
}
