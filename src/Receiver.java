import java.net.*;
import java.util.*;
public class Receiver extends Thread
{
    private int port;
    private DatagramSocket socket;
    private byte comapareSeq = 0;

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
    public void changeSeq(byte[] data)
    {
        String d = new String(data);
        if(comapareSeq == 0)
        {
            comapareSeq = 1;
        }
        else if(comapareSeq == 1)
        {
            comapareSeq = 0;
        }
    }

    public boolean checkSeq(DatagramPacket packet)
    {
        if (comapareSeq == packet.getData()[0])
        {
            System.out.println("Correct Sequence Number");
            return true;
        }
        else
        {
            System.out.println("Receiver duplicate packet detected.\n");
            return false;
        }
    }
    public void sendACK(DatagramPacket packet)
    {
        byte[] seq = new byte[1];
        seq[0] = packet.getData()[0];
        System.out.println(seq[0]);
        if(seq[0] == 0)
        {
            comapareSeq = 1;
        }
        else
        {
            comapareSeq = 0;
        }

        DatagramPacket ack = new DatagramPacket(seq,seq.length,packet.getAddress(),packet.getPort());
        try {
            socket.send(ack);
        }

        catch (Exception e){
            System.out.println("Sending ACK Failure\n");
        }
    }
    public void run() //Opens socket for datagram transfer, pushes datagram to a buffer, and assembles recieved data
    {
        try
        {
            socket=new DatagramSocket(port);
            while (true)
            {
                System.out.println("Waiting for packet\n");
                byte[] buffer=new byte[17];
                DatagramPacket packet=new DatagramPacket(buffer,buffer.length);
                socket.receive(packet);
                System.out.println(packet.getData()[0]);
                byte[] packetData = Arrays.copyOf(packet.getData(), packet.getLength());
                System.out.println("Receiver received packet with sequence number " + packet.getData()[0]+"\n");
                if(checkSeq(packet))
                {
                    changeSeq(packetData);
                }
                sendACK(packet);
            }
        }
        catch (Exception e)
        {
            stopRecieving();
        }
    }
}