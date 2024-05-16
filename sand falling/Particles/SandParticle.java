package Particles;

import java.awt.*;
import java.util.Random;

public class SandParticle implements Particle{


    final private Color colour = getRandomColour();


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



}
