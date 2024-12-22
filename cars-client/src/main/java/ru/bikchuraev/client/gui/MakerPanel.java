package ru.bikchuraev.client.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bikchuraev.api.editClasses.MakerEdit;
import ru.bikchuraev.api.editClasses.MakerFilter;
import ru.bikchuraev.api.editClasses.MakerLists;
import ru.bikchuraev.api.editClasses.FullMaker;
import ru.bikchuraev.api.entity.Country;
import ru.bikchuraev.api.servcie.CarsServerService;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.List;

import static ru.bikchuraev.client.utils.ClientUtils.isInteger;

@Component
public class MakerPanel extends JPanel {

    private final MakerTableModel tableModel = new MakerTableModel();
    private final JTable table = createTable();

    @Autowired
    private CarPanel carPanel;
    @Autowired
    private CarsServerService carsServerService;

    private final MakerLists makerList = new MakerLists();

    private final JTextField filterNameField = new JTextField();
    private final JTextField filterCountryField = new JTextField();
    private final JTextField filterYearField = new JTextField();

    private JButton addButton;
    private JButton editButton;
    private JButton removeButton;

    @PostConstruct
    public void createGUI() {
        setLayout(new BorderLayout());

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(createBookToolBar(), BorderLayout.WEST);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        table.removeColumn(table.getColumnModel().getColumn(0));
        table.removeColumn(table.getColumnModel().getColumn(1));

        refreshTableData();
    }

    private JTable createTable() {
        JTable table = new JTable(tableModel) {
            @Override
            public String getToolTipText(MouseEvent e) {
                Point p = e.getPoint();
                int row = rowAtPoint(p);
                int column = columnAtPoint(p);

                Object value = getValueAt(row, column);
                if (value == null) {
                    return null;
                }

                String strValue = value.toString();
                if (!strValue.isEmpty()) {
                    return "<html>" + strValue.replaceAll(",", "<br>") + "</html>";
                }

                return (String) value;
            }
        };
        return table;
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
        toolBar.add(new JLabel("   Производитель: "));
        toolBar.add(filterNameField);
        filterNameField.setPreferredSize(new Dimension(100, 25));
        toolBar.add(new JLabel("   Страна: "));
        toolBar.add(filterCountryField);
        filterCountryField.setPreferredSize(new Dimension(100, 25));
        toolBar.add(new JLabel("   Год основания: "));
        toolBar.add(filterYearField);
        filterYearField.setPreferredSize(new Dimension(100, 25));
        toolBar.add(new JButton(new FilterAuthorAction()));

        return toolBar;
    }

    public void refreshTableData() {
        boolean isLoggedIn = carsServerService.isLoggedIn();
        addButton.setEnabled(isLoggedIn);
        editButton.setEnabled(isLoggedIn);
        removeButton.setEnabled(isLoggedIn);

        MakerFilter filter = new MakerFilter();
        filter.setName(filterNameField.getText());
        filter.setCountry(filterCountryField.getText());
        filter.setYear(filterYearField.getText());

        List<FullMaker> allAuthors = carsServerService.loadAllMakers(filter);
        tableModel.initWith(allAuthors);
        table.revalidate();
        table.repaint();
    }

    private class AddBookAction extends AbstractAction {
        AddBookAction() {
            putValue(SHORT_DESCRIPTION, "Добавить производителя");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icons/action_add.gif")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            makerList.setCountry(carsServerService.loadAllCountries());
            makerList.setCar(carsServerService.loadAllCars());

            EditMakerDialog editMakerDialog = new EditMakerDialog(makerList, makerEdit -> {
                carsServerService.saveMaker(makerEdit);
                refreshTableData();
                carPanel.refreshTableData();
            });
            editMakerDialog.setLocationRelativeTo(MakerPanel.this);
            editMakerDialog.setVisible(true);
        }
    }

    private class EditBookAction extends AbstractAction {
        EditBookAction() {
            putValue(SHORT_DESCRIPTION, "Изменить производителя");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icons/action_edit.gif")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRowIndex = table.getSelectedRow();
            int rowCount = tableModel.getRowCount();
            if (selectedRowIndex == -1 || selectedRowIndex >= rowCount) {
                JOptionPane.showMessageDialog(
                        MakerPanel.this,
                        "Для редпктирования выберите производителя!",
                        "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Integer selectedMakerId = (Integer) tableModel.getValueAt(selectedRowIndex, 0);

            MakerEdit makerEdit = new MakerEdit();
            makerEdit.setName((String) tableModel.getValueAt(selectedRowIndex, 1));

            Country country = new Country();
            country.setId((Integer) tableModel.getValueAt(selectedRowIndex, 2));
            country.setName((String) tableModel.getValueAt(selectedRowIndex, 3));

            makerEdit.setCountry(country);
            makerEdit.setYear((Integer) tableModel.getValueAt(selectedRowIndex, 4));
            makerEdit.setCar(carsServerService.loadMakerCars(selectedMakerId));

            makerList.setCountry(carsServerService.loadAllCountries());
            makerList.setCar(carsServerService.loadNotAllCars(selectedMakerId));

            EditMakerDialog editMakerDialog = new EditMakerDialog(makerList, makerEdit, changedAuthor -> {
                carsServerService.updateMaker(selectedMakerId, changedAuthor);
                refreshTableData();
                carPanel.refreshTableData();
            });
            editMakerDialog.setLocationRelativeTo(MakerPanel.this);
            editMakerDialog.setVisible(true);
        }
    }

    private class RemoveBookAction extends AbstractAction {
        RemoveBookAction() {
            putValue(SHORT_DESCRIPTION, "Удалить производителя");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icons/action_remove.gif")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRowIndex = table.getSelectedRow();
            int rowCount = tableModel.getRowCount();
            if (selectedRowIndex == -1 || selectedRowIndex >= rowCount) {
                JOptionPane.showMessageDialog(
                        MakerPanel.this,
                        "Для удаления выберите производителя!",
                        "Внимание",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Integer selectedMakerId = (Integer) tableModel.getValueAt(selectedRowIndex, 0);
            String selectedMakerName = (String) tableModel.getValueAt(selectedRowIndex, 1);

            if (JOptionPane.showConfirmDialog(
                    MakerPanel.this,
                    "Удалить производителя '" + selectedMakerName + "'? Все его автомобили будут также удалены!",
                    "Вопрос",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                carsServerService.deleteMakerById(selectedMakerId);
                carsServerService.deleteMakerCars(selectedMakerId);
                refreshTableData();
                carPanel.refreshTableData();
            }
        }
    }

    private class FilterAuthorAction extends AbstractAction {
        FilterAuthorAction() {
            putValue(SHORT_DESCRIPTION, "Фильтровать производителей");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/icons/action_refresh.gif")));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (isInteger(filterYearField.getText())) {
                refreshTableData();
            }
            else {
                JOptionPane.showMessageDialog(
                        MakerPanel.this,
                        "Введены некорректные данные!",
                        "Внимание",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
