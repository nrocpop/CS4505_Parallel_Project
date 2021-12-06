public class ServerLauncher {
    public static void main(String[] args) {
        Thread Serv = new ServerThread();
        Serv.start();
    }
}
