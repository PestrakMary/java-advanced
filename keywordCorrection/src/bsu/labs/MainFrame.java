package bsu.labs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class MainFrame extends JFrame {
    private File dictionary = new File("D:\\Универ\\3 курс\\5 семестр\\Java\\keywordCorrection\\src\\res\\dictionary.txt");
    private File inputText;
    private ArrayList<String> dictionaryList;
    {
        dictionaryList = new ArrayList<>();
        dictionaryList = fileToList(dictionary);

    }
    private List<String> textList = new ArrayList<>();
    private boolean isSaved = false;
    private boolean isFileOpen = false;
    private JFileChooser fileChooser = new JFileChooser("D:\\Универ\\3 курс\\5 семестр\\Java\\keywordCorrection\\src\\res");
    private JTextArea textArea = new JTextArea();
    MainFrame(){
        super();
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                if(isFileOpen) {
                    if (isSaved) {
                        dispose();
                    } else {
                        int answer = JOptionPane.showConfirmDialog(MainFrame.this, "Do you want to save changes?");
                        if (answer == JOptionPane.OK_OPTION) {
                            writeToFile(textArea.getText(), inputText);
                            dispose();
                        } else if (answer == JOptionPane.NO_OPTION) {
                            dispose();
                        }
                    }
                } else {
                    int answer = JOptionPane.showConfirmDialog(MainFrame.this, "Do you want to save changes?");
                    if (answer == JOptionPane.OK_OPTION) {
                        if(fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
                            writeToFile(textArea.getText(), fileChooser.getSelectedFile());
                            dispose();
                        }
                    } else if (answer == JOptionPane.NO_OPTION) {
                        dispose();
                    }
                }
            }
        });
        this.setSize(600, 300);
        this.setLayout(new BorderLayout());
        JButton fixButton = new JButton("Check");
        JButton saveButton = new JButton("Save Changes");
        JPanel buttonBar = new JPanel(new BorderLayout());
        buttonBar.add( fixButton, BorderLayout.WEST);
        buttonBar.add(saveButton, BorderLayout.EAST);
        JMenuBar toolsBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenuItem openFile = new JMenuItem("Open file");
        menuFile.add(openFile);
        toolsBar.add(menuFile);
        this.add(toolsBar, BorderLayout.NORTH);
        this.add(buttonBar, BorderLayout.SOUTH);
        this.add(textArea, BorderLayout.CENTER);

        openFile.addActionListener(e -> {
            if(!textArea.getText().equals("")) {
                int answer = JOptionPane.showConfirmDialog(MainFrame.this, "Do you want to save changes?");
                if (answer == JOptionPane.OK_OPTION) {
                    if (isFileOpen) {
                        writeToFile(textArea.getText(), inputText);
                    } else {
                        if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
                            writeToFile(textArea.getText(), fileChooser.getSelectedFile());
                            dispose();
                        }
                    }
                }
            }
            if(fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION){
                isSaved = false;
                inputText = fileChooser.getSelectedFile();
                readFromFile(inputText, textArea);
                isFileOpen = true;
            }
        });


        saveButton.addActionListener(e -> {
            if(isFileOpen) {
                writeToFile(textArea.getText(), inputText);
                isSaved = true;
            } else{
                if(fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
                    writeToFile(textArea.getText(), fileChooser.getSelectedFile());
                }
            }
                }
        );

        fixButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textList = stringToList(textArea.getText());
                fixKeywords(textArea);
                isSaved = false;
            }
        });

        this.setVisible(true);
    }

    void readFromFile(File file, JTextArea textArea){
        try{
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String s = "";
        while((s = bufferedReader.readLine())!= null){
            textArea.append(s);
            textArea.append(String.valueOf('\n'));
        }
            bufferedReader.close();
        } catch (FileNotFoundException ex){
           JOptionPane.showMessageDialog(MainFrame.this, "File doesn't exist!");
        } catch (IOException ex){
            JOptionPane.showMessageDialog(MainFrame.this, "Can not read the file");
        }

    }

    ArrayList<String> fileToList(File file){
        ArrayList<String> list = new ArrayList<>();
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String s = "";
            while((s = bufferedReader.readLine())!=null){
              list.addAll(Arrays.asList(s.split(" ")));
            }
        } catch (FileNotFoundException ex){
            JOptionPane.showMessageDialog(MainFrame.this, "File doesn't exist!");
            return null;
        } catch (IOException ex){
            JOptionPane.showMessageDialog(MainFrame.this, "Can not read the file");
            return null;
        }
        return list;
    }

    List<String> stringToList(String strArray){
        List<String> list = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(strArray, "., ?!:\n\t\r{}()");
        while(tokenizer.hasMoreTokens()){
            list.add(tokenizer.nextToken());
        }
        return list;
    }

    void fixKeywords(JTextArea textArea){
        for(String value: textList) {
            if (dictionaryList.contains(value.toLowerCase())){
                        correctInTextArea(value, value.toLowerCase(), textArea);
            }
        }
        JOptionPane.showMessageDialog(MainFrame.this, "Fixing completed");
    }


    void correctInTextArea(String oldWord, String newWord, JTextArea textArea){
        String text = textArea.getText();
        textArea.setText(text.replace(oldWord, newWord));
    }

    void writeToFile(String text, File file){
        try{
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            printWriter.print(text);
            printWriter.close();
        } catch (IOException ex){
            JOptionPane.showMessageDialog(MainFrame.this, "Can not write changes!");
        }
    }
}

