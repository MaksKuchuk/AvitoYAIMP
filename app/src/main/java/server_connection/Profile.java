package server_connection;

public class Profile
{
    public String Login;
    public String Password;
    public String FirstName;
    public String LastName;
    public String AccType;

    public Profile(String login,String password,String firstname, String lastname, String acctype)
    {
        this.Login = login;
        this.Password = password;
        this.FirstName = firstname;
        this.LastName = lastname;
        this.AccType = acctype;
    }
}
