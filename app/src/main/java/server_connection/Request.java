package server_connection;

public class Request
{
    public String Type;
    public String Body;

    public Request(String type, String body)
    {
        this.Type = type;
        this.Body = body;
    }
}