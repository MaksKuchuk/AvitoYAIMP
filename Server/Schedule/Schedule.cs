using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Schedule
{
    public class Schedule
    {
        public Schedule(string lesson, string teacher, int room, string description, DateTime time)
        {
            Lesson = lesson;
            Teacher = teacher;
            Room = room;
            Description = description;
            Time = time;
        }
        public string Lesson { get; set; }
        public string Teacher { get; set; }
        public string Description { get; set; }
        public int Room { get; set; }
        public DateTime Time { get; set; }
    }
}
