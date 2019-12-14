package by.bsu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyFrame extends JFrame {
    MyFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 560);
        JPanel mainPanel = new JPanel(new BorderLayout());
        JLabel labelInstruction = new JLabel("Please type key combination: Alt + a ");
        MyButton c = new MyButton();

        c.addKeyCombinationListener(new KeyCombinationAdapter() {
            @Override
            public void keyCombinationTyped(KeyCombinationEvent e) {
                c.setText("Combination is typed!");
                c.setBackground(new Color(255, 200, 150));
            }
        });

        mainPanel.add(labelInstruction, BorderLayout.NORTH);
        mainPanel.add(c, BorderLayout.CENTER);
        this.add(mainPanel);
        this.setVisible(true);
    }

}
