import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {
    private InputStream is;
    private OutputStream os;
    private Socket socket;
    private volatile boolean flag;

    private Client() throws IOException {
        this.socket = new Socket("127.0.0.1", 8000);
        this.is = socket.getInputStream();
        this.os = socket.getOutputStream();
        this.flag = true;

    }

    public static void main(String[] args) throws IOException {
        String message;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Chat Id 입력 : ");
        String userName = scanner.nextLine();
        Client client = new Client();
        client.start();


        while (true) {
            String text = scanner.nextLine();
            message = userName + ": " + text;

            if (text.length() == 0)
                continue;

            if (text.equals("exit")) {
                client.os.write(message.getBytes());
                client.disconnect();
                break;
            } else {
                client.os.write(message.getBytes());
            }
        }

    }

    private void disconnect() {
        flag = false;
        try {
            os.close();
            is.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void run() {
        int StringLength;
        byte[] buffer = new byte[1024];
        while (flag) {
            try {
                while ((StringLength = is.read(buffer)) > 0) {
                    System.out.println(new String(buffer, 0, StringLength));
                }
            } catch (IOException e) {
                disconnect();
            }
        }
    }
}