package Particles;

import java.awt.*;
import java.util.Random;

public class SandParticle implements Particle{


    final private Color colour = getRandomColour();
    private float velocity = 1;

    private static float maxVelocity;

    private float acceleration = 0.2f;

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
            velocity += acceleration;
            acceleration += 0.05f;
        }

    }

    public void dampenVelocity () {
        if (velocity >= 2)
            velocity /= 1.5f;
        else
            velocity = 1;

        acceleration = 0.2f;
    }

    public static void setMaxVelocity (float maxVelocity) {
        SandParticle.maxVelocity = maxVelocity;
    }





}
