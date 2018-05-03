import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
public class DirectoryServer
{
    private DatagramSocket socket;
    public void startServer(int senderPort, int receiverPort)
    {
            socket = new DatagramSocket();
            Receiver receiver = new Receiver("Directory Server",receiverPort);
            receiver.start();
            byte[] buffer = new byte[1];
            DatagramPacket data = new DatagramPacket(buffer, buffer.length);
            socket.receive(ack);
//            while(receiver.getData() != null)
//            {
//            }
//        String dta = receiver.getData();
//        System.out.println(dta + "sdsdsd");


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
