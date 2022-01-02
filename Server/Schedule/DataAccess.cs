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
        public static string GetSchedule(string query)
        {
            List<Schedule> data;
            using (IDbConnection connection = new SqlConnection(Helper.CnnVal("TestDB")))
            {
                data = connection.Query<Schedule>(query).ToList();
            }
            string json = JsonConvert.SerializeObject(data);
            return json;
        }
        public static string AddRow(string query)
        {
            using (IDbConnection connection = new SqlConnection(Helper.CnnVal("TestDB")))
            {
                connection.Execute(query);
            }
            return "success";
        }
        public static string GetProfiles(string query)
        {
            List<Profile> data;
            using (IDbConnection connection = new SqlConnection(Helper.CnnVal("TestDB")))
            {
                data = connection.Query<Profile>(query).ToList();
            }
            string json = JsonConvert.SerializeObject(data);
            return json;
        }
    }
}
