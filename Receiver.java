import java.io.*;
import java.net.*;
import java.util.*;
public class Receiver extends Thread
{
    private int port;
    private int receiverPort;
    private DatagramSocket socket;
    private InetAddress address;
    public Receiver(String name, int port)
    {
        super(name);
        this.port = port;
    }

    public void start(byte[] targetAddress,int receiverPort) throws SocketException, UnknownHostException //Starts the sending of data by gathering the IP and setting the port
    {
        socket=new DatagramSocket();
        address=InetAddress.getByAddress(targetAddress);
        this.receiverPort=receiverPort;
    }

    public void stopRecieving()//Closes socket
    {
        if (socket!=null)
        {
            socket.close();
        }
    }

    public void receiveData(byte[] data) //Success message
    {
        System.out.println("4***Received packet with: '" + new String(data) + "'***");
    }

    public void run() //Opens socket for datagram transfer, pushes datagram to a buffer, and assembles recieved data
    {
        try
        {
            socket=new DatagramSocket(2002);
            ACK ack = new ACK();
            while (true)
            {
                System.out.println("1***Waiting for packet***");
                byte[] buffer=new byte[16];
                DatagramPacket packet=new DatagramPacket(buffer,buffer.length);
                socket.receive(packet);
                byte[] packetData=Arrays.copyOf(packet.getData(),packet.getLength());
                if(ack.isACK(new String(packetData)))
                    stopTimer();
                else
                {
                    receiveData(packetData);
                    InetAddress address = packet.getAddress();
                    ack.sendACK(address.getAddress(),2002);
                }
            }
        }
        catch (Exception e)
        {
            stopRecieving();
        }
    }
}