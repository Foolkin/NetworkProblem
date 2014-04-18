import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * This class serves to receive messages from InputStream and output ot console.
 * BYE Keyword stops the thread .
 */
public class RecivierRunnable implements Runnable {

    Socket socket;

    public RecivierRunnable(Socket socket){
        this.socket = socket;
    }

    public void run(){
        try {
            InputStream inputStream = socket.getInputStream();
            Scanner in = new Scanner(inputStream);
            try {
                boolean done = false;
                while (!done && in.hasNextLine()) {
                    String line = in.nextLine();
                    System.out.println(line);

                    if (line.trim().equals("BYE")) {
                        done = true;
                    }
                }
            } finally {
                inputStream.close();
                in.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
