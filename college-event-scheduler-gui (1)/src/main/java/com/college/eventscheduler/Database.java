package com.college.eventscheduler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Database {
    private static final String URL = "jdbc:sqlite:events.db";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL);
    }

    public static void init() {
        try (Connection conn = getConnection(); Statement st = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS events (" +
                         "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                         "name TEXT NOT NULL," +
                         "date TEXT NOT NULL," +   // ISO yyyy-MM-dd
                         "time TEXT NOT NULL," +   // HH:mm
                         "description TEXT NOT NULL," +
                         "organizer TEXT NOT NULL" +
                         ")";
            st.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
