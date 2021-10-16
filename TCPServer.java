   import java.io.*;
   import java.net.*;
   import java.util.Base64;

   public class TCPServer {
        private static boolean Exit;
        //Wait for a message to be received.
        private static String Wait(BufferedReader messageReader) throws IOException{
            return messageReader.readLine();
        }

        private static String MessageToUpper(String clientMessage){
            String fromServer; // messages sent to ServerRouter
            fromServer = clientMessage.toUpperCase(); // converting received message to upper case
            System.out.println("Server said: " + fromServer);
            return fromServer;
        }

       public static byte[] DecodeBase64(String fileString){
           byte[] decoded = Base64.getDecoder().decode(fileString);
           return decoded;
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
			         
			// Communication while loop messages are sent in the format (Request code)::(Message)
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
                   // do nothing, go to default case
                }
                switch(request){
                    case 1://string to upper
                        System.out.println("Processing String");
                        System.out.println("Client said: " + parsedMsg[1]);
                        out.println(MessageToUpper(parsedMsg[1]));
                        break;
                    case 2://Files
                        break;
                    case 3://File from string
                        Base64.Decoder mimedc = Base64.getDecoder();
                        InputStream is = mimedc.wrap(Socket.getInputStream());
                        System.out.println("File from String");
                        File newFile = new File("C:\\Users\\Alex\\Desktop\\" + parsedMsg[1]);
                        FileOutputStream fos = new FileOutputStream(newFile);
                        String Encoded = in.readLine();
                        System.out.println("Encoded: " + Encoded);
                        fos.write(DecodeBase64(Encoded));
                        fos.flush();
                        fos.close();
                        System.out.println("Task Completed");
                        break;
                    case 4:
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
