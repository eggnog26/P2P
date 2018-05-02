import java.util.*;

public class Main
{
    public static void main(String[] args) throws Exception
    {
            DirectoryServer server = new DirectoryServer();
            server.startServer(2002, 3002);
            Client client = new Client();
            client.clientStart(4002, 5002);
    }
}