import javax.swing.JFrame;


public class Fenster {
    private Spiel spiel;   // Referenz auf das Spiel
    private JFrame frame;   // Das eigentliche JFrame, welches das Fenster darstellt

    // Im Konstruktor wird ein ein JFrame erzeugt und konfiguriert,
    //  im Vollbildmodus zu laufen und das Spiel wird als Canvas hinzugefügt
    // Am Ende wird spiel.starten() aufgerufen, um die Game-Loop anzuschmeissen
    public Fenster(Spiel spiel) {
        this.spiel = spiel;
        frame = new JFrame();

        frame.setUndecorated(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.add(spiel);
        frame.setVisible(true);

        spiel.starten();
    }
}