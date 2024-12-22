package ru.bikchuraev.client.gui;

import org.springframework.stereotype.Component;
import ru.bikchuraev.api.servcie.CarsServerService;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;

import static ru.bikchuraev.client.utils.ClientUtils.prepareWindowSizeWithShifts;

@Component
public final class MainFrame extends JFrame {

    private static final String TITLE = "Cars";

    private final CarsServerService carsServerService;
    private final CarPanel carPanel;
    private final MakerPanel makerPanel;
    private final LogInDialog logInDialog;


    public MainFrame(CarsServerService carsServerService, CarPanel carPanel, LogInDialog logInDialog, MakerPanel makerPanel) {
        this.carsServerService = carsServerService;
        this.carPanel = carPanel;
        this.logInDialog = logInDialog;
        this.makerPanel = makerPanel;
    }

    @PostConstruct
    public void init() {
        setTitle(TITLE);
        createGUI();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(
                        MainFrame.this,
                        "Вы действительно хотите выйти?",
                        "Выйти?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setSize(prepareWindowSizeWithShifts(0, 40));
        setVisible(true);
    }

    private void createGUI() {
        setJMenuBar(createMenuBar());
        getContentPane().add(createTabbedPane(), BorderLayout.CENTER);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JButton authorization = new JButton("Войти");
        menuBar.add(authorization);

        authorization.addActionListener(e -> {
            if (!carsServerService.isLoggedIn()) {
                logInDialog.setLocationRelativeTo(MainFrame.this);
                logInDialog.setVisible(true);
                if (carsServerService.isLoggedIn()) authorization.setText("Выйти");
            } else {
                if (JOptionPane.showConfirmDialog(
                        MainFrame.this,
                        "Вы действительно хотите выйти?",
                        "Вопрос",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    carsServerService.logout();
                    makerPanel.refreshTableData();
                    carPanel.refreshTableData();
                    authorization.setText("Войти");
                }
            }
        });

        return menuBar;
    }

    private JTabbedPane createTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Автомобили", carPanel);
        tabbedPane.addTab("Производители", makerPanel);

        tabbedPane.setSelectedIndex(0);

        return tabbedPane;
    }

}
