package Particles;

import java.awt.*;

public interface Particle {

    int[] coordinates = new int[2];
    Color colour = null;

    public void createParticle (int x, int y);
    public Color getRandomColour();
    public Color getParticleColour();
    public boolean isParticleSettled();
    public void updateSettled();
    public int[] getCoordinates();
    public void setCoordinates(int[] coordinates);

}
