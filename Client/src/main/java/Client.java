import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class contains socket whith wich you can connect to server
 */
public class Client {
    Socket socket;

    public Client(String url, int port) {

        initSocket(url, port);
    }

    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 8189);

        client.startClient();
    }

    /**
     * Starts session between client and Server
     * Multithreaded. Uses ExecutorServise.
     */
    public void startClient() {
        ExecutorService executor = Executors.newCachedThreadPool();

        executor.execute(new SenderRunnable(socket));
        executor.execute(new RecivierRunnable(socket));

        executor.shutdown();
    }


    /**
     * Create Socket object with current url address and port
     *
     * @param port - int type. Port number which ServerSocket will listen.
     */
    public void initSocket(String url, int port) {
        try {
            socket = new Socket(url, port);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

}
