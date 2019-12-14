package by.bsu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class MyButton extends JButton {
    private LinkedList<KeyCombinationListener> ls = new LinkedList<KeyCombinationListener>();
    MyButton()
    {
        this.setSize(400, 400);
        this.setBackground(new Color(255, 255, 255));
        this.setFocusable(true);
    }

    public void addKeyCombinationListener(KeyCombinationAdapter adapter)
    {
        this.addKeyListener(adapter);
    }

    public void removeKeyCombinationListener(KeyCombinationAdapter adapter)
    {
        this.removeKeyListener(adapter);
    }

}
