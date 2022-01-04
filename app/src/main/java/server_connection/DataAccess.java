package server_connection;

import android.os.Build;
import androidx.annotation.RequiresApi;

import com.google.gson.Gson;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class DataAccess {
    private static Schedule[] GetSchedule(Request request) {
        String query = new Gson().toJson(request);
        return new Gson().fromJson(ServerConnection.Query(query), Schedule[].class);
    }
    public static Schedule[] GetSchedule() {
        return GetSchedule(new Request("getschedule", "select * from Lessons"));
    }
    public static Schedule[] GetSchedule(String query) {
        return GetSchedule(new Request("getschedule", query));
    }


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

    public static String GetProfile2(String login, String password) {
        Request request = new Request("getprofiles", "select firstname, lastname, acctype from Profiles where login='" + login + "' and password='"+password+"'");
        String query = new Gson().toJson(request);
        return ServerConnection.Query(query);
    }


    public static String AddRowToSchedule(Schedule s) {
        Request request = new Request("addrow", "insert into Lessons (lesson,teacher,room,description,time) values ('"+s.Lesson+"','"+s.Teacher+"','"+ s.Room +"','"+s.Description+"','"+s.Time+"')");
        String query = new Gson().toJson(request);
        return ServerConnection.Query(query);
    }


    public static String AddRowToProfiles(Profile p) {
        Request request = new Request("addrow", "insert into Profiles (login,password,firstname, lastname, acctype) values ('"+p.Login+"','"+p.Password+"','"+p.FirstName+"','"+p.LastName+"','"+p.AccType+"')");
        String query = new Gson().toJson(request);
        return ServerConnection.Query(query);
    }
}