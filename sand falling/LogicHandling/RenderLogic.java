package LogicHandling;

import java.awt.*;
import java.util.ArrayList;

public class RenderLogic {
    private final SimulatorScreen simulatorScreen;
    private final ArrayList<Coordinates> modifiedPixels;

    final int scaleFactor;

    public RenderLogic(SimulatorScreen simulatorScreen) {
        this.simulatorScreen = simulatorScreen;
        modifiedPixels = new ArrayList<>();
        scaleFactor = simulatorScreen.getScaleFactor();
    }

    public void markPixelModified(int x1, int y1, int x2, int y2) {
        modifiedPixels.add(new Coordinates(x1, y1));
        if (x2 != -1)
            modifiedPixels.add(new Coordinates(x2, y2));
    }

    public void updateModifiedPixels(Graphics g) {

        int x, y;


        for (Coordinates coordinates : modifiedPixels) {

            x = coordinates.x_cur();
            y = coordinates.y_cur();

            g.setColor(simulatorScreen.getParticleColour(x, y));
            g.fillRect(x * scaleFactor, y * scaleFactor, scaleFactor, scaleFactor);


        }

        modifiedPixels.clear(); // Clear the set after updating

    }


}
