import javax.swing.JFrame;


public class Window {
    // Im Konstruktor wird ein ein JFrame erzeugt und konfiguriert,
    //  im Vollbildmodus zu laufen und das Spiel wird als Canvas hinzugefügt
    // Am Ende wird spiel.starten() aufgerufen, um die Game-Loop anzuschmeissen
    public Window(Game game) {
        JFrame frame = new JFrame();

        frame.setUndecorated(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.add(game);
        frame.setVisible(true);

        game.launch();
    }
}