package bsu.labs;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ContextMenu extends JPopupMenu {
    JFrame parent;

    ContextMenu(Method method, JFrame parent){
        this.parent = parent;
        JMenuItem invoke = new JMenuItem(((MyFrame)this.parent).rb.getString("invokeMethodButton"));
        this.add(invoke);
        invoke.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InputDialog((MyFrame) ContextMenu.this.parent, ((MyFrame)ContextMenu.this.parent).rb.getString("invokeMethodButton"), method);
            }
        });

        this.setVisible(true);
    }

    ContextMenu(Constructor constructor, JFrame parent){
        this.parent = parent;
        JMenuItem create = new JMenuItem(((MyFrame)this.parent).rb.getString("createObject"));
        this.add(create);
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InputDialog((MyFrame) ContextMenu.this.parent, ((MyFrame)ContextMenu.this.parent).rb.getString("createObject"), constructor);
            }
        });

        this.setVisible(true);
    }
}

