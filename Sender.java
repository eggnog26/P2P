import java.io.*;
import java.net.*;
import java.util.*;
public class Sender
{
    private int port;
    private int receiverPort;
    private DatagramSocket socket;
    private InetAddress address;
    private int port2;
    private String name;
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
            System.out.println("2***Sending packet("+new String((packetNumber++)+")")+": '"+new String(packetData)+"'***");
            DatagramPacket packet=new DatagramPacket(packetData,packetData.length,address,receiverPort);
            socket.send(packet);
            
            //Thread.sleep(1200);
        }
        System.out.println("8***Done sending***");
    }
    public void receiveACK(byte[] data) //Success message
    {
        System.out.println("7***Received ACK***" );
    }
    public void rec_ACK() //Opens socket for datagram transfer, pushes datagram to a buffer, and assembles recieved data
    {
       
        try
        {
            while (true)
            {
                System.out.println("3***Waiting for ACK###***");
                byte[] buffer=new byte[3];
                DatagramPacket packet=new DatagramPacket(buffer,buffer.length);
                socket.receive(packet);
                byte[] packetData=Arrays.copyOf(packet.getData(),packet.getLength());
                receiveACK(packetData);
            }
        }
        catch (Exception e)
        {
            stopRecieving();
        }
    }
    public void stopRecieving()//Closes socket
    {
        if (socket!=null)
        {
            socket.close();
        }
    }
}