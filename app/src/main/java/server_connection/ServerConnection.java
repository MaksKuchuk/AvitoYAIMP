package server_connection;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.nio.charset.StandardCharsets;
import java.io.*;
import java.net.*;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class ServerConnection {
    private static final String host = "138.201.107.88";
    private static final int port = 1234;

    public static String Query(String query) {
        Log.d("MyApp", "Query started");
        int i = 0;
        try (Socket socket = new Socket(host, port)){
            OutputStream output = socket.getOutputStream();
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            output.write(query.getBytes(StandardCharsets.UTF_8));

            char[] buf = new char[1024*1024];
            StringBuilder response = new StringBuilder();
            int len = 0;
            while ((len = reader.read(buf)) > 0) {
                response.append(buf, 0, len);
            }

            Log.d("MyApp", response.toString());

            if (!response.toString().endsWith("]"))
                response.append("]");

            socket.close();
            return response.toString();
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
            Log.d("MyApp", i + " Server Error\n"+ e.getMessage());
            return "[]";
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("MyApp", i + " IO Error\n"+ e.getMessage());
            return "[]";
        }
    }
}
