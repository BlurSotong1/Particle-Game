import LogicHandling.SimulatorScreen;

public class mainAPP {

    public static void main(String[] args) {

        int scaleFactor = 3;
        int x = 600/scaleFactor;
        int y = 500/scaleFactor;
        new SimulatorScreen(x,y,scaleFactor);

    }

}
