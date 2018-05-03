import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class Test
{
    public static void main(String[] args) throws Exception {
        Receiver receiver = new Receiver("Directory Server", 2304);
        receiver.start();
        try {
            DatagramSocket socket;
            int port = 2304;
            socket = new DatagramSocket(port);
            int x = 0;
            while (x<20)
            {
                byte[] buffer = new byte[36];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String FileData = packet.toString();
                System.out.println("File Data: " + FileData);
                x++;
            }

        } catch (Exception e)
        {
            receiver.stopRecieving();
        }
    }
}
