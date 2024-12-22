package ru.bikchuraev.client.gui;

import ru.bikchuraev.api.editClasses.BookEdit;
import ru.bikchuraev.api.editClasses.BookLists;
import ru.bikchuraev.api.editClasses.SmallAuthor;
import ru.bikchuraev.api.entity.Genre;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

import static ru.bikchuraev.client.utils.ClientUtils.isBlank;
import static ru.bikchuraev.client.utils.ClientUtils.isInteger;
import static ru.bikchuraev.client.utils.ClientUtils.toStringSafe;

public class EditBookDialog extends JDialog {

    private static final String TITLEADD = "Добавление книги";
    private static final String TITLEEDIT = "Редактирование книги";

    private final JComboBox authors = new JComboBox();
    private final JComboBox genres = new JComboBox();
    private final JTextField nameField = new JTextField();
    private final JTextField yearField = new JTextField();
    private final JTextField pageField = new JTextField();

    private final BookLists bookList;
    private final BookEdit prevData;
    private final Consumer<BookEdit> newbookConsumer;

    public EditBookDialog(BookLists bookList, Consumer<BookEdit> newbookConsumer) {
        this(bookList, null, newbookConsumer);
    }

    public EditBookDialog(BookLists bookList, BookEdit prevData, Consumer<BookEdit> newbookConsumer) {
        this.newbookConsumer = newbookConsumer;
        this.bookList = bookList;
        this.prevData = prevData;

        if (prevData != null) {
            setTitle(TITLEEDIT);
        } else setTitle(TITLEADD);

        for (int i = 0; i < bookList.getAuthors().size(); i++) {
            authors.addItem(bookList.getAuthors().get(i).getName());
        }
        for (int i = 0; i < bookList.getGenres().size(); i++) {
            genres.addItem(bookList.getGenres().get(i).getName());
        }

        JPanel mainPanel = new JPanel(new GridLayout(6, 1));

        JPanel namePanel = new JPanel(new BorderLayout());
        JPanel authorPanel = new JPanel(new BorderLayout());
        JPanel yearPanel = new JPanel(new BorderLayout());
        JPanel genrePanel = new JPanel(new BorderLayout());
        JPanel pagePanel = new JPanel(new BorderLayout());

        namePanel.add(new JLabel("Название:"), BorderLayout.WEST);
        authorPanel.add(new JLabel("Автор:"), BorderLayout.WEST);
        yearPanel.add(new JLabel("Год выхода:"), BorderLayout.WEST);
        genrePanel.add(new JLabel("Жанр:"), BorderLayout.WEST);
        pagePanel.add(new JLabel("Кол-во страниц:"), BorderLayout.WEST);

        if (prevData != null) {
            nameField.setText(prevData.getName());
            authors.setSelectedItem(prevData.getAuthor().getName());
            yearField.setText(toStringSafe(prevData.getYear()));
            genres.setSelectedItem(prevData.getGenre().getName());
            pageField.setText(toStringSafe(prevData.getPages()));
        }

        namePanel.add(nameField, BorderLayout.CENTER);
        authorPanel.add(authors, BorderLayout.CENTER);
        yearPanel.add(yearField, BorderLayout.CENTER);
        genrePanel.add(genres, BorderLayout.CENTER);
        pagePanel.add(pageField, BorderLayout.CENTER);

        mainPanel.add(namePanel);
        mainPanel.add(authorPanel);
        mainPanel.add(yearPanel);
        mainPanel.add(genrePanel);
        mainPanel.add(pagePanel);
        mainPanel.add(new JButton(new SaveAction()));

        getContentPane().add(mainPanel);
        setSize(400, 230);
        setModal(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private class SaveAction extends AbstractAction {
        SaveAction() {
            putValue(NAME, prevData != null ? "Изменить" : "Добавить");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (isBlank(nameField.getText())
                    || authors.getSelectedItem() == null
                    || isBlank(yearField.getText())
                    || genres.getSelectedItem() == null
                    || isBlank(pageField.getText())) {
                JOptionPane.showMessageDialog(
                        EditBookDialog.this,
                        "Не все данные введены!",
                        "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!isInteger(yearField.getText()) || !isInteger(pageField.getText())) {
                JOptionPane.showMessageDialog(
                        EditBookDialog.this,
                        "Введены некорректные данные!",
                        "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            SmallAuthor author = new SmallAuthor();
            for (int i = 0; i < bookList.getAuthors().size(); i++) {
                if (bookList.getAuthors().get(i).getName().equals(authors.getSelectedItem())) {
                    author = bookList.getAuthors().get(i);
                    break;
                }
            }

            Genre genre = new Genre();
            for (int i = 0; i < bookList.getGenres().size(); i++) {
                if (bookList.getGenres().get(i).getName().equals(genres.getSelectedItem())) {
                    genre = bookList.getGenres().get(i);
                    break;
                }
            }

            BookEdit bookEdit = new BookEdit();
            bookEdit.setName(nameField.getText());
            bookEdit.setAuthor(author);
            bookEdit.setYear(Integer.parseInt(yearField.getText()));
            bookEdit.setGenre(genre);
            bookEdit.setPages(Integer.parseInt(pageField.getText()));
            newbookConsumer.accept(bookEdit);
            dispose();
        }
    }

}
