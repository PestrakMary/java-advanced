package bsu.labs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

class InputDialog extends JDialog {
    private JButton ok = new JButton("Ok");
   private JLabel name = new JLabel("Name: ");
   private JLabel gender = new JLabel("Gender: ");
   private JLabel sign = new JLabel("Sign: ");
   private JLabel age = new JLabel("Age: ");
   private JLabel partnerSign = new JLabel("Partner's sign: ");

   private JTextField nameField = new JTextField();
   private JComboBox<Person.Gender> genderChooser = new JComboBox<>();
   private JComboBox<Person.Sign> signChooser = new JComboBox<>();
   private JTextField ageField = new JTextField();
   private JComboBox<Person.Sign> partnerSignChooser = new JComboBox<>();

   private String namePattern = "[a-zA-Z]+";
   private String agePattern = "[0-9]+";

    Pattern nameP = Pattern.compile(namePattern);
    Pattern ageP = Pattern.compile(agePattern);


   private Person person;
   InputDialog(JFrame parent, String title){
        super(parent, title, true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        setSize(250, 250);

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(6, 2));

        container.add(name);
        container.add(nameField);

        container.add(gender);
        for(var item : Person.Gender.values()){
        genderChooser.addItem(item);
        }
        genderChooser.setSelectedItem(Person.Gender.female);
        container.add(genderChooser);

        container.add(age);
        container.add(ageField);

        container.add(sign);
        for(var item : Person.Sign.values()){
            signChooser.addItem(item);
        }
        signChooser.setSelectedItem(Person.Sign.Aries);
        container.add(signChooser);

        container.add(partnerSign);
        for(var item : Person.Sign.values()){
            partnerSignChooser.addItem(item);
        }
        partnerSignChooser.setSelectedItem(Person.Sign.Aries);
        container.add(partnerSignChooser);

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(nameP.matcher(nameField.getText()).matches()) {
                    if(ageP.matcher(ageField.getText()).matches()) {
                        person = new Person(nameField.getText(), (Person.Gender) genderChooser.getSelectedItem(),
                                (Person.Sign) signChooser.getSelectedItem(),
                                Integer.parseInt(ageField.getText()), (Person.Sign) partnerSignChooser.getSelectedItem());
                        Main.personsCollection.getCollection().add(person);
                        InputDialog.this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(InputDialog.this, "Age format is wrong!");
                    }
                } else{
                    JOptionPane.showMessageDialog(InputDialog.this, "Name format is wrong!");
                }
            }
        });

        container.add(ok);

        setVisible(true);

    }
    InputDialog(JFrame parent, String title, Person editPerson){
        super(parent, title, true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        setSize(250, 250);

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(6, 2));

        container.add(name);
        nameField.setText(editPerson.getName());
        container.add(nameField);

        container.add(gender);
        for(var item : Person.Gender.values()){
            genderChooser.addItem(item);
        }
        genderChooser.setSelectedItem(editPerson.getGender());
        container.add(genderChooser);

        container.add(age);
        ageField.setText(String.valueOf(editPerson.getAge()));
        container.add(ageField);

        container.add(sign);
        for(var item : Person.Sign.values()){
            signChooser.addItem(item);
        }
        signChooser.setSelectedItem(editPerson.getSign());
        container.add(signChooser);

        container.add(partnerSign);
        for(var item : Person.Sign.values()){
            partnerSignChooser.addItem(item);
        }
        partnerSignChooser.setSelectedItem(editPerson.getPartnerSign());
        container.add(partnerSignChooser);

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(nameP.matcher(nameField.getText()).matches()) {
                    if(ageP.matcher(ageField.getText()).matches()) {
                        editPerson.setName(nameField.getText());
                        editPerson.setGender((Person.Gender) genderChooser.getSelectedItem());
                        editPerson.setSign((Person.Sign) signChooser.getSelectedItem());
                        editPerson.setAge(Integer.parseInt(ageField.getText()));
                        editPerson.setPartnerSign((Person.Sign) partnerSignChooser.getSelectedItem());
                        InputDialog.this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(InputDialog.this, "Age format is wrong!");
                    }
                } else{
                    JOptionPane.showMessageDialog(InputDialog.this, "Name format is wrong!");
                }
            }
        });

        container.add(ok);

        setVisible(true);

    }

}
