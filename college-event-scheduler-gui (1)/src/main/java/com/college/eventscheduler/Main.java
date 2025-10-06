package com.college.eventscheduler;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Database.init(); // initialize DB
            new LoginFrame();
        });
    }
}
