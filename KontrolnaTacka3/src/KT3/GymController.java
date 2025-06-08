package KT3;

import javax.swing.*;
import javax.swing.event.*;
import java.time.LocalDate;
import java.util.List;

public class GymController {
    private GymModel model;
    private GymView view;

    public GymController(GymModel model) {
        this.model = model;
    }

    public void setView(GymView view) {
        this.view = view;
    }

    public void filterData(LocalDate startDate, LocalDate endDate) {
        List<GymVisit> filtered = model.filterByDate(startDate, endDate);
        updateView(filtered);
    }

    public void sortData() {
        List<GymVisit> sorted = model.sortByTrainerVisits();
        updateView(sorted);
    }

    public void saveResults() {
        String filename = view.getFilenameInput();
        if (filename != null && !filename.trim().isEmpty()) {
            model.saveResultsToCSV(filename);
            JOptionPane.showMessageDialog(view, "Rezultati sacuvani u SRC/Rezultati/" + filename + ".csv");
        }
    }

    public DocumentListener getSearchListener() {
        return new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { search(); }
            public void insertUpdate(DocumentEvent e) { search(); }
            public void removeUpdate(DocumentEvent e) { search(); }

            private void search() {
                String searchText = view.getSearchText().toLowerCase();
                List<GymVisit> filtered = model.searchVisits(searchText);
                updateView(filtered);
            }
        };
    }

    private void updateView(List<GymVisit> visits) {
        String[][] data = new String[visits.size()][6];
        for (int i = 0; i < visits.size(); i++) {
            GymVisit visit = visits.get(i);
            data[i] = new String[]{
                visit.getClanId(),
                visit.getClanIme(),
                visit.getTrenerId(),
                visit.getTrenerIme(),
                visit.getVremePrijave(),
                visit.getVremeOdjave()
            };
        }
        String[] columns = {"clan_id", "clan_ime", "trener_id", "trener_ime", "vreme_prijave", "vreme_odjave"};
        view.setTableData(data, columns);
    }
}