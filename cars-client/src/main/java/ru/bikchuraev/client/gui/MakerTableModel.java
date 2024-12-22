package ru.bikchuraev.client.gui;

import ru.bikchuraev.api.editClasses.FullMaker;

import javax.swing.table.AbstractTableModel;
import java.util.Arrays;
import java.util.List;

public class MakerTableModel extends AbstractTableModel {

    private static final List<String> COLUMNS = Arrays.asList(
            "id", "Название компании", "Id страны", "Страна", "Год основания", "Список автомобилей"
    );
    private static final List<Class<?>> TYPES = Arrays.asList(
            Integer.class, String.class, Integer.class, String.class, Integer.class, String.class
    );

    private List<FullMaker> data;

    public void initWith(List<FullMaker> data) {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return COLUMNS.get(columnIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return TYPES.get(columnIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        FullMaker maker = data.get(rowIndex);

        switch (columnIndex) {
            case 0: return maker.getId();
            case 1: return maker.getName();
            case 2: return maker.getCountryId();
            case 3: return maker.getCountryName();
            case 4: return maker.getBirthYear();
            case 5: return maker.getCarList();
            default: return null;
        }
    }

}
