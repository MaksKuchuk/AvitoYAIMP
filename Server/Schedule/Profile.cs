using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace Schedule
{
    public class Profile
    {
        public Profile(string firstname, string lastname, string acctype)
        {
            FirstName = firstname;
            LastName = lastname;
            AccType = acctype;
        }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public string AccType { get; set; }
    }
}
