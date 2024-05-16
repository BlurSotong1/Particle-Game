package Particles;

import java.awt.*;
import java.util.Random;

public class SandParticle implements Particle{


    final private Color colour = getRandomColour();
    private float velocity = 1;

    private final static float MAX_VELOCITY = 5.0f;

    private float acceleration = MAX_VELOCITY/100;


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
        if (velocity <= MAX_VELOCITY) {
            velocity += acceleration;
            acceleration += 0.1f;
        }

    }

    public void dampenVelocity () {
        if (velocity >= 2)
            velocity /= 1.5f;
        else
            velocity = 1;

        acceleration = MAX_VELOCITY/100;

    }





}
