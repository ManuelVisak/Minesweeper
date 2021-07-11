import javax.swing.*;
import java.awt.*;

public class Steuerung {

    private final Spielfeld dasSpielfeld = new Spielfeld(this);
    private final Zelle[][] dieZellen = new Zelle[dasSpielfeld.BREITE][dasSpielfeld.HOEHE];
    private final int[][] dasMinenfeld = new int[dasSpielfeld.BREITE][dasSpielfeld.HOEHE];
    private final int[][] dasMinenfeldMitRand = new int[dasSpielfeld.BREITE + 2][dasSpielfeld.HOEHE + 2];
    private final int[][] dasGefahrenfeld = new int[dasMinenfeld.length][dasMinenfeld[0].length];


    public Steuerung() {
        neuesSpiel();
    }

    private void neuesSpiel() {

        fuelleMinenfeld();
        fuelleGefahrenfeld();
        dasSpielfeld.erzeugeButtons(dieZellen, dasMinenfeld, dasGefahrenfeld);
    }

    private void fuelleMinenfeld() {
        for (int i = 0; i < dasMinenfeld.length; i++) {
            for (int j = 0; j < dasMinenfeld[0].length; j++) {
                dasMinenfeld[i][j] = mine(0.2);
            }
        }

        for (int i = 0; i < dasMinenfeld.length; i++) {
            for (int j = 0; j < dasMinenfeld[i].length; j++) {
                if (j == 0 || i == 0)
                    dasMinenfeldMitRand[i][j] = 0;
                dasMinenfeldMitRand[i + 1][j + 1] = dasMinenfeld[i][j];
            }
            dasMinenfeldMitRand[i][dasMinenfeldMitRand[0].length - 1] = 0;
        }
        for (int i = 0; i < dasMinenfeldMitRand[0].length; i++) {
            dasMinenfeldMitRand[dasMinenfeldMitRand.length - 1][i] = 0;
        }
    }

    private void fuelleGefahrenfeld() {
        for (int i = 0; i < dasGefahrenfeld.length; i++) {
            for (int j = 0; j < dasGefahrenfeld[i].length; j++) {
                dasGefahrenfeld[i][j] = zaehleMinenUm(i, j);
            }
        }
    }

    private int zaehleMinenUm(int y, int x) {

        int minen = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (!(i == 0 && j == 0)) {
                    minen -= dasMinenfeldMitRand[y + 1 + i][x + 1 + j];
                }
            }
        }
        return minen;
    }

    public void verloren() {
        for (int i = 0; i < dasMinenfeld.length; i++) {
            for (int j = 0; j < dasMinenfeld[0].length; j++) {
                if (dasMinenfeld[i][j] == -1) {
                    dieZellen[i][j].setBackground(Color.red);
                }
            }
        }
        JOptionPane.showMessageDialog(null, "DEFEAT", "Minesweeper", JOptionPane.INFORMATION_MESSAGE);
        dasSpielfeld.dispose();
    }

    public void schaueObGewonnen() {
        boolean b = true;
        for (int i = 0; i < dieZellen.length; i++) {
            for (int j = 0; j < dieZellen[0].length; j++) {
                if (!((dieZellen[i][j].getWert() == 2 || dieZellen[i][j].getWert() == 1) && b)) {
                    b = false;
                    break;
                }
            }
        }
        if (b) {
            JOptionPane.showMessageDialog(null, "VICTORY", "Minesweeper", JOptionPane.QUESTION_MESSAGE);
            dasSpielfeld.dispose();
        }
    }

    public void deckeNebenanAuf(int x, int y) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (istZelle(x + i, y + j))
                    dieZellen[x + i][y + j].setWert(1);
            }
        }
    }

    private boolean istZelle(int x, int y) {
        return x < dieZellen.length && y < dieZellen[0].length && x >= 0 && y >= 0;
    }

    private int mine(double wahrscheinlichkeit) {
        if (Math.random() < wahrscheinlichkeit)
            return -1;
        else
            return 0;

    }

    public static void main(String[] args) {
        new Steuerung();
    }
}
