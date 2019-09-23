package bsu.labs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class Main extends JFrame {

    static final int IDEAL_AGE_DELTA = 2;
    JFileChooser fileChooser = new JFileChooser("D:\\Универ\\3 курс\\5 семестр\\Java\\wedding\\src\\res");
    File dataFile;
    static Singleton<Person> personsCollection = Singleton.getInstance();
    JList<Person> fileOutputList = new JList<>(new DefaultListModel<>());

    public static void main(String[] args) {
        new Main("Wedding");
    }

    Main(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(650, 560);

        fileOutputList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(fileOutputList);

        JMenuBar toolsBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenu menuEdit = new JMenu("Edit");
        JMenuItem openFile = new JMenuItem("Open file");
        JMenuItem saveFile = new JMenuItem("Save file");
        JMenuItem addPerson = new JMenuItem("Add person");
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        menuFile.add(openFile);
        menuFile.add(saveFile);
        menuEdit.add(addPerson);
        toolsBar.add(menuFile);
        toolsBar.add(menuEdit);
        this.add(toolsBar, BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);

        openFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileChooser.showOpenDialog(Main.this) == JFileChooser.APPROVE_OPTION) {
                    dataFile = fileChooser.getSelectedFile();
                    readFromFile(dataFile);
                    fillOutputList();
                }
            }
        });

        saveFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dataFile != null) {
                    writeToFile(dataFile);
                } else if (fileChooser.showSaveDialog(Main.this) == JFileChooser.APPROVE_OPTION) {
                    writeToFile(fileChooser.getSelectedFile());
                }
            }
        });

        addPerson.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InputDialog(Main.this, "Add person");
                fillOutputList();
            }
        });

        fileOutputList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //invoke popup menu with right button
                if (e.getButton() == MouseEvent.BUTTON3) {
                    new ContextMenu(((JList<Person>) e.getSource()).getSelectedValue(), Main.this).
                            show(Main.this, e.getXOnScreen(), e.getYOnScreen());
                }
            }
        });

        this.setVisible(true);
    }

    void writeToFile(File dataFile) {
        try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            personsCollection.getCollection().stream().
                    forEach((Person person) -> {
                        try {
                            writer.writeObject(person);
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(Main.this, "Write exception!");
                        }
                    });
        } catch (IOException e) {
            JOptionPane.showMessageDialog(Main.this, "Write exception!");
        }
    }

    void readFromFile(File dataFile) {
        personsCollection.getCollection().clear();
        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(dataFile))) {
            Person temp;
            while ((temp = (Person) reader.readObject()) != null) {
                personsCollection.getCollection().add(temp);
            }
        } catch (EOFException ex) {
          //  JOptionPane.showMessageDialog(Main.this, "Read exception!");
        } catch (ClassNotFoundException | IOException ex) {
            JOptionPane.showMessageDialog(Main.this, "Wrong data in file");
        }
    }


    void fillOutputList() {
        DefaultListModel<Person> listModel = (DefaultListModel<Person>) fileOutputList.getModel();
        listModel.clear();
        personsCollection.getCollection().
                stream().
                forEachOrdered((Person person) ->listModel.addElement(person));
    }

}
