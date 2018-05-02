public class Main
{
    public static void main(String[] args) throws Exception
    {
        Receiver receiverThread;

            DirectoryServer server = new DirectoryServer();
            server.startServer(2002, 3002);
            Client client = new Client();
            client.clientStart(3002, 2002);
    }
}