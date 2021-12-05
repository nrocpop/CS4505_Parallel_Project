public class ClientLauncher {
    public static void main(String[] args) {
        Thread client = new ClientThread();
        client.start();

    }
}
