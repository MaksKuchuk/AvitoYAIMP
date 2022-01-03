using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Data;
using System.Data.SqlClient;
using System.Threading.Tasks;
using Dapper;
using Newtonsoft.Json;

namespace Schedule
{
    public static class DataAccess
    {
        public static StringBuilder GetSchedule(string Request)
        {
            List<Schedule> data;
            using (IDbConnection connection = new SqlConnection(Helper.CnnVal("TestDB"))) 
            {
                string query = Request;
                data = connection.Query<Schedule>(query).ToList();
            }
            string json=JsonConvert.SerializeObject(data);
            return new StringBuilder(json);
        }
        public static StringBuilder AddRowToSchedule(string Request)
        {
            using (IDbConnection connection = new SqlConnection(Helper.CnnVal("TestDB")))
            {
                string query = Request;
                connection.Execute(query);
            }
            return new StringBuilder("success");
        }
        public static StringBuilder GetProfile(string Request)
        {
            List<Profile> data;
            using (IDbConnection connection = new SqlConnection(Helper.CnnVal("TestDB")))
            {
                string query = Request;
                data = connection.Query<Profile>(query).ToList();
            }
            string json = JsonConvert.SerializeObject(data);
            return new StringBuilder(json);
        }
    }
}
