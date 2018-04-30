import java.net.*;
import java.util.*;
public class DirectoryMain
{
    public static void main(String[] args) throws Exception
    {
        Receiver receiver=new Receiver("Receiver", 2002);//sets new receiver with port 50000
        receiver.start();
        
        Scanner input=new Scanner(System.in);//Input from user to send
        byte[] address = {127,0,0,1}; //IP address set to localhost
        Sender sender=new Sender();
        sender.start(address,2002);  //Sender created and assigned to port 50000
        String data=input.next();		//Gather input data
        sender.rdtSend(data.getBytes());//Sends data to reciever
    }
}