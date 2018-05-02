import java.util.ArrayList;
public class DirectoryServer
{
    public void startServer(int senderPort, int receiverPort)
    {
        try {
            Receiver receiver = new Receiver("Directory Server",receiverPort);
            receiver.start();

            //byte[] targetAdddress = {127,0,0,1};
            //Sender sender = new Sender();
            //sender.startSender(targetAdddress, senderPort);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private class directoryEntry
    {
        String fileName;
        ArrayList<String> hostAddresses = new ArrayList<String>();

        public directoryEntry(String f, String ip)
        {
            fileName = f;
            hostAddresses.add(ip);
        }
        //0yearbook.txt ip address, receiverport,
        //query <yearbook.txt, ipaddress, receiverport

        public String getFileName()
        {
            return fileName;
        }

        public void addHost(String ip)
        {
            hostAddresses.add(ip);
        }

        public String displayHostAddresses()
        {
            String ha = String.join(", ", hostAddresses);
            return ha;
        }
        public ArrayList<String> getHostAddresses()
        {
            return hostAddresses;
        }
    }
}
