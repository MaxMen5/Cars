package ru.bikchuraev.client.gui;

import ru.bikchuraev.api.editClasses.MakerEdit;
import ru.bikchuraev.api.editClasses.MakerLists;
import ru.bikchuraev.api.editClasses.FullCar;
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

public class EditMakerDialog extends JDialog {

    private static final String TITLEADD = "Добавление производителя";
    private static final String TITLEEDIT = "Редактирование производителя";

    private final JComboBox countries = new JComboBox();
    private final JTextField nameField = new JTextField();
    private final JTextField yearField = new JTextField();

    private JList<FullCar> makerCarList = createCarList();
    private JList<FullCar> allcarList = createCarList();

    private final MakerLists makerList;
    private final MakerEdit prevData;
    private final Consumer<MakerEdit> newMakerConsumer;

    public EditMakerDialog(MakerLists makerList, Consumer<MakerEdit> newMakerConsumer) {
        this(makerList, null, newMakerConsumer);
    }

    public EditMakerDialog(MakerLists makerList, MakerEdit prevData, Consumer<MakerEdit> newMakerConsumer) {
        this.newMakerConsumer = newMakerConsumer;
        this.makerList = makerList;
        this.prevData = prevData;

        if (prevData != null) {
            setTitle(TITLEEDIT);
        } else setTitle(TITLEADD);

        for (int i = 0; i < makerList.getCountry().size(); i++) {
            countries.addItem(makerList.getCountry().get(i).getName());
        }

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel northPanel = new JPanel(new GridLayout(3, 1));

        JPanel namePanel = new JPanel(new BorderLayout());
        JPanel countryPanel = new JPanel(new BorderLayout());
        JPanel yearPanel = new JPanel(new BorderLayout());

        JPanel listPanel = new JPanel(new GridLayout(1, 2, 5, 5));

        listPanel.add(new JScrollPane(allcarList));
        listPanel.add(new JScrollPane(makerCarList));

        DefaultListModel<FullCar> makerCarModel = new DefaultListModel<>();
        if (prevData != null) {
            for (FullCar book : prevData.getCar()) {
                makerCarModel.addElement(book);
            }
        }
        makerCarList.setModel(makerCarModel);

        DefaultListModel<FullCar> allCarModel = new DefaultListModel<>();
        for (FullCar book : makerList.getCar()) {
            allCarModel.addElement(book);
        }
        allcarList.setModel(allCarModel);

        namePanel.add(new JLabel("Производитель:"), BorderLayout.WEST);
        countryPanel.add(new JLabel("Страна:"), BorderLayout.WEST);
        yearPanel.add(new JLabel("Год основания:"), BorderLayout.WEST);

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

        allcarList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
                    FullCar selectedValue = allcarList.getSelectedValue();
                    ((DefaultListModel<FullCar>) allcarList.getModel()).removeElement(selectedValue);
                    ((DefaultListModel<FullCar>) makerCarList.getModel()).addElement(selectedValue);
                    allcarList.revalidate();
                    allcarList.repaint();
                    makerCarList.revalidate();
                    makerCarList.repaint();
                }
            }
        });

        makerCarList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
                    FullCar selectedValue = makerCarList.getSelectedValue();
                    ((DefaultListModel<FullCar>) makerCarList.getModel()).removeElement(selectedValue);
                    ((DefaultListModel<FullCar>) allcarList.getModel()).addElement(selectedValue);
                    allcarList.revalidate();
                    allcarList.repaint();
                    makerCarList.revalidate();
                    makerCarList.repaint();
                }
            }
        });
    }

    private JList<FullCar> createCarList() {
        JList<FullCar> carList = new JList<>();
        carList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (renderer instanceof JLabel && value instanceof FullCar) {
                    JLabel label = (JLabel) renderer;
                    FullCar book = (FullCar) value;
                    label.setText(book.getName());
                }
                return renderer;
            }
        });
        return carList;
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
                        EditMakerDialog.this,
                        "Не все данные введены!",
                        "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!isInteger(yearField.getText())) {
                JOptionPane.showMessageDialog(
                        EditMakerDialog.this,
                        "Введены некорректные данные!",
                        "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Country country = new Country();
            for (int i = 0; i < makerList.getCountry().size(); i++) {
                if (makerList.getCountry().get(i).getName().equals(countries.getSelectedItem())) {
                    country = makerList.getCountry().get(i);
                    break;
                }
            }

            MakerEdit makerEdit = new MakerEdit();
            makerEdit.setName(nameField.getText());
            makerEdit.setCountry(country);
            makerEdit.setYear(Integer.parseInt(yearField.getText()));
            newMakerConsumer.accept(makerEdit);
            dispose();
        }
    }

}
