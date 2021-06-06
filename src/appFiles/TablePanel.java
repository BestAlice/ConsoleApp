package appFiles;

import labwork_class.Difficulty;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class TablePanel extends UserPanel{
    public JTable table;
    public DefaultTableModel model;
    JScrollPane tableWithScroll;
    public ArrayList<HashMap> tableList = new ArrayList<>();
    public ArrayList<Object[]> tableRows = new ArrayList<>();
    public static String[] columnNames= new String[]{
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

    public TablePanel(SpringLayout layout){
        super(layout);
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
    }

    public void createTable(DefaultTableModel model){
        table = new JTable(model){
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int col)
            {
                return false;
                //return (col == 1 && row == 1);
            }
        };
    }

    public void createPanel(){
        //Таблица

        Object data[][] = {{"1", "2", "3", "4", "5", "6", "7" , "8", "9", "10"}};
        model = new DefaultTableModel(data, columnNames);
        createTable(model);
        TableColumnModel columnModel = table.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(30);
        columnModel.getColumn(2).setPreferredWidth(140);
        columnModel.getColumn(3).setPreferredWidth(220);
        columnModel.getColumn(4).setPreferredWidth(160);
        columnModel.getColumn(5).setPreferredWidth(260);
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

    public void addRow(String[] newRow){
        if (newRow.length == 10) {
            Application.table_panel.model.addRow(newRow);
        }
    }

    public void clearTable(){
        model.setRowCount(0);
        update();
        //table.setModel(model);
    }

    public void addRow(HashMap<String, Object> map){
        tableList.add(map);
        createStringLine(map);
    }

    public void createStringLine(HashMap<String, Object> map) {
        ArrayList<Object> list = new ArrayList<>();
        list.add(map.get("id"));
        list.add(map.get("name"));
        list.add(String.format("(%s ,%s)", map.get("x"), map.get("y")));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.YYYY hh:mm:ss");
        list.add(((LocalDateTime)map.get("creationDate")).format(formatter));
        list.add(map.get("minimalPoint"));
        list.add(map.get("personalQualitiesMaximum"));

        Difficulty dif = (Difficulty) map.get("difficulty");
        if (dif != null) {
            list.add(dif);
        } else {
            list.add("");
        }

        String disName = (String) map.get("disciplineName");
        if (disName != null) {
            list.add(disName);
            list.add(map.get("practiceHours"));
        } else {
            list.add("");
            list.add("");
        }
        list.add(map.get("userName"));

        Object[] newRow =  list.toArray();
        tableRows.add(newRow);
    }

    public void updateTable() {
        for (Object[] row: tableRows){
            model.addRow(row);
        }
        update();
        Application.update();
    }
}
