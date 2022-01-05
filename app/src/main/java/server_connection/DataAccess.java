package server_connection;

import android.os.Build;
import androidx.annotation.RequiresApi;

import com.google.gson.Gson;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DataAccess {
    private static Schedule[] GetSchedule(Request request) {
        String query = new Gson().toJson(request);
        return new Gson().fromJson(ServerConnection.Query(query), Schedule[].class);
    }
    /** Gets full schedule from data base
     @return Schedule[] if successful or empty Schedule[] otherwise */
    public static Schedule[] GetSchedule() {
        return GetSchedule(new Request("getschedule", "select * from Lessons order by time asc"));
    }
    /** Gets schedule from data base by query
     @return Schedule[] if successful or empty Schedule[] otherwise */
    public static Schedule[] GetSchedule(String query) {
        return GetSchedule(new Request("getschedule", query));
    }

    /** Gets profile from data base
     @return Profile[] if successful or empty Profile[] otherwise */
    public static Profile[] GetProfile(String login, String password) {
        Request request = new Request("getprofiles", "select firstname, lastname, acctype from Profiles where login='" + login + "' and password='" + password + "'");
        String query = new Gson().toJson(request);
        Profile[] profiles = new Gson().fromJson(ServerConnection.Query(query), Profile[].class);
        for (Profile profile : profiles) {
            profile.Login = login;
            profile.Password = password;
        }
        return profiles;
    }

    /** Adds Schedule to data base
     @return String 'successful' or '[]' otherwise */
    public static String AddRowToSchedule(Schedule s) {
        Request request = new Request("addrow", "insert into Lessons (lesson,teacher,room,description,time) values (N'"+s.Lesson+"',N'"+s.Teacher+"',N'"+ s.Room +"',N'"+s.Description+"',N'"+s.Time+"')");
        String query = new Gson().toJson(request);
        return ServerConnection.Query(query);
    }

    /** Adds Profile to data base
     @return String 'successful' or '[]' otherwise */
    public static String AddRowToProfiles(Profile p) {
        Request request = new Request("addrow", "insert into Profiles (login,password,firstname, lastname, acctype) values (N'"+p.Login+"',N'"+p.Password+"',N'"+p.FirstName+"',N'"+p.LastName+"',N'"+p.AccType+"')");
        String query = new Gson().toJson(request);
        return ServerConnection.Query(query);
    }
}