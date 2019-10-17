package bsu.labs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

class InputDialog extends JDialog {


    MyFrame parent;

    MyJList<Constructor> constructors = new MyJList<>(new DefaultListModel<>());


    InputDialog(MyFrame parent, String title, Method method){
        super(parent, title, true);
        this.parent = parent;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(250, 250);

        JButton invoke = new JButton(this.parent.rb.getString("invokeMethodButton"));

        JLabel result = new JLabel(this.parent.rb.getString("resultLabel"));
        JTextArea outputResults = new JTextArea();


        Container container = this.getContentPane();
        container.setLayout(new GridLayout(method.getParameterCount()+2, 2));

        outputResults.setEnabled(false);
        for(Class param : method.getParameterTypes()){
            container.add(new JLabel(param.getTypeName()));
            container.add(new JTextArea());
        }
        container.add(result);
        container.add(outputResults);


        invoke.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<Object> args = new ArrayList<>();
                    for(int i = 1; i <= (container.getComponentCount()/2 - 2); i+=2){
                       // Class arg = Class.forName(((JLabel)container.getComponent(i-1)).getText());
                        String temp = ((JLabel)container.getComponent(i-1)).getText();
                        if (temp.equals("int") || temp.equals("java.lang.Integer"))
                            args.add(Integer.parseInt(((JTextArea)container.getComponent(i)).getText()));
                        else if (temp.equals("long") || temp.equals("java.lang.Long"))
                            args.add(Long.parseLong(((JTextArea)container.getComponent(i)).getText()));
                        else if (temp.equals("double") || temp.equals("java.lang.Double"))
                            args.add(Double.parseDouble(((JTextArea)container.getComponent(i)).getText()));
                        else if (temp.equals("float") || temp.equals("java.lang.Float"))
                            args.add(Float.parseFloat(((JTextArea)container.getComponent(i)).getText()));
                        else if (temp.equals("boolean") || temp.equals("java.lang.Boolean"))
                            args.add(Boolean.parseBoolean(((JTextArea)container.getComponent(i)).getText()));
                        else if (temp.equals("byte") || temp.equals("java.lang.Byte"))
                            args.add(Byte.parseByte(((JTextArea)container.getComponent(i)).getText()));
                        else {
                            Class arg = Class.forName(((JLabel)container.getComponent(i-1)).getText());
                            args.add(arg.cast(((JTextArea)container.getComponent(i)).getText()));
                        }
                    }
                    if(args.isEmpty()) {
                        outputResults.setText(method.invoke(parent.instance).toString());
                    } else{
                        outputResults.setText(method.invoke(parent.instance, args.toArray()).toString());
                    }
                } catch ( IllegalAccessException | ClassNotFoundException | InvocationTargetException ex){
                    JOptionPane.showMessageDialog(InputDialog.this,
                            InputDialog.this.parent.rb.getString("exMessage"),
                            InputDialog.this.parent.rb.getString("MessageTitle"),
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        container.add(new JLabel(" "));
        container.add(invoke);

        setVisible(true);

    }

    public InputDialog(MyFrame parent, String title, Class requestClass){
        super(parent, title, true);
        this.parent = parent;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(250, 250);

        constructors.fillJList(Arrays.asList(requestClass.getConstructors()));

        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());
        container.add(constructors);


        constructors.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //invoke popup menu with right button
                if (e.getButton() == MouseEvent.BUTTON3) {
                        new ContextMenu(((JList<Constructor>) e.getSource()).getSelectedValue(), parent).
                                show(InputDialog.this, e.getXOnScreen(), e.getYOnScreen());
                }
            }
        });


        setVisible(true);
    }


    public InputDialog(MyFrame parent, String title, Constructor constructor) {
        super(parent, title, true);
        this.parent = parent;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(250, 250);

        JButton create = new JButton(this.parent.rb.getString("createObject"));

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(constructor.getParameterCount()+1, 2));

        for(Class param : constructor.getParameterTypes()){
            container.add(new JLabel(param.getTypeName()));
            container.add(new JTextArea());
        }


        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Object> args = new ArrayList<>();
                try {
                    for(int i = 1; i <= (container.getComponentCount()/2 - 1); i+=2){
                      //  Class arg = Class.forName(((JLabel)container.getComponent(i-1)).getText());
                        String temp =((JLabel)container.getComponent(i-1)).getText();
                        if (temp.equals("int") || temp.equals("java.lang.Integer"))
                            args.add(Integer.parseInt(((JTextArea)container.getComponent(i)).getText()));
                        else if (temp.equals("long") || temp.equals("java.lang.Long"))
                            args.add(Long.parseLong(((JTextArea)container.getComponent(i)).getText()));
                        else if (temp.equals("double") || temp.equals("java.lang.Double"))
                            args.add(Double.parseDouble(((JTextArea)container.getComponent(i)).getText()));
                        else if (temp.equals("float") || temp.equals("java.lang.Float"))
                            args.add(Float.parseFloat(((JTextArea)container.getComponent(i)).getText()));
                        else if (temp.equals("boolean") || temp.equals("java.lang.Boolean"))
                            args.add(Boolean.parseBoolean(((JTextArea)container.getComponent(i)).getText()));
                        else if (temp.equals("byte") || temp.equals("java.lang.Byte"))
                            args.add(Byte.parseByte(((JTextArea)container.getComponent(i)).getText()));
                        else
                        {
                            Class arg = Class.forName(((JLabel)container.getComponent(i-1)).getText());
                            args.add(arg.cast(((JTextArea)container.getComponent(i)).getText()));
                        }
                    }
                    createObj(constructor, args);
                } catch ( ClassNotFoundException  ex){
                    JOptionPane.showMessageDialog(InputDialog.this,
                            InputDialog.this.parent.rb.getString("exMessage"),
                            InputDialog.this.parent.rb.getString("MessageTitle"),
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        container.add(new JLabel(" "));
        container.add(create);

        setVisible(true);
    }

    private void createObj(Constructor constructor, ArrayList<Object> arguments) {
            try {
                if (arguments.isEmpty()) {
                    parent.instance = constructor.newInstance();
                    parent.isObjectExist = true;
                }
                else {
                    parent.instance = constructor.newInstance(arguments.toArray());
                    parent.isObjectExist = true;
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
}

