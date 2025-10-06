package com.college.eventscheduler;

public class Event {
    private int id;
    private String name;
    private String date; // yyyy-MM-dd
    private String time; // HH:mm
    private String description;
    private String organizer;

    public Event() {}

    public Event(int id, String name, String date, String time, String description, String organizer) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.description = description;
        this.organizer = organizer;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getOrganizer() { return organizer; }
    public void setOrganizer(String organizer) { this.organizer = organizer; }
}
