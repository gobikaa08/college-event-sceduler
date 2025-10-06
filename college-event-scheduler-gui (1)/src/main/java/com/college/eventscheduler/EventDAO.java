package com.college.eventscheduler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {

    public static void addEvent(Event e) throws Exception {
        String sql = "INSERT INTO events(name,date,time,description,organizer) VALUES(?,?,?,?,?)";
        try (Connection conn = Database.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getName());
            ps.setString(2, e.getDate());
            ps.setString(3, e.getTime());
            ps.setString(4, e.getDescription());
            ps.setString(5, e.getOrganizer());
            ps.executeUpdate();
        }
    }

    public static void deleteEvent(int id) throws Exception {
        String sql = "DELETE FROM events WHERE id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public static List<Event> getAllEvents() throws Exception {
        String sql = "SELECT id,name,date,time,description,organizer FROM events ORDER BY date,time";
        List<Event> list = new ArrayList<>();
        try (Connection conn = Database.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Event(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("date"),
                        rs.getString("time"),
                        rs.getString("description"),
                        rs.getString("organizer")
                    ));
                }
            }
        }
        return list;
    }

    public static List<Event> getEventsOnDate(LocalDate date) throws Exception {
        String sql = "SELECT id,name,date,time,description,organizer FROM events WHERE date = ? ORDER BY time";
        List<Event> list = new ArrayList<>();
        try (Connection conn = Database.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, date.toString());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Event(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("date"),
                        rs.getString("time"),
                        rs.getString("description"),
                        rs.getString("organizer")
                    ));
                }
            }
        }
        return list;
    }
}
