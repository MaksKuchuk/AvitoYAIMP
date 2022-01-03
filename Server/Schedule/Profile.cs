using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Schedule
{
    public class Profile
    {
        public Profile(string login, string password, string firstname, string lastname, string acctype)
        {
            Login = login;
            Password = password;
            FirstName = firstname;
            LastName = lastname;
            AccType = acctype;
        }
        public string Login { get; set; }
        public string Password { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public string AccType { get; set; }
    }
}
