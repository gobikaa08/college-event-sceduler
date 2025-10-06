# College Event Scheduler using GUI Calendar

This is a Java Swing desktop application (Maven) that provides:

- Login (admin/admin, student/student)
- Admin panel to add/delete events
- Student panel with a simple calendar, today's events, and event details
- Events are persisted in `events.db` (SQLite file created in working directory)

## Run locally (with Maven)

Requirements: Java 17+, Maven

1. Build:
```
mvn clean package
```
2. Run:
```
java -jar target/college-event-scheduler-gui-1.0.0-jar-with-dependencies.jar
```

## Run with Docker (headless jar)

1. Build image:
```
docker build -t college-event-scheduler-gui .
```
2. Run container (mount cwd so events.db is persisted to host):
```
docker run -v "%cd%:/app" -w /app -it college-event-scheduler-gui java -jar app.jar
```

> Note: GUI applications typically won't display from Docker containers on Windows without X11/GUI forwarding. For GUI use the local jar.

## Default credentials
- Admin: `admin` / `admin`
- Student: `student` / `student`

