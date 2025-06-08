package KT3;

import javax.swing.*;
import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;

public class GymModel {
    private List<GymVisit> visits;

    public GymModel() {
    	this.visits = loadVisitsFromCSV("src/main/resources/podaci.csv");;
    }

    private List<GymVisit> loadVisitsFromCSV(String filename) {
        try {
            return Files.lines(Paths.get(filename))
                .skip(1)
                .map(line -> line.split(","))
                .map(parts -> new GymVisit(
                    parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]))
                .collect(Collectors.toList());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Greška pri učitavanju podataka");
            return new ArrayList<>();
        }
    }

    public List<GymVisit> filterByDate(LocalDate startDate, LocalDate endDate) {
        return visits.stream()
            .filter(visit -> isDateInRange(visit.getVremePrijave(), startDate, endDate))
            .collect(Collectors.toList());
    }

    public List<GymVisit> sortByTrainerVisits() {
        Map<String, Long> trainerCounts = visits.stream()
            .filter(visit -> !visit.getTrenerIme().isEmpty())
            .collect(Collectors.groupingBy(GymVisit::getTrenerIme, Collectors.counting()));

        return visits.stream()
            .sorted((v1, v2) -> Long.compare(
                trainerCounts.getOrDefault(v2.getTrenerIme(), 0L),
                trainerCounts.getOrDefault(v1.getTrenerIme(), 0L)
            ))
            .collect(Collectors.toList());
    }

    public List<GymVisit> searchVisits(String searchText) {
        return visits.stream()
            .filter(visit -> visit.toString().toLowerCase().contains(searchText))
            .collect(Collectors.toList());
    }

    public void saveResultsToCSV(String filename) {
        try {
            Path dir = Paths.get("SRC/Rezultati");
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }

            Path filePath = dir.resolve(filename + ".csv");
            try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
                writer.write("clan_id,clan_ime,trener_id,trener_ime,vreme_prijave,vreme_odjave\n");
                for (GymVisit visit : visits) {
                    writer.write(String.join(",", 
                        visit.getClanId(), visit.getClanIme(), visit.getTrenerId(),
                        visit.getTrenerIme(), visit.getVremePrijave(), visit.getVremeOdjave()) + "\n");
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Greška pri čuvanju podataka");
        }
    }

    private boolean isDateInRange(String dateTime, LocalDate start, LocalDate end) {
        LocalDate date = LocalDate.parse(dateTime.substring(0, 10));
        return !date.isBefore(start) && !date.isAfter(end);
    }
}