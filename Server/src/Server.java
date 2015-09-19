import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public class Server {

	public static int port = 4000;
	
    @SuppressWarnings("resource")
    public static void main (String[] args) throws IOException {

    	
        ServerSocket server = null;  
        try {
            server = new ServerSocket(port); 
            //get Ip address of the server
            System.out.println(Inet4Address.getLocalHost().getHostAddress());
            
        } catch (IOException e) {
            System.err.println("Could not start up on: " + "port" + "Maby server is already open? Or a portforwording messup?");
            System.err.println(e);
            System.exit(1);
        }

        Socket client = null;
        while(true) {
            try {
            	//socket accept
                client = server.accept();
                System.out.println("Connected to Client!");
            } catch (IOException e) {
                System.err.println("Connection Refused!!");
                System.err.println(e);
            }

            Thread t = new Thread(new ClientConn(client));
            t.start();
        }
    }
}