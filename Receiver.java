import java.io.*;
import java.net.*;
import java.util.*;
public class Receiver extends Thread
{
    private int port;
    private DatagramSocket socket;
    
    public Receiver(String name, int port)
    {
        super(name);
        this.port = port;
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
        System.out.println("***Received packet with: '" + new String(data) + "'***");
    }
    
    public void run() //Opens socket for datagram transfer, pushes datagram to a buffer, and assembles recieved data
    {
        try
        {
            socket=new DatagramSocket(50000);
            while (true)
            {
                System.out.println("***Waiting for packet***");
                byte[] buffer=new byte[16];
                
                DatagramPacket packet=new DatagramPacket(buffer,buffer.length);
                socket.receive(packet);
                byte[] packetData=Arrays.copyOf(packet.getData(),packet.getLength());
                receiveData(packetData);
            }
        }
        catch (Exception e)
        {
            stopRecieving();
        }
    }
}