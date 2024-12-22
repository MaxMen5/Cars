package ru.bikchuraev.client.gui;

import ru.bikchuraev.api.editClasses.AuthorEdit;
import ru.bikchuraev.api.editClasses.AuthorLists;
import ru.bikchuraev.api.editClasses.FullBook;
import ru.bikchuraev.api.entity.Country;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

import static ru.bikchuraev.client.utils.ClientUtils.isBlank;
import static ru.bikchuraev.client.utils.ClientUtils.isInteger;
import static ru.bikchuraev.client.utils.ClientUtils.toStringSafe;

public class EditAuthorDialog extends JDialog {

    private static final String TITLEADD = "Добавление автора";
    private static final String TITLEEDIT = "Редактирование автора";

    private final JComboBox countries = new JComboBox();
    private final JTextField nameField = new JTextField();
    private final JTextField yearField = new JTextField();

    private JList<FullBook> authorBookList = createBookList();
    private JList<FullBook> allBookList = createBookList();

    private final AuthorLists authorList;
    private final AuthorEdit prevData;
    private final Consumer<AuthorEdit> newAuthorConsumer;

    public EditAuthorDialog(AuthorLists authorList, Consumer<AuthorEdit> newAuthorConsumer) {
        this(authorList, null, newAuthorConsumer);
    }

    public EditAuthorDialog(AuthorLists authorList, AuthorEdit prevData, Consumer<AuthorEdit> newAuthorConsumer) {
        this.newAuthorConsumer = newAuthorConsumer;
        this.authorList = authorList;
        this.prevData = prevData;

        if (prevData != null) {
            setTitle(TITLEEDIT);
        } else setTitle(TITLEADD);

        for (int i = 0; i < authorList.getCountry().size(); i++) {
            countries.addItem(authorList.getCountry().get(i).getName());
        }

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel northPanel = new JPanel(new GridLayout(3, 1));

        JPanel namePanel = new JPanel(new BorderLayout());
        JPanel countryPanel = new JPanel(new BorderLayout());
        JPanel yearPanel = new JPanel(new BorderLayout());

        JPanel listPanel = new JPanel(new GridLayout(1, 2, 5, 5));

        listPanel.add(new JScrollPane(allBookList));
        listPanel.add(new JScrollPane(authorBookList));

        DefaultListModel<FullBook> authorBookModel = new DefaultListModel<>();
        if (prevData != null) {
            for (FullBook book : prevData.getBook()) {
                authorBookModel.addElement(book);
            }
        }
        authorBookList.setModel(authorBookModel);

        DefaultListModel<FullBook> allBookModel = new DefaultListModel<>();
        for (FullBook book : authorList.getBook()) {
            allBookModel.addElement(book);
        }
        allBookList.setModel(allBookModel);

        namePanel.add(new JLabel("Имя:"), BorderLayout.WEST);
        countryPanel.add(new JLabel("Страна:"), BorderLayout.WEST);
        yearPanel.add(new JLabel("Год выхода:"), BorderLayout.WEST);

        if (prevData != null) {
            nameField.setText(prevData.getName());
            countries.setSelectedItem(prevData.getCountry().getName());
            yearField.setText(toStringSafe(prevData.getYear()));
        }

        namePanel.add(nameField, BorderLayout.CENTER);
        countryPanel.add(countries, BorderLayout.CENTER);
        yearPanel.add(yearField, BorderLayout.CENTER);

        northPanel.add(namePanel);
        northPanel.add(countryPanel);
        northPanel.add(yearPanel);

        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(listPanel, BorderLayout.CENTER);
        mainPanel.add(new JButton(new SaveAction()), BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        setSize(400, 500);
        setModal(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        allBookList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
                    FullBook selectedValue = allBookList.getSelectedValue();
                    ((DefaultListModel<FullBook>) allBookList.getModel()).removeElement(selectedValue);
                    ((DefaultListModel<FullBook>) authorBookList.getModel()).addElement(selectedValue);
                    allBookList.revalidate();
                    allBookList.repaint();
                    authorBookList.revalidate();
                    authorBookList.repaint();
                }
            }
        });

        authorBookList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
                    FullBook selectedValue = authorBookList.getSelectedValue();
                    ((DefaultListModel<FullBook>) authorBookList.getModel()).removeElement(selectedValue);
                    ((DefaultListModel<FullBook>) allBookList.getModel()).addElement(selectedValue);
                    allBookList.revalidate();
                    allBookList.repaint();
                    authorBookList.revalidate();
                    authorBookList.repaint();
                }
            }
        });
    }

    private JList<FullBook> createBookList() {
        JList<FullBook> bookList = new JList<>();
        bookList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (renderer instanceof JLabel && value instanceof FullBook) {
                    JLabel label = (JLabel) renderer;
                    FullBook book = (FullBook) value;
                    label.setText(book.getName());
                }
                return renderer;
            }
        });
        return bookList;
    }

    private class SaveAction extends AbstractAction {
        SaveAction() {
            putValue(NAME, prevData != null ? "Изменить" : "Добавить");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (isBlank(nameField.getText())
                    || countries.getSelectedItem() == null
                    || isBlank(yearField.getText())) {
                JOptionPane.showMessageDialog(
                        EditAuthorDialog.this,
                        "Не все данные введены!",
                        "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!isInteger(yearField.getText())) {
                JOptionPane.showMessageDialog(
                        EditAuthorDialog.this,
                        "Введены некорректные данные!",
                        "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Country country = new Country();
            for (int i = 0; i < authorList.getCountry().size(); i++) {
                if (authorList.getCountry().get(i).getName().equals(countries.getSelectedItem())) {
                    country = authorList.getCountry().get(i);
                    break;
                }
            }

            AuthorEdit authorEdit = new AuthorEdit();
            authorEdit.setName(nameField.getText());
            authorEdit.setCountry(country);
            authorEdit.setYear(Integer.parseInt(yearField.getText()));
            newAuthorConsumer.accept(authorEdit);
            dispose();
        }
    }

}
