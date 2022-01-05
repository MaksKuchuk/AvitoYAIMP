package server_connection;

import android.os.Build;
import androidx.annotation.RequiresApi;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Schedule
{
    public String Lesson;
    public String Teacher;
    public String Description;
    public String Room;
    public String Time;

    public Schedule(String lesson, String teacher, String room, String description, String time)
    {
        this.Lesson = lesson;
        this.Teacher = teacher;
        this.Room = room;
        this.Description = description;
        this.Time = time;
    }

    public LocalDateTime GetLDC() {
        return LocalDateTime.parse(this.Time);
    }

    public String GetLDC(DateTimeFormatter format) {
        return LocalDateTime.parse(this.Time).format(format);
    }
}