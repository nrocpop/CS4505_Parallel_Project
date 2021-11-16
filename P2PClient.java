import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Base64;
import java.util.Queue;

public class P2PClient extends Thread {
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
    public static void waitForConnect(BufferedReader In) {
    while(In.equals(null)){
    }

    }

    public void connectToClient() {
    }



    private static  PrintWriter out; // writers (for writing back to the machine and to destination)
    private static  BufferedReader in; // reader (for reading from the machine connected to)


    public static void main(String[] args){
        System.out.println("Wait");
        Thread Client = new ClientView();
        Client.start();


}}