import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Zelle extends JButton implements MouseListener {

    private int wert;
    private final int gefahrenwert, xPos, yPos;
    private final Steuerung dieSteuerung;
    private final int MINE = -1, FREI = 0, ENTDECKT = 1, MARKIERT_MINE = 2,MARKIERT_FREI = 3;
    private final Color DUNKEL_GRUEN = Color.getHSBColor(0.4F, 0.75F, 0.8F), GRUEN = Color.getHSBColor(0.35F, 0.6F, 0.9F),
            HELL_GRUEN = Color.getHSBColor(0.23F, 0.6F, 0.9F), ORANGE = Color.getHSBColor(0.1F, 0.75F, 0.9F),
            DUNKEL_ORANGE = Color.getHSBColor(0.3F, 0.8F, 0.7F);

    public Zelle(int yPos, int xPos, int breite, int wert, int gefahrenwert, Steuerung steuerung) {
        this.wert = wert;
        this.xPos = xPos;
        this.yPos = yPos;
        this.gefahrenwert = gefahrenwert;
        dieSteuerung = steuerung;

        int xPosPx = xPos * breite;
        int yPosPx = yPos * breite;

        setBounds(xPosPx, yPosPx, breite, breite);

        if (wert == 1)
            setBackground(Color.red);
        else
            setBackground(Color.lightGray);

        addMouseListener(this);

    }

    public void entdecke() {
        switch (wert) {
            case MINE: dieSteuerung.verloren(); break;
            case FREI: wert = ENTDECKT;
            if (gefahrenwert == 0) {
                dieSteuerung.deckeNebenanAuf(xPos, yPos);
            } break;
            case ENTDECKT: if (gefahrenwert == 0) {
                dieSteuerung.deckeNebenanAuf(xPos, yPos);
            } break;
            case MARKIERT_MINE:
            case MARKIERT_FREI:break;
        }
        updateZelle();
    }

    private void markiere() {
        switch (wert) {
            case MINE: wert = MARKIERT_MINE;
            case FREI: wert = MARKIERT_FREI;
            case ENTDECKT: break;
            case MARKIERT_MINE: wert = MINE;
            case MARKIERT_FREI: wert = FREI;
        }
        updateZelle();
    }

    private void updateZelle() {
        switch (wert) {
            case MINE:
            case FREI: setText(""); setBackground(Color.lightGray); break;
            case ENTDECKT: switch (gefahrenwert) {
                case 0: setBackground(DUNKEL_GRUEN); setText(""); break;
                case 1:
                case 2: setBackground(GRUEN); setText("" + gefahrenwert); break;
                case 3: setBackground(HELL_GRUEN); setText("" + gefahrenwert); break;
                case 4: setBackground(ORANGE); setText("" + gefahrenwert); break;
                case 5:
                case 6:
                case 7:
                case 8: setBackground(DUNKEL_ORANGE); setText("" + gefahrenwert); break;
                }
            break;
            case MARKIERT_MINE:
            case MARKIERT_FREI: setText(""); setBackground(Color.black); break;
        }
    }

   public  void setWert(int wert) {
        this.wert = wert;
        updateZelle();
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
