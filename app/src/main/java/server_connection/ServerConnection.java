package server_connection;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.nio.charset.StandardCharsets;
import java.io.*;
import java.net.*;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class ServerConnection {
    private static final String host = "138.201.107.88";
    private static final int port = 1234;

    public static String Query(String query) {
        try (Socket socket = new Socket(host, port)){
            OutputStream output = socket.getOutputStream();
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            output.write(query.getBytes(StandardCharsets.UTF_8));
            String response = reader.readLine();

            socket.close();
            return response;
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
            return "[]"; //"Server Error\n"+ e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return "[]"; //"IO Error\n"+ e.getMessage();
        }
    }
}
