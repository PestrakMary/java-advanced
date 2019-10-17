package bsu.labs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.lang.module.Configuration;
import java.lang.reflect.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;


public class MyFrame extends JFrame {

    Class requestClass;
    ArrayList<Method> methods;
    public Object instance;
    MyJList<Method> methodsList = new MyJList<>(new DefaultListModel<>());
    Locale us = new Locale("EN", "US");
    Locale by = new Locale("BEL", "BY");
    ResourceBundle rb;

    JMenuBar menu;
    JMenu languageMenu;
    JMenu toolsMenu;
    JMenuItem createObj;
    JMenuItem getMethods;
    JMenuItem belItem;
    JMenuItem enItem;
    JLabel classNameL;
    JTextField classNameT;
    JLabel dateLabel;
    JLabel currencyLabel;

    public boolean isObjectExist = false;

    MyFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 560);
        menu = new JMenuBar();
        languageMenu = new JMenu();
        menu.add(languageMenu);
        belItem = new JMenuItem();
        enItem = new JMenuItem();
        languageMenu.add(belItem);
        languageMenu.add(enItem);
        classNameL = new JLabel();
        classNameT = new JTextField();
        getMethods = new JMenuItem();
        createObj = new JMenuItem();
        toolsMenu = new JMenu();
        toolsMenu.add(createObj);
        toolsMenu.add(getMethods);
        menu.add(toolsMenu);

        JPanel mainPanel = new JPanel(new BorderLayout());

        this.getContentPane().setLayout(new BorderLayout());
        JPanel inputPanel  = new JPanel(new BorderLayout());
        inputPanel.add(classNameL, BorderLayout.WEST);
        inputPanel.add(classNameT, BorderLayout.CENTER);
        methodsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dateLabel = new JLabel();
        currencyLabel = new JLabel();
        JPanel informationalPanel = new JPanel(new BorderLayout());
        informationalPanel.add(dateLabel, BorderLayout.NORTH);
        informationalPanel.add(currencyLabel, BorderLayout.SOUTH);
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(methodsList), BorderLayout.CENTER);
        mainPanel.add(informationalPanel, BorderLayout.SOUTH);

        getContentPane().add(menu, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        getMethods.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                        if(isObjectExist) {
                            ArrayList<Method> tempMethods = new ArrayList<>(Arrays.asList(requestClass.getMethods()));
                            methods = new ArrayList<>(tempMethods
                                    .stream()
                                    //delete static methods
                                    .filter((Method m) -> !Modifier.isStatic(m.getModifiers()))
                                    .collect(Collectors.toList()));
                            methodsList.fillJList(methods);
                        }
                    }
        });

        createObj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isObjectExist = false;
                requestClass = findClass(classNameT.getText());
                if (requestClass != null) {
                     new InputDialog(MyFrame.this, rb.getString("createObject"), requestClass);
                }
            }
        });

        methodsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //invoke popup menu with right button
                if (e.getButton() == MouseEvent.BUTTON3) {
                    if(isObjectExist) {
                        new ContextMenu(((JList<Method>) e.getSource()).getSelectedValue(), MyFrame.this).
                                show(MyFrame.this, e.getXOnScreen(), e.getYOnScreen());
                    }
                }
            }
        });

        belItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initI18N(by);
            }
        });

        enItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initI18N(us);
            }
        });

        initI18N(by);
        setVisible(true);

    }

    Class findClass(String className){
        try {
            requestClass = Class.forName(className);
        } catch (ClassNotFoundException ex){
            JOptionPane.showMessageDialog(this,rb.getString("classNotFoundEx"),
                    rb.getString("MessageTitle"),JOptionPane.INFORMATION_MESSAGE);
        }
        return requestClass;
    }

    private void initI18N(Locale locale){
            rb = ResourceBundle.getBundle("text", locale);
            this.setTitle(rb.getString("appName"));
            languageMenu.setText(rb.getString("langButton"));
            toolsMenu.setText(rb.getString("toolsMenu"));
            belItem.setText(rb.getString("languageBe"));
            enItem.setText(rb.getString("languageEn"));
            classNameL.setText(rb.getString("classNameLabel"));
            getMethods.setText(rb.getString("getMethodsButton"));
            createObj.setText(rb.getString("createObject"));
            currencyLabel.setText( NumberFormat.getCurrencyInstance(by).format(208.4)+" = "+'\n'
                    +NumberFormat.getCurrencyInstance(us).
                    format(100));
            dateLabel.setText(DateFormat.getDateInstance(DateFormat.SHORT, locale).format(new Date().getTime()));

    }


}
