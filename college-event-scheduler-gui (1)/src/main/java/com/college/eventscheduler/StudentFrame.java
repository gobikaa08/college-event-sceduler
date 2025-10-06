package com.college.eventscheduler;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class StudentFrame extends JFrame {

    private JPanel calendarPanel;
    private JLabel monthLabel;
    private YearMonth currentMonth;
    private DefaultListModel<String> todayListModel;

    public StudentFrame() {
        setTitle("Student - Events Calendar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        currentMonth = YearMonth.now();
        JPanel main = new JPanel(new BorderLayout());

        JPanel top = new JPanel(new BorderLayout());
        JButton prev = new JButton("<");
        JButton next = new JButton(">");
        monthLabel = new JLabel("", SwingConstants.CENTER);
        top.add(prev, BorderLayout.WEST);
        top.add(monthLabel, BorderLayout.CENTER);
        top.add(next, BorderLayout.EAST);

        calendarPanel = new JPanel(new GridLayout(0,7));
        updateCalendar();

        prev.addActionListener(ae -> {
            currentMonth = currentMonth.minusMonths(1);
            updateCalendar();
        });
        next.addActionListener(ae -> {
            currentMonth = currentMonth.plusMonths(1);
            updateCalendar();
        });

        JPanel right = new JPanel(new BorderLayout());
        right.add(new JLabel("Today's Events"), BorderLayout.NORTH);
        todayListModel = new DefaultListModel<>();
        JList<String> todayList = new JList<>(todayListModel);
        right.add(new JScrollPane(todayList), BorderLayout.CENTER);

        main.add(top, BorderLayout.NORTH);
        main.add(calendarPanel, BorderLayout.CENTER);
        main.add(right, BorderLayout.EAST);

        add(main);
        loadTodayEvents();
        setVisible(true);
    }

    private void updateCalendar() {
        calendarPanel.removeAll();
        monthLabel.setText(currentMonth.getMonth() + " " + currentMonth.getYear());

        // Weekday headers
        String[] headers = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
        for (String h : headers) {
            JButton b = new JButton(h);
            b.setEnabled(false);
            calendarPanel.add(b);
        }

        java.time.LocalDate first = currentMonth.atDay(1);
        int start = first.getDayOfWeek().getValue() % 7; // Sunday=0
        int days = currentMonth.lengthOfMonth();

        for (int i=0;i<start;i++) {
            calendarPanel.add(new JLabel()); // empty
        }
        for (int d=1; d<=days; d++) {
            LocalDate date = currentMonth.atDay(d);
            JButton dayBtn = new JButton(String.valueOf(d));
            // mark if events exist
            try {
                var events = EventDAO.getEventsOnDate(date);
                if (!events.isEmpty()) {
                    dayBtn.setBackground(Color.CYAN);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            dayBtn.addActionListener(ae -> {
                // show dialog with events for that date
                try {
                    java.util.List<Event> evs = EventDAO.getEventsOnDate(date);
                    if (evs.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "No events on " + date.toString());
                    } else {
                        StringBuilder sb = new StringBuilder();
                        for (Event e : evs) {
                            sb.append(e.getName()).append(" ("+e.getTime()+")\n").append(e.getDescription()).append("\nOrganizer: ").append(e.getOrganizer()).append("\n\n");
                        }
                        JOptionPane.showMessageDialog(this, sb.toString(), "Events on " + date.toString(), JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            calendarPanel.add(dayBtn);
        }
        // fill remaining cells to keep grid neat
        int filled = headers.length + start + days;
        int cells = ((filled + 6) / 7) * 7;
        for (int i=filled; i<cells; i++) calendarPanel.add(new JLabel());

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    private void loadTodayEvents() {
        todayListModel.clear();
        try {
            var list = EventDAO.getEventsOnDate(LocalDate.now());
            for (Event e : list) {
                todayListModel.addElement(e.getName() + " ("+e.getTime()+") - " + e.getOrganizer());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
