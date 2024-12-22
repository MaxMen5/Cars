package ru.bikchuraev.client.gui;

import ru.bikchuraev.api.editClasses.CarEdit;
import ru.bikchuraev.api.editClasses.CarLists;
import ru.bikchuraev.api.editClasses.SmallMaker;
import ru.bikchuraev.api.entity.Body;

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

    private final CarLists bookList;
    private final CarEdit prevData;
    private final Consumer<CarEdit> newbookConsumer;

    public EditBookDialog(CarLists bookList, Consumer<CarEdit> newbookConsumer) {
        this(bookList, null, newbookConsumer);
    }

    public EditBookDialog(CarLists bookList, CarEdit prevData, Consumer<CarEdit> newbookConsumer) {
        this.newbookConsumer = newbookConsumer;
        this.bookList = bookList;
        this.prevData = prevData;

        if (prevData != null) {
            setTitle(TITLEEDIT);
        } else setTitle(TITLEADD);

        for (int i = 0; i < bookList.getAuthors().size(); i++) {
            authors.addItem(bookList.getAuthors().get(i).getName());
        }
        for (int i = 0; i < bookList.getBodies().size(); i++) {
            genres.addItem(bookList.getBodies().get(i).getName());
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
            genres.setSelectedItem(prevData.getBody().getName());
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

            SmallMaker author = new SmallMaker();
            for (int i = 0; i < bookList.getAuthors().size(); i++) {
                if (bookList.getAuthors().get(i).getName().equals(authors.getSelectedItem())) {
                    author = bookList.getAuthors().get(i);
                    break;
                }
            }

            Body body = new Body();
            for (int i = 0; i < bookList.getBodies().size(); i++) {
                if (bookList.getBodies().get(i).getName().equals(genres.getSelectedItem())) {
                    body = bookList.getBodies().get(i);
                    break;
                }
            }

            CarEdit carEdit = new CarEdit();
            carEdit.setName(nameField.getText());
            carEdit.setAuthor(author);
            carEdit.setYear(Integer.parseInt(yearField.getText()));
            carEdit.setBody(body);
            carEdit.setPages(Integer.parseInt(pageField.getText()));
            newbookConsumer.accept(carEdit);
            dispose();
        }
    }

}
