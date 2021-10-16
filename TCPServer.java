   import java.awt.*;
   import java.io.*;
   import java.net.*;
   import java.util.Base64;

   public class TCPServer {
        private static boolean Exit;
        //Wait for a message to be received.
        private static String Wait(BufferedReader messageReader) throws IOException{
            return messageReader.readLine();
        }
        //returns an uppercase version of the provided string
        private static String MessageToUpper(String clientMessage){
            String fromServer; // messages sent to ServerRouter
            fromServer = clientMessage.toUpperCase(); // converting received message to upper case
            System.out.println("Server said: " + fromServer);
            return fromServer;
        }
        //Decodes a Base64 string to a byte array
       public static byte[] DecodeBase64(String fileString){
           return Base64.getDecoder().decode(fileString);
       }

       public static void main(String[] args) throws IOException {
            // Variables for setting up connection and communication
         Socket Socket = null; // socket to connect with ServerRouter
         PrintWriter out = null; // for writing to ServerRouter
         BufferedReader in = null; // for reading form ServerRouter
			InetAddress addr = InetAddress.getLocalHost();
			String host = addr.getHostAddress(); // Server machine's IP
			String routerName = "192.168.50.109"; // ServerRouter host name default:"j263-08.cse1.spsu.edu"
			int SockNum = 5555; // port number

			// Tries to connect to the ServerRouter
         try {
            Socket = new Socket(routerName, SockNum);
            out = new PrintWriter(Socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(Socket.getInputStream()));
         } 
             catch (UnknownHostException e) {
               System.err.println("Don't know about router: " + routerName);
               System.exit(1);
            } 
             catch (IOException e) {
               System.err.println("Couldn't get I/O for the connection to: " + routerName);
               System.exit(1);
            }
				
      	// Variables for message passing
           String fromClient; // messages received from ServerRouter
           String address ="192.168.50.110"; // destination IP (Client)
           String Delimiter = "::";

           // Communication process (initial sends/receives)
			out.println(address);// initial send (IP of the destination Client)
			fromClient = in.readLine();// initial receive from router (verification of connection)
			System.out.println("ServerRouter: " + fromClient);
			         
			// Communication while loop messages are sent in the format [Request code]::[Message].
            // once a message is received, it is separated in to a string array and passed into the
           // switch statement. if the original message is the exit command then the socket is closed.
      	while (!Exit) {
            fromClient = Wait(in);
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
                            partRcv = DecodeBase64(Encoded);
                            fo.write(partRcv);
                            fo.flush();
                            count += 1024l;
                        }
                        fo.close();
                        out.println("Receive Complete");
                        System.out.println("opening File...");
                        Desktop.getDesktop().open(newF);
                        break;
                    case 3:
                        break;
                    default:
                        break;

                }
            }
             // sending the converted message back to the Client via ServerRouter
         }
			
			// closing connections
         out.close();
         in.close();
         Socket.close();
      }
   }
