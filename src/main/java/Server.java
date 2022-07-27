import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(getPort());
            while (true) {
                try (Socket socket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader((new InputStreamReader(socket.getInputStream())))) {
                    String line;
                    while ((line = in.readLine()) != null) {
                        out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getPort() {
        try (FileReader reader = new FileReader("src\\main\\resources\\settings.json")) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;
            return Integer.parseInt((String) jsonObject.get("port"));
        } catch (IOException | ParseException e) {
            return 0;
        }
    }
}
