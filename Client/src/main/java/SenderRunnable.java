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

    /**
     * Socket with server ip address and port number.
     */
    private Socket socket;

    /**
     * Constructor.
     *
     * @param socket Socket with server ip address and port number.
     */
    public SenderRunnable(Socket socket) {
        this.socket = socket;
    }

    /**
     * Get string from console and send this string to server.
     */
    @Override
    public void run() {
        try (
            OutputStream outputStream = socket.getOutputStream();
            Scanner in = new Scanner(System.in);
            PrintWriter out = new PrintWriter(outputStream, true)
        ) {
            boolean done = false;

            while (!done && in.hasNextLine()) {
                String line = in.nextLine();
                out.println(line);

                if (line.trim().equals(Constants.SHUTDOWN_MESSAGE)) {
                    done = true;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("can't send data", e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                // ignored
            }
        }
    }
}
