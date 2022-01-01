using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Data;
using System.Data.SqlClient;
using System.Threading.Tasks;
using Dapper;
using Newtonsoft.Json;

namespace Scedule
{
    public static class DataAccess
    {
        const string ConnStr = "YOUR CONNECTION STRING GOES HERE";
        public static StringBuilder GetScedule(string Request)
        {
            List<Scedule> data;
            using (IDbConnection connection = new SqlConnection(ConnStr)) 
            {
                string query = Request;
                data = connection.Query<Scedule>(query).ToList();
            }
            string json=JsonConvert.SerializeObject(data);
            return new StringBuilder(json);
        }
        public static StringBuilder AddRowToScedule(string Request)
        {
            using (IDbConnection connection = new SqlConnection(ConnStr))
            {
                string query = Request;
                connection.Execute(query);
            }
            return new StringBuilder("success");
        }
        public static StringBuilder GetProfile(string Request)
        {
            List<Profile> data;
            using (IDbConnection connection = new SqlConnection(ConnStr))
            {
                string query = Request;
                data = connection.Query<Profile>(query).ToList();
            }
            string json = JsonConvert.SerializeObject(data);
            return new StringBuilder(json);
        }
    }
}
