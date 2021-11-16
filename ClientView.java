import java.io.*;
import java.net.Socket;
import java.util.Base64;

public class ClientView extends Thread {
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
    public ClientView(){

    }

    public void run()  {
        try {
            boolean exit = false;
            Socket Socket = null; // socket to connect with ServerRouter
            PrintWriter Pout = null; // for writing to ServerRouter
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            BufferedReader Bout = new BufferedReader(new InputStreamReader(System.in));//for taking user input on the client
            BufferedReader in = null; // for reading form ServerRouter
            String routerName = "192.168.50.109"; // ServerRouter host name default:"j263-08.cse1.spsu.edu"
            int SockNum = 5555; // port number
            String fromServer; // messages received from ServerRouter
            String fromUser; // messages sent to ServerRouter

            while (!exit) {
                System.out.println("hi");
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
                            Pout.println(EncodeBase64(partSend));
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
        }catch (IOException e){
            System.out.println(e);
        }
    }
}
