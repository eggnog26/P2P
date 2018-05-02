import java.io.*;
import java.net.*;
import java.util.*;

public class Sender
{
    private byte seqNum = 0;
    private byte compareSeq = 0;
    private boolean isACK = false;
    private boolean Resend = false;
    private int receiverPort = 0;
    private DatagramSocket socket;
    private InetAddress address;
    private DatagramPacket packet2;

    public void startSender(byte[] targetAddress, int receiverPort) throws SocketException, UnknownHostException
    {
        socket = new DatagramSocket();
        address = InetAddress.getByAddress(targetAddress);
        this.receiverPort = receiverPort;
        socket.setSoTimeout(350);
    }

    public void waitForACK() throws IOException, InterruptedException
    {
        System.out.println("Sender Waiting For ACK ");
        isACK = false;
        while(!isACK)
        {
            byte[] buffer = new byte[1];
            DatagramPacket ack = new DatagramPacket(buffer, buffer.length);
            socket.receive(ack);
            System.out.println("Sender Recieved ACK ");
            if(ACKCheck(ack) == true)
            {
                isACK = true;
                flip();
            }
            else
                {
                isACK = false;
            }
        }
    }

    public boolean ACKCheck(DatagramPacket ack)
    {
        byte recvACK = ack.getData()[0];
        if(recvACK == compareSeq)
        {
            System.out.println("Correct ACK Recieved By Sender");
            return true;
        }
        else
        {
            return false;
        }
    }
    public void flip()
    {
        if (seqNum == 0)
        {
            seqNum = 1;
        }
        else if(seqNum == 1)
        {
            seqNum = 0;
        }
        else
        {
            System.out.println("Sequence Number Error");
        }
    }
    public void rdtSend(byte[] data) throws SocketException, IOException, InterruptedException
    {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
        int packetNumber = 0;
        while (byteStream.available()>0)
        {
            byte[] packetData = new byte[35];
            byte[] seqData = new byte[1];
            byte[] totalData = new byte[packetData.length + seqData.length];

            seqData[0] = seqNum;
            int bytesRead = byteStream.read(packetData);

            if (bytesRead<packetData.length)
            {
                packetData = Arrays.copyOf(packetData, bytesRead);
            }
            System.arraycopy(seqData, 0, totalData, 0, seqData.length);
            System.arraycopy(packetData, 0, totalData, seqData.length, packetData.length);
            DatagramPacket packet = new DatagramPacket(totalData, totalData.length, address, receiverPort);
            if (Resend == false)
            {
                String printData = new String(totalData);
                printData = printData.substring(1);
                System.out.println("Sending packet("+ new String((packetNumber++) + ")")+": '"+ printData );
                packet2  = new DatagramPacket(totalData, totalData.length, address,receiverPort);
                socket.send(packet);
                compareSeq = packet.getData()[0];
            }
            else
            {
                System.out.println("Resending Packet");
                packetNumber--;
                String d = new String(packet2.getData());
                d = d.substring(1);
                System.out.println("Resending packet("+new String((packetNumber++)+")")+": '"+ d );
                socket.send(packet2);
                seqNum = packet2.getData()[0];
            }

            try
            {
                waitForACK();
                Resend = false;
            }
            catch (SocketTimeoutException s)
            {
                System.out.println("ACK Timeout....Resending");
                Resend = true;
                //Thread.sleep(1200);
            }

            // Minor pause for easier visualization only
            //Thread.sleep(1200);
        }

        System.out.println("Sender done sending\n");
    }
}