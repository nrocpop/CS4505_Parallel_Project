   import java.io.*;
   import java.net.*;
   import java.util.Base64;
   import java.util.Queue;
   import java.util.LinkedList;
   import java.util.concurrent.TimeUnit;
   import java.util.stream.BaseStream;


   public class TCPClient{

        //checks a queue for messages
        private static void CheckMsg(Queue<String> Messages){
            while(Messages.peek() != null){
                System.out.println("Response: "+ Messages.poll());
            }
        }
       public static String EncodeBase64(File f)throws IOException{
           FileInputStream fis = new FileInputStream(f);
           byte[] fileBytes =new byte[(int)f.length()];
           fis.read(fileBytes);
           String encodedFile = Base64.getEncoder().encodeToString(fileBytes);
           return encodedFile;
       }

       public static String EncodeBase64(byte[] b)throws IOException{
           return Base64.getEncoder().encodeToString(b);

       }



       public static void main(String[] args) throws IOException,InterruptedException {
           // Variables for setting up connection and communication
            boolean exit = false;
            Socket Socket = null; // socket to connect with ServerRouter
            PrintWriter Pout = null; // for writing to ServerRouter
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            BufferedReader Bout = new BufferedReader(new InputStreamReader(System.in));//for taking user input on the client
            BufferedReader in = null; // for reading form ServerRouter
		    InetAddress addr = InetAddress.getLocalHost();
		    String host = addr.getHostAddress(); // Client machine's IP
      	    String routerName = "192.168.50.109"; // ServerRouter host name default:"j263-08.cse1.spsu.edu"
		    int SockNum = 5555; // port number
			
			// Tries to connect to the ServerRouter
         try {
            Socket = new Socket(routerName, SockNum);
            Pout = new PrintWriter(Socket.getOutputStream(), true);
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
         String fromServer; // messages received from ServerRouter
         String fromUser; // messages sent to ServerRouter

		 String address ="192.168.50.109";// destination IP (Server) default:10.5.2.109
         long t0, t1, t; // variables fo timing
			
			// Communication process (initial sends/receives
			Pout.println(address);// initial send (IP of the destination Server)
			fromServer = in.readLine();//initial receive from router (verification of connection)
			System.out.println("ServerRouter: " + fromServer);
			Pout.println(host); // Client sends the IP of its machine as initial send
			t0 = System.currentTimeMillis();

            while(!exit){
                fromServer = null;
                int choice = 999;
                System.out.println("select option:\n1:uppercase string\n2:send file\n3:close connection");
                try{
                    choice = Integer.parseInt(Bout.readLine());}
                catch(NumberFormatException e){
                    System.out.println("NAN");
                }
                switch (choice){
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
                        byte[] partSend = new byte[1024];
                        fromUser = "2::" + Fname1 + "::" + Size;
                        Pout.println(fromUser);
                        String encoded;
                        while(count <= Size){
                            fi.read(partSend);
                            Pout.println(EncodeBase64(partSend));
                            count += 1024l;
                        }
                        fi.close();
                        fromServer = in.readLine();
                        System.out.println(fromServer);
                        break;
                    case 3:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid Selection");
                        break;
                }


            }
      	
			// closing connections
         Pout.close();
         in.close();
         Socket.close();
      }
   }
