import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


class ClientConn implements Runnable {
    public Socket client;
    ObjectInputStream ois;
    ObjectOutputStream oos;

    ClientConn(Socket client) {
        this.client = client;
        try {
           	//opening Input Stream from client to servers
            ois = new ObjectInputStream(client.getInputStream());
        	//response from server to client
            oos = new ObjectOutputStream(client.getOutputStream());
            
        } catch (IOException e) {
            System.err.println(e);
            return;
        }
    }

    public void run() {
    	String Recv;
    	//reading object casting to string and print in console the client message
        System.out.println("Waiting for device message:");
		try {
            try {
            	//set outputStream passing the string received from client
				Recv = ((String)ois.readObject());
				System.out.println(Recv);
				//generating response string
	            String response = "Message succesfully sended to server";
	            //write and flush on output stream
	            oos.writeObject(response);
	            oos.flush();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}