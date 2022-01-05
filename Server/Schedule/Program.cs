using Dapper;
using System.Data;
using System.Data.SqlClient;
using System.Net;
using System.Net.Sockets;
using System.Text;
using Newtonsoft.Json;

namespace Schedule
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Started");
            AppDomain.CurrentDomain.SetData("APP_CONFIG_FILE", "App.config");//Reallocating the config file

            const string ip = "138.201.107.88";
            const int port = 1234;
            var tcpEndPoint = new IPEndPoint(IPAddress.Parse(ip), port);
            var tcpSocket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

            tcpSocket.Bind(tcpEndPoint);
            tcpSocket.Listen(10);

            while (true)
            {
                var listener = tcpSocket.Accept();
                Console.WriteLine("Client connected");

                var buf = new byte[512];
                int size = 0;
                var data = new StringBuilder();
                do
                {
                    size = listener.Receive(buf);
                    data.Append(Encoding.UTF8.GetString(buf, 0, size));
                }
                while (listener.Available > 0);
                Console.WriteLine($"Recieved {data.ToString()}");

                string response = "[]";
                try
                {
                    var req = JsonConvert.DeserializeObject<Request>(data.ToString());

                    if (req.type == "getschedule")
                    {
                        response = DataAccess.GetSchedule(req.body);
                    }
                    else if (req.type == "addrow")
                    {
                        response = DataAccess.AddRow(req.body);
                    }
                    else if (req.type == "getprofiles")
                    {
                        response = DataAccess.GetProfiles(req.body);
                    }
                }
                catch
                {
                    response = "[]";
                }

                listener.Send(Encoding.UTF8.GetBytes(response));
                Console.WriteLine($"Sent {response.Length} bytes");
                listener.Shutdown(SocketShutdown.Both);
                listener.Close();
                Console.WriteLine("Client disconnected");
            }
        }
    }
}