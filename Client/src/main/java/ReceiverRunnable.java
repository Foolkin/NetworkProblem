import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * This class serves to receive messages from InputStream and output to console.
 * BYE Keyword stops the thread .
 */
public class ReceiverRunnable implements Runnable {

    /**
     *  Socket with server ip address and port number.
     */
    private Socket socket;

    /**
     * Constructor.
     *
     * @param socket Socket with server ip address and port number.
     */
    public ReceiverRunnable(Socket socket){
        this.socket = socket;
    }

    /**
     * Gets a string from the server and displays the console
     */
    @Override
    public void run(){
        try (
             InputStream inputStream = socket.getInputStream();
             Scanner in = new Scanner(inputStream)
        ) {
            boolean done = false;
            while (!done && in.hasNextLine()) {
                String line = in.nextLine();
                System.out.println(line);

                if (line.trim().equals(Constants.SHUTDOWN_MESSAGE)) {
                    done = true;
                }
            }
        } catch (IOException e){
            throw new RuntimeException("Can't read data", e);
        }  finally {
            try {
                socket.close();
            } catch (IOException e) {
                // ignored
            }
        }
     }
}
