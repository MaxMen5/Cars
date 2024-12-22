package ru.bikchuraev.client.gui;

import ru.bikchuraev.api.editClasses.FullAuthor;

import javax.swing.table.AbstractTableModel;
import java.util.Arrays;
import java.util.List;

public class AuthorTableModel extends AbstractTableModel {

    private static final List<String> COLUMNS = Arrays.asList(
            "id", "Имя", "Id страны", "Страна", "Год рождения", "Список книг"
    );
    private static final List<Class<?>> TYPES = Arrays.asList(
            Integer.class, String.class, Integer.class, String.class, Integer.class, String.class
    );

    private List<FullAuthor> data;

    public void initWith(List<FullAuthor> data) {
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
        FullAuthor author = data.get(rowIndex);

        switch (columnIndex) {
            case 0: return author.getId();
            case 1: return author.getName();
            case 2: return author.getCountryId();
            case 3: return author.getCountryName();
            case 4: return author.getBirthYear();
            case 5: return author.getBookList();
            default: return null;
        }
    }

}
