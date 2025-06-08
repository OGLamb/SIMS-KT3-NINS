package KT3;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GymModel model = new GymModel();
            GymController controller = new GymController(model);
            GymView view = new GymView(controller);
            controller.setView(view);
            view.setVisible(true);
        });
    }
}