package by.bsu;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public abstract class KeyCombinationAdapter extends KeyAdapter implements KeyCombinationListener {
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == 'a' && e.isAltDown()) {
            KeyCombinationEvent event = new KeyCombinationEvent((Component) e.getSource(), e.getID(), e.getWhen(), e.getModifiersEx(), e.getKeyCode(), e.getKeyChar(), e.getKeyLocation());
            this.keyCombinationTyped(event);
        }
    }
}
