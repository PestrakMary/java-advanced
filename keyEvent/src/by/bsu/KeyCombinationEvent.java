package by.bsu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.EventObject;

public class KeyCombinationEvent extends KeyEvent {


    public KeyCombinationEvent(Component source, int id, long when, int modifiers, int keyCode, char keyChar, int keyLocation) {
        super(source, id, when, modifiers, keyCode, keyChar, keyLocation);
    }
}
