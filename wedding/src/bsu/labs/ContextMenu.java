package bsu.labs;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ContextMenu extends JPopupMenu {
    JMenuItem editPerson = new JMenuItem("Edit person");
    JMenuItem deletePerson = new JMenuItem("Delete person");
    JMenuItem pairSelection = new JMenuItem("Pair selection");
    JFrame parent;

    ContextMenu(Person person, JFrame parent){
        this.parent = parent;
        this.add(editPerson);
        this.add(deletePerson);
        this.add(pairSelection);
        editPerson.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InputDialog(ContextMenu.this.parent, "Edit", person);
            }
        });

        deletePerson.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((Main)parent).personsCollection.getCollection().remove(person);
                ((Main)parent).fillOutputList();
            }
        });
        pairSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!((Main)parent).personsCollection.getCollection().isEmpty()) {
                    Person person1 = ((Main)parent).fileOutputList.getSelectedValue();
                    if (person1 != null) {
                        ArrayList<Person> result = (ArrayList<Person>) ((Main)parent).personsCollection.
                                getCollection().
                                stream().
                                filter((Person person2)-> person1.getGender() != person2.getGender()).
                                filter((Person person2) -> Math.abs(person2.getAge() - person1.getAge()) <= Main.IDEAL_AGE_DELTA).
                                filter((Person person2)-> person1.getPartnerSign() == person2.getSign()
                                        && person1.getSign() == person2.getPartnerSign()).collect(Collectors.toList());
                        StringBuffer buffer = new StringBuffer();
                        if(!result.isEmpty()) {
                            result.stream().forEachOrdered((Person person) -> {
                                buffer.append(person);
                                buffer.append('\n');
                            });
                        } else {
                            buffer.append("No available pairs now");
                        }
                        JOptionPane.showMessageDialog(parent,  buffer.toString(), "Ideal pairs", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(parent, "Please choose person for selection pairs!");
                    }
                }
            }
        });
        this.setVisible(true);
    }
}
