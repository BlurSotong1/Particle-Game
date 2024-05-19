package Particles;

import java.awt.*;
import java.util.Random;

public class SandParticle implements Particle{


    final private Color colour = getRandomColour();
    private float velocity = 0;

    private static float maxVelocity;

    public SandParticle () {
    }
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

    public float getVelocity () {
        return velocity;
    }

    public void increaseVelocity () {
        if (velocity <= maxVelocity) {
            velocity += 0.8f;
        }

    }

    public void dampenVelocity () {

            velocity *= 0.5f;

    }

    public static void setMaxVelocity (float maxVelocity) {
        SandParticle.maxVelocity = maxVelocity;
    }





}
