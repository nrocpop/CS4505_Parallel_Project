import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Base64;
import java.util.Queue;

public class P2PClient{

    public static void main(String[] args){
        Runnable Client = new ClientView();
        Client.run();


}}