import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread{

    private static String MessageToUpper(String clientMessage){
        String fromServer; // messages sent to ServerRouter
        fromServer = clientMessage.toUpperCase(); // converting received message to upper case
        System.out.println("Server said: " + fromServer);
        return fromServer;
    }
    //Wait for a message to be received.
    private static String Wait(BufferedReader messageReader) throws IOException{
        return messageReader.readLine();
    }


    public void run() {
        boolean Exit = false;
        String RouterAddress = "127.0.0.1";
        String myAddress = "192.168.50.109";
        long startTime,endTime;//timer variable
        Socket CommSocket = null; // socket to connect with ServerRouter
        PrintWriter out = null; // for writing to ServerRouter
        BufferedReader in = null; // for reading form ServerRouter
        String fromClient; // messages received from ServerRouter
        String Delimiter = "::";
        int ServerPort = 5656;
        //Register with router
        try{
            CommSocket = new Socket(RouterAddress,5555);
            out = new PrintWriter(CommSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(CommSocket.getInputStream()));
            Thread.currentThread().sleep(2000);
            out.println("1::" + myAddress + "::" + ServerPort);

        }
        catch(IOException | InterruptedException e){

        }

        try{
            ServerSocket SSocket = new ServerSocket(ServerPort);
            CommSocket = SSocket.accept();
            out = new PrintWriter(CommSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(CommSocket.getInputStream()));
        }
        catch(IOException e){
        }


        // Communication while loop messages are sent in the format [Request code]::[Message].
        // once a message is received, it is separated in to a string array and passed into the
        // switch statement. if the original message is the exit command then the socket is closed.
        try{while (!Exit) {
            fromClient = Wait(in);
            startTime = System.currentTimeMillis();
            System.out.println("Recieved: " + fromClient);
            String[] parsedMsg = fromClient.split(Delimiter);
            if (fromClient.equals("exit")) {
                Exit=true;
                break;
            }//exit statement
            else{
                int request = 999;//initialization to 999. Do not make a case statement for 999.
                try{
                    request = Integer.parseInt(parsedMsg[0]);
                }catch(NumberFormatException e){
                    //If error handling happens on the server side this should return a message to the client.
                    //otherwise, do nothing and go to default case.
                }
                switch(request){
                    case 1://Returns an uppercase string to the client.

                        System.out.println("Processing String");
                        System.out.println("Client said: " + parsedMsg[1]);
                        out.println(MessageToUpper(parsedMsg[1]));
                        endTime = System.currentTimeMillis();
                        System.out.println("Operation Time: " + (endTime - startTime));
                        break;
                    case 2:
                        // open a file from the client received as [operation code]::[file name]::[file size]
                        //file is received as a series of strings that are the file bytes encoded in Base64.
                        //The strings are decoded and then the bytes are written to a file.
                        File newF = new File("C:\\Users\\Alex\\Desktop\\" + parsedMsg[1]);
                        FileOutputStream fo = new FileOutputStream(newF);
                        String Encoded;
                        long FSize = Long.parseLong(parsedMsg[2]);
                        long count = 0l;
                        byte[] partRcv;
                        while(count <= FSize){
                            Encoded = in.readLine();
                            partRcv = Encoding.DecodeBase64(Encoded);
                            fo.write(partRcv);
                            fo.flush();
                            count += partRcv.length;
                        }
                        fo.close();
                        out.println("Receive Complete");
                        System.out.println("opening File...");
                        Desktop.getDesktop().open(newF);
                        endTime = System.currentTimeMillis();
                        System.out.print("Operation Time: " + (endTime - startTime) + " ");
                        System.out.println("Transfer rate: " + ((FSize/(endTime-startTime))/1000) + "MBps");
                        break;
                    case 3:
                        break;
                    default:
                        break;

                }
            }
            // sending the converted message back to the Client via ServerRouter
        }
        }catch (IOException e){

        }




    }
}
