package ru.bikchuraev.client.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bikchuraev.api.editClasses.BookEdit;
import ru.bikchuraev.api.editClasses.BookFilter;
import ru.bikchuraev.api.editClasses.BookLists;
import ru.bikchuraev.api.editClasses.FullBook;
import ru.bikchuraev.api.editClasses.SmallAuthor;
import ru.bikchuraev.api.entity.Genre;
import ru.bikchuraev.api.servcie.LibraryServerService;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

import static ru.bikchuraev.client.utils.ClientUtils.isInteger;

@Component
public class BookPanel extends JPanel {

    private final BookTableModel tableModel = new BookTableModel();
    private final JTable table = new JTable(tableModel);

    private final BookLists bookLists = new BookLists();

    @Autowired
    private AuthorPanel authorPanel;
    @Autowired
    private LibraryServerService libraryServerService;

    private final JTextField filterNameField = new JTextField();
    private final JTextField filterAuthorField = new JTextField();
    private final JTextField filterYearField = new JTextField();
    private final JTextField filterGenreField = new JTextField();
    private final JTextField filterPagesField = new JTextField();

    private JButton addButton;
    private JButton editButton;
    private JButton removeButton;

    @PostConstruct
    public void init() {
        bookLists.setAuthors(libraryServerService.loadSmallAuthors());
        bookLists.setGenres(libraryServerService.loadAllGenres());

        createGUI();
    }

    private void createGUI() {
        setLayout(new BorderLayout());

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(createBookToolBar(), BorderLayout.WEST);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        table.removeColumn(table.getColumnModel().getColumn(0));
        table.removeColumn(table.getColumnModel().getColumn(1));
        table.removeColumn(table.getColumnModel().getColumn(3));

        refreshTableData();
    }

    private JToolBar createBookToolBar() {
        JToolBar toolBar = new JToolBar(SwingConstants.HORIZONTAL);

        toolBar.setFloatable(false);
        addButton = new JButton(new AddBookAction());
        addButton.setEnabled(false);
        toolBar.add(addButton);
        editButton = new JButton(new EditBookAction());
        editButton.setEnabled(false);
        toolBar.add(editButton);
        removeButton = new JButton(new RemoveBookAction());
        removeButton.setEnabled(false);
        toolBar.add(removeButton);

        toolBar.add(new JLabel("   Название: "));
        toolBar.add(filterNameField);
        filterNameField.setPreferredSize(new Dimension(100, 25));
        toolBar.add(new JLabel("   Автор: "));
        toolBar.add(filterAuthorField);
        filterAuthorField.setPreferredSize(new Dimension(100, 25));
        toolBar.add(new JLabel("   Год выхода: "));
        toolBar.add(filterYearField);
        filterYearField.setPreferredSize(new Dimension(100, 25));
        toolBar.add(new JLabel("   Жанр: "));
        toolBar.add(filterGenreField);
        filterGenreField.setPreferredSize(new Dimension(100, 25));
        toolBar.add(new JLabel("   Кол-во страниц: "));
        toolBar.add(filterPagesField);
        filterPagesField.setPreferredSize(new Dimension(100, 25));
        toolBar.add(new JButton(new FilterBookAction()));

        return toolBar;
    }

    public void refreshTableData() {
        boolean isLoggedIn = libraryServerService.isLoggedIn();
        addButton.setEnabled(isLoggedIn);
        editButton.setEnabled(isLoggedIn);
        removeButton.setEnabled(isLoggedIn);

        BookFilter bookFilter = new BookFilter();
        bookFilter.setName(filterNameField.getText());
        bookFilter.setAuthor(filterAuthorField.getText());
        bookFilter.setYear(filterYearField.getText());
        bookFilter.setGenre(filterGenreField.getText());
        bookFilter.setPage(filterPagesField.getText());

        List<FullBook> allBooks = libraryServerService.loadAllBooks(bookFilter);
        tableModel.initWith(allBooks);
        table.revalidate();
        table.repaint();
    }

    private class AddBookAction extends AbstractAction {
        AddBookAction() {
            putValue(SHORT_DESCRIPTION, "Добавить книгу");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icons/action_add.gif")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            EditBookDialog editBookDialog = new EditBookDialog(bookLists, bookEdit -> {
                libraryServerService.saveBook(bookEdit);
                refreshTableData();
                authorPanel.refreshTableData();
            });
            editBookDialog.setLocationRelativeTo(BookPanel.this);
            editBookDialog.setVisible(true);
        }
    }

    private class EditBookAction extends AbstractAction {
        EditBookAction() {
            putValue(SHORT_DESCRIPTION, "Изменить книгу");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icons/action_edit.gif")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRowIndex = table.getSelectedRow();
            int rowCount = tableModel.getRowCount();
            if (selectedRowIndex == -1 || selectedRowIndex >= rowCount) {
                JOptionPane.showMessageDialog(
                        BookPanel.this,
                        "Для редпктирования выберите книгу!",
                        "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Integer selectedBookId = (Integer) tableModel.getValueAt(selectedRowIndex, 0);

            BookEdit bookEdit = new BookEdit();
            bookEdit.setName((String) tableModel.getValueAt(selectedRowIndex, 1));

            SmallAuthor author = new SmallAuthor();
            author.setId((Integer) tableModel.getValueAt(selectedRowIndex, 2));
            author.setName((String) tableModel.getValueAt(selectedRowIndex, 3));

            bookEdit.setAuthor(author);
            bookEdit.setYear((Integer) tableModel.getValueAt(selectedRowIndex, 4));

            Genre genre = new Genre();
            genre.setId((Integer) tableModel.getValueAt(selectedRowIndex, 5));
            genre.setName((String) tableModel.getValueAt(selectedRowIndex, 6));

            bookEdit.setGenre(genre);
            bookEdit.setPages((Integer) tableModel.getValueAt(selectedRowIndex, 7));

            EditBookDialog editBookDialog = new EditBookDialog(bookLists, bookEdit, changedBook -> {
                libraryServerService.updateBook(selectedBookId, changedBook);
                refreshTableData();
                authorPanel.refreshTableData();
            });
            editBookDialog.setLocationRelativeTo(BookPanel.this);
            editBookDialog.setVisible(true);
        }
    }

    private class RemoveBookAction extends AbstractAction {
        RemoveBookAction() {
            putValue(SHORT_DESCRIPTION, "Удалить книгу");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icons/action_remove.gif")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRowIndex = table.getSelectedRow();
            int rowCount = tableModel.getRowCount();
            if (selectedRowIndex == -1 || selectedRowIndex >= rowCount) {
                JOptionPane.showMessageDialog(
                        BookPanel.this,
                        "Для удаления выберите книгу!",
                        "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Integer selectedBookId = (Integer) tableModel.getValueAt(selectedRowIndex, 0);
            String selectedBookName = (String) tableModel.getValueAt(selectedRowIndex, 1);

            if (JOptionPane.showConfirmDialog(
                    BookPanel.this,
                    "Удалить книгу '" + selectedBookName + "'?",
                    "Вопрос",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                libraryServerService.deleteBookById(selectedBookId);
                refreshTableData();
                authorPanel.refreshTableData();
            }
        }
    }

    private class FilterBookAction extends AbstractAction {
        FilterBookAction() {
            putValue(SHORT_DESCRIPTION, "Фильтровать книги");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icons/action_refresh.gif")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (isInteger(filterYearField.getText()) && isInteger(filterPagesField.getText())) {
                refreshTableData();
            }
            else {
                JOptionPane.showMessageDialog(
                        BookPanel.this,
                        "Введены некорректные данные!",
                        "Внимание",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
