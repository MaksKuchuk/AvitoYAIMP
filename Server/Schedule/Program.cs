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
            var tcpSocket=new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

            tcpSocket.Bind(tcpEndPoint);
            tcpSocket.Listen(10);

            while(true)
            {
                var listener=tcpSocket.Accept();
                var buf = new byte[512];
                int size = 0;
                var data=new StringBuilder();
                do
                {
                    size = listener.Receive(buf);
                    data.Append(Encoding.UTF8.GetString(buf, 0, size));
                }
                while (listener.Available > 0);

                var req = JsonConvert.DeserializeObject<Request>(data.ToString());
                var response = new StringBuilder();
                try
                {
                    if (req.type == "getscedule")
                    {
                        response = DataAccess.GetSchedule(req.body);
                    }
                    else if (req.type == "addscedule")
                    {
                        response = DataAccess.AddRowToSchedule(req.body);
                    }
                    else if (req.type == "login")
                    {
                        response = DataAccess.GetProfile(req.body);
                    }
                }
                catch
                {
                    response = new StringBuilder("hueva:(");
                }
                listener.Send(Encoding.UTF8.GetBytes(response.ToString()));
               
                listener.Shutdown(SocketShutdown.Both);
                listener.Close();
            }

        }
    }
}