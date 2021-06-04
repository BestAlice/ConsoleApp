package appFiles;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class TablePanel extends UserPanel{
    private JTable table;
    JScrollPane tableWithScroll;

    public TablePanel(SpringLayout layout){
        super(layout);
        //setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
    }

    public void createPanel(){
        //Таблица
        String[] columnNames = {
                "Id",
                "Name",
                "Coordinates",
                "Creation Date",
                "Minimal Point",
                "Personal Qualities Maximum",
                "Difficulty",
                "Discipline",
                "Practice Hours",
                "Autor"};
        Object data[][] = {{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}};
        table = new JTable(data, columnNames){
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int col)
            {
                return false;
                //return (col == 1 && row == 1);
            }
        };;
        TableColumnModel columnModel = table.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(30);
        columnModel.getColumn(2).setPreferredWidth(140);
        columnModel.getColumn(3).setPreferredWidth(160);
        columnModel.getColumn(4).setPreferredWidth(160);
        columnModel.getColumn(5).setPreferredWidth(320);
        columnModel.getColumn(6).setPreferredWidth(120);
        columnModel.getColumn(7).setPreferredWidth(120);
        columnModel.getColumn(8).setPreferredWidth(160);

        tableWithScroll = new JScrollPane(table); //Добавляем на экран именно вот это!!!!!
        table.setFillsViewportHeight(true);
        tableWithScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        add(tableWithScroll);
        update();
        //setMinimumSize(new Dimension(800, 480));




        //tableWithScroll.setPreferredSize(new Dimension(800, 480));
    }

    @Override
    public void setPosition() {
        layout.putConstraint(SpringLayout.NORTH, tableWithScroll, 0,
                SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, tableWithScroll, 0,
                SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, tableWithScroll, 0,
                SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, tableWithScroll, 0,
                SpringLayout.SOUTH, this);
    }

    public JScrollPane getTable() {
        return tableWithScroll;
    }
}
