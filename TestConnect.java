import java.io.*;
import java.net.ServerSocket;
import java.net.*;
import java.net.UnknownHostException;
import java.util.Base64;
public class TestConnect extends Thread{

    public static Socket Socket;
    public static boolean  wait = true;
    static PrintWriter Pout = null; // for writing to ServerRouter
    public static void connect()throws IOException,InterruptedException {
        try{
            Socket = new Socket("127.0.0.1",5555);
        }catch(IOException e){
            System.out.println("error: " + e);
        }
    }
    public static void main(String[] args) {
    try{
        connect();
        Pout = new PrintWriter(Socket.getOutputStream(),true);
    }catch (IOException e){
        System.out.println("error: " + e);
    } catch (InterruptedException e){
        System.out.println("error: " + e);
    }


    }
}
