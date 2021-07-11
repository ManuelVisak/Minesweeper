import javax.swing.*;

public class Spielfeld extends JFrame {

    public final int BREITE = 25, HOEHE = 15, ZELLENBREITE = 45;
    private final Steuerung dieSteuerung;

    public Spielfeld(Steuerung steuerung) {
        dieSteuerung = steuerung;

        erzeugeSpielfeld();
    }

    private void erzeugeSpielfeld() {
        setLayout(null);
        setTitle("Traffic Jam");
        setSize(BREITE * ZELLENBREITE + 100, HOEHE * ZELLENBREITE + 100);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    public void erzeugeButtons(Zelle[][] dieZellen, int[][] dasMinenfeld, int[][] dasGefahrenfeld) {

        for (int i = 0; i < dieZellen.length; i++) {
            for (int j = 0; j < dieZellen[0].length; j++) {
                dieZellen[i][j] = new Zelle(j, i, ZELLENBREITE, dasMinenfeld[i][j], dasGefahrenfeld[i][j], dieSteuerung);
                add(dieZellen[i][j]);
                revalidate();
                repaint();
            }
        }
    }
}
