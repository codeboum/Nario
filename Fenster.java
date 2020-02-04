import javax.swing.JFrame;


public class Fenster {
    private Spiel spiel;
    private JFrame frame;

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