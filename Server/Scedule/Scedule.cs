using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Scedule
{
    public class Scedule
    {
        public Scedule(string lesson, string teacher, string description, int room)
        {
            Lesson = lesson;
            Teacher = teacher;
            Room = room;
            Description = description;
        }
        public string Lesson { get; set; }
        public string Teacher { get; set; }
        public string Description { get; set; }
        public int Room { get; set; }

    }
}
