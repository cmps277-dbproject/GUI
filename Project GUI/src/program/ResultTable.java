package program;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ResultTable {

    JFrame f;
    JTable j;
  
    public ResultTable(String[] columnNames, String[][] data)
    {
        f = new JFrame();
        f.setTitle("Inventory");

        j = new JTable(data, columnNames);
        j.setBounds(30, 40, 200, 300);
  
        // adding it to JScrollPane
        JScrollPane sp = new JScrollPane(j);
        f.add(sp);
        // Frame Size
        f.setSize(500, 200);
        // Frame Visible = true
        f.setVisible(true);
    }

}
