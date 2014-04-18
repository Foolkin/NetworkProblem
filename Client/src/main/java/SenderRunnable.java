import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * This class send serves to send messages that comes from Scanner to OutPutStream.
 * Implements Runnable.
 * BYE keyword stops the thread.
 */
public class SenderRunnable implements Runnable {
    Socket socket;

    public SenderRunnable(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            OutputStream outputStream = socket.getOutputStream();
            Scanner in = new Scanner(System.in);
            PrintWriter out = new PrintWriter(outputStream, true);
            try {
                boolean done = false;

                while (!done && in.hasNextLine()) {
                    String line = in.nextLine();
                    out.println(line);

                    if (line.trim().equals("BYE")) {
                        done = true;
                    }
                }
            } finally {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
