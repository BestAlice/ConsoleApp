package appFiles;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class TablePanel extends UserPanel{
    private JTable table;


    public TablePanel(SpringLayout layout){
        super(layout);
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
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
        Object data[][] = {};
        table = new JTable(data, columnNames);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(30);
        columnModel.getColumn(2).setPreferredWidth(140);
        columnModel.getColumn(3).setPreferredWidth(160);
        columnModel.getColumn(4).setPreferredWidth(160);
        columnModel.getColumn(5).setPreferredWidth(320);
        columnModel.getColumn(6).setPreferredWidth(120);
        columnModel.getColumn(7).setPreferredWidth(120);
        columnModel.getColumn(8).setPreferredWidth(160);
        JScrollPane tableWithScroll = new JScrollPane(table); //Добавляем на экран именно вот это!!!!!
        table.setFillsViewportHeight(true);
        tableWithScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        panel.add(tableWithScroll);


        layout.putConstraint(SpringLayout.NORTH, tableWithScroll, 0,
                SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, tableWithScroll, 0,
                SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.EAST, tableWithScroll, 0,
                SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.SOUTH, tableWithScroll, 0,
                SpringLayout.SOUTH, panel);
    }
}
