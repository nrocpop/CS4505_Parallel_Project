import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread{

    public void run() {
        String address = null;
        boolean exit = false;
        boolean connected = false;
        BufferedReader in = null; // for reading form ServerRouter
        PrintWriter Pout = null;
        int peerPort;
        // Variables for message passing
        String fromServer; // messages received from ServerRouter
        String fromUser; // messages sent to ServerRouter
        BufferedReader Bout = new BufferedReader(new InputStreamReader(System.in));//for taking user input on the client
        // Connecting to router
        try {
            Socket RSocket = new Socket("192.168.50.110",5555);
            Pout = new PrintWriter(RSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(RSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(!connected){
            System.out.println("Enter IP Address:");//format xxx.xxx.xxx.xxx including periods.
            try {
                address = Bout.readLine();
                Pout.println("0::" + address);
                connected = true;
                System.out.println("Connecting...");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            int ServerPort = Integer.parseInt(in.readLine());
            System.out.println("Server port recieved: " + ServerPort);
            System.out.println(ServerPort);
            Socket mySocket = new Socket(address, ServerPort);
            Pout = new PrintWriter(mySocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            while (!exit) {
                fromServer = null;
                int choice = 999;
                System.out.println("select option:\n1:uppercase string\n2:send file\n3:close connection");
                try {
                    choice = Integer.parseInt(Bout.readLine());
                } catch (NumberFormatException e) {
                    System.out.println("NAN");
                }
                switch (choice) {
                    case 1:
                        System.out.println("Enter a string: ");
                        fromUser = "1::" + Bout.readLine();
                        Pout.println(fromUser);
                        System.out.println("Waiting for response");
                        System.out.println("Response: " + in.readLine());
                        break;
                    case 2:
                        System.out.println("Please enter file location");
                        String fPath = Bout.readLine();
                        File newF = new File(fPath);
                        FileInputStream fi = new FileInputStream(newF);
                        String Fname1 = newF.getName();
                        long Size = newF.length();
                        long count = 0l;
                        byte[] partSend = new byte[8192];
                        fromUser = "2::" + Fname1 + "::" + Size;
                        Pout.println(fromUser);
                        String encoded;
                        while (count <= Size) {
                            fi.read(partSend);
                            Pout.println(Encoding.EncodeBase64(partSend));
                            count += partSend.length;
                        }
                        fi.close();
                        fromServer = in.readLine();
                        System.out.println(fromServer);
                        break;
                    case 3:
                        exit = true;
                        Pout.println("exit");
                        break;
                    default:
                        System.out.println("Invalid Selection");
                        break;
                }


            }
            // closing connections
            Pout.close();
            in.close();
        }catch(IOException e){

    }



    }
}
