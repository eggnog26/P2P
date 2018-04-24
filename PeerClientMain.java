import java.net.*;
import java.util.*;
public class PeerClientMain
{
    public static void main(String[] args)
    {
        Scanner input=new Scanner(System.in);//Input from user to send
        try
        {
            byte[] address = {127,0,0,1}; //IP address set to localhost
            Sender sender=new Sender();
            sender.start(address,50000);  //Sender created and assigned to port 50000

            String data=input.next();		//Gather input data
            sender.rdtSend(data.getBytes());//Sends data to reciever
        }
        catch(Exception e)
        {
            e.printStackTrace();//Helps identify any bugs by tracing the exception
        }
    }
}