package com.college.eventscheduler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {

    public LoginFrame() {
        setTitle("College Event Scheduler - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel title = new JLabel("Please sign in", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(18f));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        userField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        passField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        userField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton loginBtn = new JButton("Sign in");
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel msg = new JLabel(" ", SwingConstants.CENTER);
        msg.setForeground(Color.RED);
        msg.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(new JLabel("Username:"));
        panel.add(userField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(new JLabel("Password:"));
        panel.add(passField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(loginBtn);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(msg);

        add(panel);

        loginBtn.addActionListener((ActionEvent e) -> {
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword()).trim();
            if (user.equals("admin") && pass.equals("admin")) {
                SwingUtilities.invokeLater(() -> {
                    dispose();
                    new AdminFrame();
                });
            } else if (user.equals("student") && pass.equals("student")) {
                SwingUtilities.invokeLater(() -> {
                    dispose();
                    new StudentFrame();
                });
            } else {
                msg.setText("Bad credentials");
            }
        });

        setVisible(true);
    }
}
