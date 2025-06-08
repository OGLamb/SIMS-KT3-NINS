package KT3;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class GymView extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField searchField;
    private JTable resultTable;
    private JButton filterButton, sortButton, saveButton;
    private JSpinner startDateSpinner, endDateSpinner;

    public GymView(GymController controller) {
        setTitle("Teretana - Vlasnik");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel controlPanel = new JPanel(new GridLayout(1, 5));

        startDateSpinner = new JSpinner(new SpinnerDateModel());
        endDateSpinner = new JSpinner(new SpinnerDateModel());
        startDateSpinner.setEditor(new JSpinner.DateEditor(startDateSpinner, "dd.MM.yyyy"));
        endDateSpinner.setEditor(new JSpinner.DateEditor(endDateSpinner, "dd.MM.yyyy"));

        filterButton = new JButton("Filtriraj");
        sortButton = new JButton("Sortiraj");
        saveButton = new JButton("Sačuvaj rezultate");

        searchField = new JTextField();
        searchField.getDocument().addDocumentListener(controller.getSearchListener());

        controlPanel.add(new JLabel("Od:"));
        controlPanel.add(startDateSpinner);
        controlPanel.add(new JLabel("Do:"));
        controlPanel.add(endDateSpinner);
        controlPanel.add(filterButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(controlPanel, BorderLayout.NORTH);
        topPanel.add(searchField, BorderLayout.SOUTH);

        resultTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(resultTable);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(sortButton);
        buttonPanel.add(saveButton);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        filterButton.addActionListener(e -> controller.filterData(
            LocalDate.from(((java.util.Date) startDateSpinner.getValue()).toInstant()),
            LocalDate.from(((java.util.Date) endDateSpinner.getValue()).toInstant())
        ));

        sortButton.addActionListener(e -> controller.sortData());
        saveButton.addActionListener(e -> controller.saveResults());
    }

    public void setTableData(String[][] data, String[] columns) {
        resultTable.setModel(new javax.swing.table.DefaultTableModel(data, columns));
    }

    public String getSearchText() {
        return searchField.getText();
    }

    public String getFilenameInput() {
        return JOptionPane.showInputDialog(this, "Unesite naziv fajla:", "rezultat");
    }
}