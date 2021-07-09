import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Zelle extends JButton implements MouseListener {

    private int wert, gefahrenwert, xPos, yPos, xPosPx, yPosPx;
    private final Steuerung dieSteuerung;

    public Zelle(int yPos, int xPos, int breite, int wert, int gefahrenwert, Steuerung steuerung) {
        this.wert = wert;
        this.xPos = xPos;
        this.yPos = yPos;
        this.gefahrenwert = gefahrenwert;
        dieSteuerung = steuerung;

        xPosPx = xPos * breite;
        yPosPx = yPos * breite;

        setBounds(xPosPx, yPosPx, breite, breite);

        if (wert == 1)
            setBackground(Color.red);
        else
            setBackground(Color.lightGray);

        addMouseListener(this);

    }

    public void entdecke() {
        if (wert == -1) {
            dieSteuerung.verloren();
        } else if (wert == 0) {
            deckeAuf();
            if (gefahrenwert == 0)
                dieSteuerung.deckeAuf(xPos, yPos);
        }
        dieSteuerung.gewonnen();
    }

    private void markiere() {
        if (getBackground().equals(Color.black)) {
            setBackground(Color.lightGray);
        } else {
            setBackground(Color.black);
            if (wert == -1) {
                wert = 2;
            }
        }

    }

    public void deckeAuf() {
        wert = 1;
        setBackground(Color.green);
        if (gefahrenwert != 0)
            setText("" + gefahrenwert);
    }

    public int getWert() {
        return wert;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            markiere();
        } else if (SwingUtilities.isLeftMouseButton(e)) {
            entdecke();
        }
    }

    @Override public void mousePressed(MouseEvent e) { }
    @Override public void mouseReleased(MouseEvent e) { }
    @Override public void mouseEntered(MouseEvent e) { }
    @Override public void mouseExited(MouseEvent e) { }
}
