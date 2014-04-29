import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * This class contains server socket that could send echo messages to client.
 */
public class Server {
    /**
     * ServerSocket, that will be used to connect with client.
     */
    private ServerSocket serverSocket;

    public Server(int port){
        initServerSocket(port);
    }

    /**
     *  Starts listening server socket. If receive incoming connection create session.
     */
    public void startServer(){
        try {
            Socket incoming = serverSocket.accept();
            try {
                InputStream inStream = incoming.getInputStream();
                OutputStream outStream = incoming.getOutputStream();

                Scanner in = new Scanner(inStream);
                PrintWriter out = new PrintWriter(outStream, true);

                out.println("Anotheria bootcamp Server. \nEnter BYE to exit.");

                echo(in, out);
            } finally {
                incoming.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get String from Scanner object and put echo string to PrintWriter object
     * @param in Contains string that should be send in echo
     * @param out Contains echo string.
     */
    public void echo(Scanner in, PrintWriter out){
        boolean done = false;

        while (!done && in.hasNextLine()) {
            String line = in.nextLine();
            out.println("Echo: " + line);
            if (line.trim().equals(Constants.SHUTDOWN_MESSAGE))
                done = true;
        }
    }

    /**
     * Create Socket object with current port
     * @param port Port number that ServerSocket will listen.
     */
    public void initServerSocket(int port){
        try{
            serverSocket = new ServerSocket(port);
        } catch (IOException e){
            System.err.println(e);
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException("Unexpected number of arguments");
        }

        final int port = Integer.parseInt(args[0]);

        new Server(port).startServer();

    }

}
