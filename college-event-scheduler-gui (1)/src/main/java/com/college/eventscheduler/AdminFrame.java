package com.college.eventscheduler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class AdminFrame extends JFrame {

    private DefaultTableModel tableModel;

    public AdminFrame() {
        setTitle("Admin - Manage Events");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel main = new JPanel(new BorderLayout());
        JPanel form = new JPanel();
        form.setLayout(new GridLayout(6,2,5,5));
        JTextField nameField = new JTextField();
        JTextField dateField = new JTextField(); // yyyy-MM-dd
        JTextField timeField = new JTextField(); // HH:mm
        JTextField orgField = new JTextField();
        JTextArea descArea = new JTextArea(3,20);

        form.add(new JLabel("Name:")); form.add(nameField);
        form.add(new JLabel("Date (yyyy-MM-dd):")); form.add(dateField);
        form.add(new JLabel("Time (HH:mm):")); form.add(timeField);
        form.add(new JLabel("Organizer:")); form.add(orgField);
        form.add(new JLabel("Description:")); form.add(new JScrollPane(descArea));

        JButton addBtn = new JButton("Add Event");
        JButton deleteBtn = new JButton("Delete Selected");

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER);
        JPanel btnp = new JPanel();
        btnp.add(addBtn); btnp.add(deleteBtn);
        top.add(btnp, BorderLayout.SOUTH);

        tableModel = new DefaultTableModel(new Object[]{"ID","Name","Date","Time","Organizer"},0);
        JTable table = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(table);

        main.add(top, BorderLayout.NORTH);
        main.add(tableScroll, BorderLayout.CENTER);

        add(main);
        loadEvents();

        addBtn.addActionListener(ae -> {
            String name = nameField.getText().trim();
            String date = dateField.getText().trim();
            String time = timeField.getText().trim();
            String org = orgField.getText().trim();
            String desc = descArea.getText().trim();
            if (name.isEmpty()||date.isEmpty()||time.isEmpty()||org.isEmpty()||desc.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                Event e = new Event(0,name,date,time,desc,org);
                EventDAO.addEvent(e);
                loadEvents();
                nameField.setText(""); dateField.setText(""); timeField.setText(""); orgField.setText(""); descArea.setText(""); 
                JOptionPane.showMessageDialog(this, "Event added");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error adding event: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteBtn.addActionListener(ae -> {
            int row = table.getSelectedRow();
            if (row==-1) {
                JOptionPane.showMessageDialog(this, "Select a row to delete");
                return;
            }
            int id = (int) tableModel.getValueAt(row,0);
            try {
                EventDAO.deleteEvent(id);
                loadEvents();
                JOptionPane.showMessageDialog(this, "Event deleted");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error deleting event: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }

    private void loadEvents() {
        SwingUtilities.invokeLater(() -> {
            try {
                List<Event> list = EventDAO.getAllEvents();
                tableModel.setRowCount(0);
                for (Event e : list) {
                    tableModel.addRow(new Object[]{e.getId(), e.getName(), e.getDate(), e.getTime(), e.getOrganizer()});
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
