import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class P2PRouter {
        public static void main(String[] args) throws IOException {
            Socket clientSocket = null; // socket for the thread
            Object [][] RoutingTable = new Object [100][2]; // routing table
            int SockNum = 5555; // port number
            Boolean Running = true;
            int ind = 0; // index in the routing table

            //Accepting connections
            ServerSocket serverSocket = null; // server socket for accepting connections
            try {
                serverSocket = new ServerSocket(5555);
                System.out.println("ServerRouter is Listening on port: 5555.");
            }
            catch (IOException e) {
                System.err.println("Could not listen on port: 5555.");
                System.exit(1);
            }

            // Creating threads with accepted connections
            while (Running == true)
            {
                try {
                    clientSocket = serverSocket.accept();
                    System.out.println("Connection detected from " + clientSocket.getInetAddress().getHostAddress());
                    SThread t = new SThread(RoutingTable, clientSocket, ind); // creates a thread with a random port
                    t.start(); // starts the thread
                    ind++; // increments the index
                }
                catch (IOException e) {
                    System.err.println("Client/Server failed to connect.");
                    System.exit(1);
                }
            }//end while

            //closing connections
            clientSocket.close();
            serverSocket.close();

        }
    }

