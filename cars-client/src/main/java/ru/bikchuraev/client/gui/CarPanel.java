package ru.bikchuraev.client.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bikchuraev.api.editClasses.CarEdit;
import ru.bikchuraev.api.editClasses.CarFilter;
import ru.bikchuraev.api.editClasses.CarLists;
import ru.bikchuraev.api.editClasses.FullCar;
import ru.bikchuraev.api.editClasses.SmallMaker;
import ru.bikchuraev.api.entity.Body;
import ru.bikchuraev.api.servcie.CarsServerService;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

import static ru.bikchuraev.client.utils.ClientUtils.isInteger;

@Component
public class CarPanel extends JPanel {

    private final CarTableModel tableModel = new CarTableModel();
    private final JTable table = new JTable(tableModel);

    private final CarLists carLists = new CarLists();

    @Autowired
    private MakerPanel makerPanel;
    @Autowired
    private CarsServerService carsServerService;

    private final JTextField filterNameField = new JTextField();
    private final JTextField filterMakerField = new JTextField();
    private final JTextField filterYearField = new JTextField();
    private final JTextField filterBodyField = new JTextField();
    private final JTextField filterMileField = new JTextField();

    private JButton addButton;
    private JButton editButton;
    private JButton removeButton;

    @PostConstruct
    public void init() {
        carLists.setMakers(carsServerService.loadSmallMakers());
        carLists.setBodies(carsServerService.loadAllBody());

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

        toolBar.add(new JLabel("   Модель: "));
        toolBar.add(filterNameField);
        filterNameField.setPreferredSize(new Dimension(100, 25));
        toolBar.add(new JLabel("   Производитель: "));
        toolBar.add(filterMakerField);
        filterMakerField.setPreferredSize(new Dimension(100, 25));
        toolBar.add(new JLabel("   Год выпуска: "));
        toolBar.add(filterYearField);
        filterYearField.setPreferredSize(new Dimension(100, 25));
        toolBar.add(new JLabel("   Кузов: "));
        toolBar.add(filterBodyField);
        filterBodyField.setPreferredSize(new Dimension(100, 25));
        toolBar.add(new JLabel("   Пробег: "));
        toolBar.add(filterMileField);
        filterMileField.setPreferredSize(new Dimension(100, 25));
        toolBar.add(new JButton(new FilterBookAction()));

        return toolBar;
    }

    public void refreshTableData() {
        boolean isLoggedIn = carsServerService.isLoggedIn();
        addButton.setEnabled(isLoggedIn);
        editButton.setEnabled(isLoggedIn);
        removeButton.setEnabled(isLoggedIn);

        CarFilter carFilter = new CarFilter();
        carFilter.setName(filterNameField.getText());
        carFilter.setMaker(filterMakerField.getText());
        carFilter.setYear(filterYearField.getText());
        carFilter.setBody(filterBodyField.getText());
        carFilter.setMile(filterMileField.getText());

        List<FullCar> allCars = carsServerService.loadAllCars(carFilter);
        tableModel.initWith(allCars);
        table.revalidate();
        table.repaint();
    }

    private class AddBookAction extends AbstractAction {
        AddBookAction() {
            putValue(SHORT_DESCRIPTION, "Добавить автомобиль");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icons/action_add.gif")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            EditCarDialog editCarDialog = new EditCarDialog(carLists, carEdit -> {
                carsServerService.saveCar(carEdit);
                refreshTableData();
                makerPanel.refreshTableData();
            });
            editCarDialog.setLocationRelativeTo(CarPanel.this);
            editCarDialog.setVisible(true);
        }
    }

    private class EditBookAction extends AbstractAction {
        EditBookAction() {
            putValue(SHORT_DESCRIPTION, "Изменить автомобиль");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icons/action_edit.gif")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRowIndex = table.getSelectedRow();
            int rowCount = tableModel.getRowCount();
            if (selectedRowIndex == -1 || selectedRowIndex >= rowCount) {
                JOptionPane.showMessageDialog(
                        CarPanel.this,
                        "Для редактирования выберите автомобиль!",
                        "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Integer selectedCarId = (Integer) tableModel.getValueAt(selectedRowIndex, 0);

            CarEdit carEdit = new CarEdit();
            carEdit.setName((String) tableModel.getValueAt(selectedRowIndex, 1));

            SmallMaker maker = new SmallMaker();
            maker.setId((Integer) tableModel.getValueAt(selectedRowIndex, 2));
            maker.setName((String) tableModel.getValueAt(selectedRowIndex, 3));

            carEdit.setMaker(maker);
            carEdit.setYear((Integer) tableModel.getValueAt(selectedRowIndex, 4));

            Body body = new Body();
            body.setId((Integer) tableModel.getValueAt(selectedRowIndex, 5));
            body.setName((String) tableModel.getValueAt(selectedRowIndex, 6));

            carEdit.setBody(body);
            carEdit.setMile((Integer) tableModel.getValueAt(selectedRowIndex, 7));

            EditCarDialog editCarDialog = new EditCarDialog(carLists, carEdit, changedCar -> {
                carsServerService.updateCar(selectedCarId, changedCar);
                refreshTableData();
                makerPanel.refreshTableData();
            });
            editCarDialog.setLocationRelativeTo(CarPanel.this);
            editCarDialog.setVisible(true);
        }
    }

    private class RemoveBookAction extends AbstractAction {
        RemoveBookAction() {
            putValue(SHORT_DESCRIPTION, "Удалить автомобиль");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icons/action_remove.gif")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRowIndex = table.getSelectedRow();
            int rowCount = tableModel.getRowCount();
            if (selectedRowIndex == -1 || selectedRowIndex >= rowCount) {
                JOptionPane.showMessageDialog(
                        CarPanel.this,
                        "Для удаления выберите автомобиль!",
                        "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Integer selectedCarId = (Integer) tableModel.getValueAt(selectedRowIndex, 0);
            String selectedCarName = (String) tableModel.getValueAt(selectedRowIndex, 1);

            if (JOptionPane.showConfirmDialog(
                    CarPanel.this,
                    "Удалить автомобиль '" + selectedCarName + "'?",
                    "Вопрос",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                carsServerService.deleteCarById(selectedCarId);
                refreshTableData();
                makerPanel.refreshTableData();
            }
        }
    }

    private class FilterBookAction extends AbstractAction {
        FilterBookAction() {
            putValue(SHORT_DESCRIPTION, "Фильтровать автомобили");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icons/action_refresh.gif")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (isInteger(filterYearField.getText()) && isInteger(filterMileField.getText())) {
                refreshTableData();
            }
            else {
                JOptionPane.showMessageDialog(
                        CarPanel.this,
                        "Введены некорректные данные!",
                        "Внимание",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
