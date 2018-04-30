import java.io.*;
import java.net.*;
import java.util.*;
public class ACK
{
   public void sendACK(byte[] add, int port) throws Exception
   {
       DatagramSocket socket = new DatagramSocket();
       byte[] packetData = ("ACK").getBytes();
       System.out.println("5***Sending ACK***");
       InetAddress address  = InetAddress.getByAddress(add);
       DatagramPacket ack_packet=new DatagramPacket(packetData,packetData.length,address,port);
   }
   public boolean isACK(String test)
   {
       return test == "ACK";
   }
}
