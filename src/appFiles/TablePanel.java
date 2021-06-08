package appFiles;

import labwork_class.Difficulty;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class TablePanel extends UserPanel{
    private int filterRow = 10;
    private String value;
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
        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 1 && table.getSelectedRow() != -1) {
                    // your valueChanged overridden method
                    ;
                    Long id = (Long) table.getValueAt(row, 0);
                    HashMap<String, Object> laba = getLabWorkById(id);
                    Application.info_panel.setLabwork(laba);
                }
            }
        });
        table.setAutoCreateRowSorter(true);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
        int columnIndexToSort = table.getColumnModel().getColumnIndex("Creation Date");
        sorter.setComparator(columnIndexToSort, new Comparator<String>()
        {
            @Override
            public int compare(String o1, String o2)
            {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(locale.getString("info.formatter"));
                LocalDateTime ldt1, ldt2;
                ldt1 = LocalDateTime.parse(o1, formatter);
                ldt2 = LocalDateTime.parse(o2, formatter);
                return ldt1.compareTo(ldt2);
            }
        });

        TableRowSorter<TableModel> sorter1 = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
        int columnIndexToSort1 = table.getColumnModel().getColumnIndex("Coordinates");

        sorter1.setComparator(columnIndexToSort1, new Comparator<String>()
        {
            @Override
            public int compare(String o1, String o2)
            {
                int var1 =Arrays.stream(o1.replace("(", "").replace(")", "").split(","))
                        .map(x -> Integer.parseInt(x)).mapToInt(i -> i.intValue()).sum();;
                int var2 =Arrays.stream(o2.replace("(", "").replace(")", "").split(","))
                        .map(x -> Integer.parseInt(x)).mapToInt(i -> i.intValue()).sum();                //int var1 = Arrays.stream(vav1).
                return var1-var2;
            }
        });
        int columnIndexToSort3 = table.getColumnModel().getColumnIndex("Minimal Point");
        sorter.setComparator(columnIndexToSort3, new Comparator<Integer>()
        {
            @Override
            public int compare(Integer o1, Integer o2)
            {/*
                int val1 = Integer.valueOf(o1);
                int val2 = Integer.valueOf(o2);
                return val1-val2;
                */ return o1-o2;
            }
        });

        int columnIndexToSort4 = table.getColumnModel().getColumnIndex("Personal Qualities Maximum");
        sorter.setComparator(columnIndexToSort4, new Comparator<Long>()
        {
            @Override
            public int compare(Long o1, Long o2)
            {
              return (int)(o1-o2);
            }
        });
        int columnIndexToSort5 = table.getColumnModel().getColumnIndex("Practice Hours");
        sorter.setComparator(columnIndexToSort5, new Comparator<Integer>()
        {
            @Override
            public int compare(Integer o1, Integer o2)
            {
                return (int)(o1-o2);
            }
        });
        int columnIndexToSort6 = table.getColumnModel().getColumnIndex("Id");
        sorter.setComparator(columnIndexToSort6, new Comparator<Long>()
        {
            @Override
            public int compare(Long o1, Long o2)
            {
                return (int)(o1-o2);
            }
        });
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

    @Override
    public void updateLocale() {
        locale = Application.rb;
        ArrayList<HashMap> newList = (ArrayList)tableList.clone();
        clearTable();
        for (HashMap map: newList) {
            addRow(map);
        }
        updateTable();
        update();
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
        tableList = new ArrayList<>();
        tableRows = new ArrayList<>();
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(locale.getString("info.formatter"));
        list.add(((LocalDateTime)map.get("creationDate")).format(formatter));
        //list.add(((LocalDateTime)map.get("creationDate")));

        list.add((int)map.get("minimalPoint"));
        list.add((long)map.get("personalQualitiesMaximum"));

        Difficulty dif = (Difficulty) map.get("difficulty");
        if (dif != null) {
            list.add(dif);
        } else {
            list.add("");
        }

        String disName = (String) map.get("disciplineName");
        if (disName != null) {
            list.add(disName);
            list.add((int)map.get("practiceHours"));
        } else {
            list.add("");
            list.add("");
        }
        list.add(map.get("userName"));

        Object[] newRow =  list.toArray();
        tableRows.add(newRow);
    }

    public void updateTable() {
        //clearTable();

            model.setRowCount(0);



        for (int i = 0; i< tableRows.size(); i++){


            if (filterRow != 10 ) {
                if (String.valueOf(tableRows.get(i)[filterRow]).equals(value))
                {model.addRow(tableRows.get(i));}
            } else {
                model.addRow(tableRows.get(i));
            }
        }

        boolean haveId = false;
        ArrayList<Long> usingId = new ArrayList<>();

        for (HashMap map: tableList) {
            usingId.add((Long)map.get("id"));
            if (Application.info_panel.getLabwork() != null){
                Long this_id = (Long)Application.info_panel.getLabwork().get("id");
                Long id = (Long)map.get("id");
                if (this_id.equals(id)){
                    haveId =true;
                    break;
                }
            }
        }
        Application.commandReader.setUsingId(usingId);
        if (!haveId) {
            Application.info_panel.setLabwork(null);
        }

        update();
        Application.update();
    }

    public HashMap<String, Object> getLabWorkById(Long id){
        HashMap<String, Object> laba = new HashMap<>();
        for (HashMap map: tableList) {
            if (map.get("id").equals(id)) {
                laba = map;
                break;
            }
        }
        return laba;
    }

    public int getFilterRow() {
        return filterRow;
    }

    public void setFilterRow(int filterRow) {
        this.filterRow = filterRow;
        updateTable();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
