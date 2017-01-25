import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

class ConnectedUser extends Thread {
    private Server server;
    private Socket clientSocket;
    private InputStream is;
    private OutputStream os;
    private volatile boolean flag;


    ConnectedUser(Server server, Socket socket) throws IOException {
        this.server = server;
        this.clientSocket = socket;
        this.is = clientSocket.getInputStream();
        this.os = clientSocket.getOutputStream();
        this.flag = true;
    }

    public void run() {
        int readCount;
        byte[] data = new byte[1024];
        while (flag) {
            try {
                while ((readCount = is.read(data)) > 0) {

                    String message = new String(data, 0, readCount);
                    checkMessage(message);
                }

            } catch (IOException e) {
                System.out.println("READ ERROR");
            }

        }
    }

    private void checkMessage(String message) {
        StringTokenizer st = new StringTokenizer(message, ": ");

        String userName = st.nextToken();
        String text = st.nextToken();

        if (text.equals("exit")) {
            try {
                server.exitUser(this, userName);
                this.join(); //join안하면 exit을 입력하면 인풋스트림이랑 다 close하는데 종료될때까지 기다리지않고 또 run의 흐름을 이어간다
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            server.broadCast(message, this);
        }
    }

    void disconnect() throws IOException {
        flag = false;
        is.close();
        os.close();
        clientSocket.close();
    }

    void sendMessage(String message) {
        try {
            os.write(message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}