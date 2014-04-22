import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class contains socket with which you can connect to server
 */
public class Client {

    /**
     * Socket, that will be used to set connection with server.
     */
    private Socket socket;

    /**
     * Constructor.
     *
     * @param url ip address
     * @param port port
     */
    public Client(String url, int port) {
        initSocket(url, port);
    }

    /**
     * Starts session between client and Server
     * Multithreaded. Uses ExecutorServise.
     */
    public void start() {
        ExecutorService executor = Executors.newCachedThreadPool();

        executor.execute(new SenderRunnable(socket));
        executor.execute(new ReceiverRunnable(socket));

        executor.shutdown();
    }


    /**
     * Create Socket object with current url address and port
     *
     * @param port - int type. Port number which ServerSocket will listen.
     */
    private void initSocket(String url, int port) {
        try {
            socket = new Socket(url, port);
        } catch (IOException e) {
            throw new RuntimeException("Can't initialize socket", e);
        }
    }

    /**
     * Main method. Entry point
     * @param args
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Unexpected number of arguments");
        }

        final String address = args[0];
        final int port = Integer.parseInt(args[1]);

        new Client(address, port).start();
    }
}
