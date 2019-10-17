package bsu.labs;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MyJList<T> extends JList<T> {
    public MyJList(ListModel<T> model){
        super(model);
    }

    public void fillJList(List<T> data){
        ((DefaultListModel)this.getModel()).clear();
        ((DefaultListModel)this.getModel()).addAll(data);
    }
}
