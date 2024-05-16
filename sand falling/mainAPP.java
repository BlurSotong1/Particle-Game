import LogicHandling.SimulatorScreen;

public class mainAPP {

    public static void main(String[] args) {

        int scaleFactor = 5;
        int x = 1000/scaleFactor;
        int y = 800/scaleFactor;
        new SimulatorScreen(x,y,scaleFactor);

    }

}
