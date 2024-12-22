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

public class EditCarDialog extends JDialog {

    private static final String TITLEADD = "Добавление автомобиля";
    private static final String TITLEEDIT = "Редактирование автомобиля";

    private final JComboBox makers = new JComboBox();
    private final JComboBox bodies = new JComboBox();
    private final JTextField nameField = new JTextField();
    private final JTextField yearField = new JTextField();
    private final JTextField mileField = new JTextField();

    private final CarLists carList;
    private final CarEdit prevData;
    private final Consumer<CarEdit> newCarConsumer;

    public EditCarDialog(CarLists carList, Consumer<CarEdit> newCarConsumer) {
        this(carList, null, newCarConsumer);
    }

    public EditCarDialog(CarLists carList, CarEdit prevData, Consumer<CarEdit> newCarConsumer) {
        this.newCarConsumer = newCarConsumer;
        this.carList = carList;
        this.prevData = prevData;

        if (prevData != null) {
            setTitle(TITLEEDIT);
        } else setTitle(TITLEADD);

        for (int i = 0; i < carList.getMakers().size(); i++) {
            makers.addItem(carList.getMakers().get(i).getName());
        }
        for (int i = 0; i < carList.getBodies().size(); i++) {
            bodies.addItem(carList.getBodies().get(i).getName());
        }

        JPanel mainPanel = new JPanel(new GridLayout(6, 1));

        JPanel namePanel = new JPanel(new BorderLayout());
        JPanel makerPanel = new JPanel(new BorderLayout());
        JPanel yearPanel = new JPanel(new BorderLayout());
        JPanel bodyPanel = new JPanel(new BorderLayout());
        JPanel milePanel = new JPanel(new BorderLayout());

        namePanel.add(new JLabel("Модель:"), BorderLayout.WEST);
        makerPanel.add(new JLabel("Производитель:"), BorderLayout.WEST);
        yearPanel.add(new JLabel("Год выпуска:"), BorderLayout.WEST);
        bodyPanel.add(new JLabel("Кузов:"), BorderLayout.WEST);
        milePanel.add(new JLabel("Пробег:"), BorderLayout.WEST);

        if (prevData != null) {
            nameField.setText(prevData.getName());
            makers.setSelectedItem(prevData.getMaker().getName());
            yearField.setText(toStringSafe(prevData.getYear()));
            bodies.setSelectedItem(prevData.getBody().getName());
            mileField.setText(toStringSafe(prevData.getMile()));
        }

        namePanel.add(nameField, BorderLayout.CENTER);
        makerPanel.add(makers, BorderLayout.CENTER);
        yearPanel.add(yearField, BorderLayout.CENTER);
        bodyPanel.add(bodies, BorderLayout.CENTER);
        milePanel.add(mileField, BorderLayout.CENTER);

        mainPanel.add(namePanel);
        mainPanel.add(makerPanel);
        mainPanel.add(yearPanel);
        mainPanel.add(bodyPanel);
        mainPanel.add(milePanel);
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
                    || makers.getSelectedItem() == null
                    || isBlank(yearField.getText())
                    || bodies.getSelectedItem() == null
                    || isBlank(mileField.getText())) {
                JOptionPane.showMessageDialog(
                        EditCarDialog.this,
                        "Не все данные введены!",
                        "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!isInteger(yearField.getText()) || !isInteger(mileField.getText())) {
                JOptionPane.showMessageDialog(
                        EditCarDialog.this,
                        "Введены некорректные данные!",
                        "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            SmallMaker maker = new SmallMaker();
            for (int i = 0; i < carList.getMakers().size(); i++) {
                if (carList.getMakers().get(i).getName().equals(makers.getSelectedItem())) {
                    maker = carList.getMakers().get(i);
                    break;
                }
            }

            Body body = new Body();
            for (int i = 0; i < carList.getBodies().size(); i++) {
                if (carList.getBodies().get(i).getName().equals(bodies.getSelectedItem())) {
                    body = carList.getBodies().get(i);
                    break;
                }
            }

            CarEdit carEdit = new CarEdit();
            carEdit.setName(nameField.getText());
            carEdit.setMaker(maker);
            carEdit.setYear(Integer.parseInt(yearField.getText()));
            carEdit.setBody(body);
            carEdit.setMile(Integer.parseInt(mileField.getText()));
            newCarConsumer.accept(carEdit);
            dispose();
        }
    }

}
