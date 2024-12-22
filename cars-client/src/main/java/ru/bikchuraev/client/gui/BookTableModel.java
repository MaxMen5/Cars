package ru.bikchuraev.client.gui;

import ru.bikchuraev.api.editClasses.FullBook;

import javax.swing.table.AbstractTableModel;
import java.util.Arrays;
import java.util.List;

public class BookTableModel extends AbstractTableModel {

    private static final List<String> COLUMNS = Arrays.asList(
            "id", "Название", "Id автора", "Автор", "Год выхода", "Id жанра", "Жанр", "Кол-во страниц"
    );
    private static final List<Class<?>> TYPES = Arrays.asList(
            Integer.class, String.class, Integer.class, String.class, Integer.class, Integer.class, String.class, Integer.class
    );

    private List<FullBook> data;

    public void initWith(List<FullBook> data) {
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
        FullBook book = data.get(rowIndex);

        switch (columnIndex) {
            case 0: return book.getId();
            case 1: return book.getName();
            case 2: return book.getAuthorId();
            case 3: return book.getAuthorName();
            case 4: return book.getYear();
            case 5: return book.getGenreId();
            case 6: return book.getGenreName();
            case 7: return book.getPageCount();
            default: return null;
        }
    }

}
