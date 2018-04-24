import java.io.*;
import java.net.*;
import java.util.*;
public class Sender
{
    private int receiverPort;
    private DatagramSocket socket;
    private InetAddress address;

    public void start(byte[] targetAddress,int receiverPort) throws SocketException, UnknownHostException //Starts the sending of data by gathering the IP and setting the port
    {
        socket=new DatagramSocket();
        address=InetAddress.getByAddress(targetAddress);
        this.receiverPort=receiverPort;
    }

    public void stop() //Closes socket
    {
        if (socket!=null)
        {
            socket.close();
        }
    }
    
    public void rdtSend(byte[] data) throws SocketException, IOException, InterruptedException //Sends data using Reliable Data Transfer, splitting data into 16 byte packets and assembleing them into a datagram for transfer 
    {
        ByteArrayInputStream byteStream=new ByteArrayInputStream(data);
        int packetNumber = 0;
        while (byteStream.available()>0)
        {
            byte[] packetData=new byte[16];
            int bytesRead=byteStream.read(packetData);
            if (bytesRead<packetData.length)
            {
                packetData = Arrays.copyOf(packetData, bytesRead);
            }
            System.out.println("***Sending packet("+new String((packetNumber++)+")")+": '"+new String(packetData)+"'***");
            DatagramPacket packet=new DatagramPacket(packetData,packetData.length,address,receiverPort);
            socket.send(packet);
            
            Thread.sleep(1200);
        }
        System.out.println("***Done sending***");
    }
}