package server_connection;

public class Schedule
{
    public String Lesson;
    public String Teacher;
    public String Description;
    public int Room;
    public String Time; // LocalDateTime yyyy-MM-ddThh:mm:ss (just letter T)

    public Schedule (String lesson, String teacher, int room, String description, String time)
    {
        this.Lesson = lesson;
        this.Teacher = teacher;
        this.Room = room;
        this.Description = description;
        this.Time = time;
    }

}