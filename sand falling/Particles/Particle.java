package Particles;

import java.awt.*;

public interface Particle {

     Color getRandomColour();
     Color getParticleColour();
     float getVelocity();
     void increaseVelocity();
     void dampenVelocity();


}
