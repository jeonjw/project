import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {
    private ServerSocket serverSocket;
    private List<ConnectedUser> userList;

    private Server() throws IOException {
        serverSocket = new ServerSocket(8000);
        userList = new ArrayList();
    }

    public static void main(String[] args) {
        Server server;
        try {
            server = new Server();
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void broadCast(String message, ConnectedUser sender) {
        for (ConnectedUser client : userList)
            if (!sender.equals(client)) {
                client.sendMessage(message);
            }
    }


    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                ConnectedUser connectedUser = new ConnectedUser(this, socket);
                connectedUser.start();
                userList.add(connectedUser);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    void exitUser(ConnectedUser user, String userName) throws IOException {
        userList.remove(user);
        String message = userName + "님이 퇴장하였습니다. 남은 유저 :"+ userList.size();
        broadCast(message, user);
        user.disconnect();

    }
}