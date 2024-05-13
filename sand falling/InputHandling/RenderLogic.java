package InputHandling;

import Particles.Particle;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RenderLogic {
    private SimulatorScreen simulatorScreen;
    private ArrayList<Point> modifiedPixels;

    public RenderLogic(SimulatorScreen simulatorScreen) {
        this.simulatorScreen = simulatorScreen;
        modifiedPixels = new ArrayList<>();
    }

    public void markPixelModified(int x1, int y1, int x2, int y2) {
        modifiedPixels.add(new Point(x1, y1,x2,y2));
    }

    public void updateModifiedPixels(Graphics g) {
        int scaleFactor = simulatorScreen.getScaleFactor();
        int x, y;

        modifiedPixels.sort((p1, p2) -> Integer.compare(p2.y_cur, p1.y_cur));

        for (Point point : modifiedPixels) {
            x = point.x_cur;
            y = point.y_cur;
            int xPrev = point.x_prev;
            int yPrev = point.y_prev;

            g.setColor(simulatorScreen.getParticleColour(x, y));
            g.fillRect(x * scaleFactor, y * scaleFactor, scaleFactor, scaleFactor);

            if (xPrev != -1) {

                g.setColor(Color.WHITE);
                g.fillRect(xPrev * scaleFactor, yPrev * scaleFactor, scaleFactor, scaleFactor);


                g.setColor(simulatorScreen.getParticleColour(xPrev, yPrev));
                g.fillRect(xPrev * scaleFactor, yPrev * scaleFactor, scaleFactor, scaleFactor);

            }

        }

        modifiedPixels.clear(); // Clear the set after updating

    }

    private static class Point {
        int x_cur;
        int y_cur;
        int y_prev;
        int x_prev;

        public Point(int x_cur, int y_cur, int x_prev, int y_prev) {
            this.x_cur = x_cur;
            this.y_cur = y_cur;
            this.x_prev = x_prev;
            this.y_prev = y_prev;
        }

        public Point(int x, int y) {
        }
    }
}
