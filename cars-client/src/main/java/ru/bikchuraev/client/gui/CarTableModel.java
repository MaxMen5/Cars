package ru.bikchuraev.client.gui;

import ru.bikchuraev.api.editClasses.FullCar;

import javax.swing.table.AbstractTableModel;
import java.util.Arrays;
import java.util.List;

public class CarTableModel extends AbstractTableModel {

    private static final List<String> COLUMNS = Arrays.asList(
            "id", "Модель", "Id производителя", "Производитель", "Год выпуска", "Id кузова", "Кузов", "Пробег"
    );
    private static final List<Class<?>> TYPES = Arrays.asList(
            Integer.class, String.class, Integer.class, String.class, Integer.class, Integer.class, String.class, Integer.class
    );

    private List<FullCar> data;

    public void initWith(List<FullCar> data) {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return 8;
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
        FullCar car = data.get(rowIndex);

        switch (columnIndex) {
            case 0: return car.getId();
            case 1: return car.getName();
            case 2: return car.getMakerId();
            case 3: return car.getMakerName();
            case 4: return car.getYear();
            case 5: return car.getBodyId();
            case 6: return car.getBodyName();
            case 7: return car.getMile();
            default: return null;
        }
    }

}
