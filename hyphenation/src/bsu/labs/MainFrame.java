package bsu.labs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class MainFrame extends JFrame {
    private File dictionary = new File("D:\\Универ\\3 курс\\5 семестр\\Java\\hyphenation\\src\\res\\dictionary.txt");
    private File inputText;
    private Map<String, ArrayList<DictionaryEntry>> dictionaryMap;
    {
        dictionaryMap = new LinkedHashMap<>();
        dictionaryMap = fileToMap(dictionary);

    }
    private List<String> textList = new ArrayList<>();
    private boolean isSaved = false;
    private boolean isFileOpen = false;
    private boolean withUpperCase = false;
    private JFileChooser fileChooser = new JFileChooser("D:\\Универ\\3 курс\\5 семестр\\Java\\hyphenation\\src\\res");
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
        JMenu menuSettings = new JMenu("Settings");
        JMenuItem openFile = new JMenuItem("Open file");
        JMenuItem chooseDictionary = new JMenuItem("Choose dictionary");
        menuSettings.add(chooseDictionary);
        menuFile.add(openFile);
        toolsBar.add(menuFile);
        toolsBar.add(menuSettings);
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

        chooseDictionary.addActionListener(e -> {
            if(fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION){
                dictionary = fileChooser.getSelectedFile();
                dictionaryMap = fileToMap(dictionary);
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
                fixHyphenation(textArea);
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

    Map<String, ArrayList<DictionaryEntry>> fileToMap(File file){
        Map<String, ArrayList<DictionaryEntry>> map = new LinkedHashMap<>();
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String s = "";
            while((s = bufferedReader.readLine())!=null){
                ArrayList<DictionaryEntry> words = new ArrayList<>();
              String[] tempResult =  s.split(" ");
              for(String temp: tempResult){
                  words.add(new DictionaryEntry(temp, temp.indexOf("-")));
              }
                map.put(tempResult[0].replaceAll("-", ""), words);
            }
        } catch (FileNotFoundException ex){
            JOptionPane.showMessageDialog(MainFrame.this, "File doesn't exist!");
            return null;
        } catch (IOException ex){
            JOptionPane.showMessageDialog(MainFrame.this, "Can not read the file");
            return null;
        }
        return map;
    }

    List<String> stringToList(String strArray){
        List<String> list = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(strArray, "., ?!:\n\t\r");
        while(tokenizer.hasMoreTokens()){
            list.add(tokenizer.nextToken());
        }
        return list;
    }

    void fixHyphenation(JTextArea textArea){
        for(String value: textList) {
            withUpperCase = false;
            if(value.contains("-")){
                if(value.matches("[А-Я][а-я-]+")) {
                    withUpperCase = true;
                    value = value.toLowerCase();
                }
            DictionaryEntry key = new DictionaryEntry(value, value.indexOf("-"));
            if (dictionaryMap.containsKey(value.replace("-", ""))) {
                if (!dictionaryMap.get(value.replace("-", "")).contains(key)) {
                    String correct = findBestHyphenation(key);
                    StringBuffer stringBuffer = new StringBuffer("Do you want to fix a ");
                    stringBuffer.append(value);
                    stringBuffer.append(" to ");
                    stringBuffer.append(correct);
                    stringBuffer.append(" ?");
                    int answer = JOptionPane.showConfirmDialog(MainFrame.this, stringBuffer.toString(), "Confirm fix", JOptionPane.YES_NO_OPTION);
                    if (answer == JOptionPane.OK_OPTION) {
                       correctInTextArea(value, correct, textArea);
                    }
                } /*else{
                    continue;
                }*/
            } else {
                StringBuffer stringBuffer = new StringBuffer("The word ");
                stringBuffer.append(value);
                stringBuffer.append(" is not contained in the dictionary. This word will not be corrected!");
                JOptionPane.showMessageDialog(MainFrame.this, stringBuffer);
                continue;
            }
        }
        }
        JOptionPane.showMessageDialog(MainFrame.this, "Fixing completed");
    }

    private String findBestHyphenation(DictionaryEntry key) {
        int delta = Integer.MAX_VALUE;
        String word = "";
        int old = delta;
        for(DictionaryEntry val: dictionaryMap.get(key.getWord().replace("-", ""))){
            delta = Math.min(old, Math.abs(key.getPosition() - val.getPosition()));
            if(old != delta){
                word = val.getWord();
                old = delta;
            }
        }
        return word;
    }

    void correctInTextArea(String oldWord, String newWord, JTextArea textArea){
        String text = textArea.getText();
        if(withUpperCase) {
            textArea.setText(text.replace(oldWord.replace(oldWord.charAt(0), Character.toUpperCase(oldWord.charAt(0))),
                    newWord.replace(newWord.charAt(0), Character.toUpperCase(newWord.charAt(0)))));
            withUpperCase = false;
        } else {
            textArea.setText(text.replace(oldWord, newWord));
        }
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

