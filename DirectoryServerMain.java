import java.net.*;
public class DirectoryServerMain
{
    public static void main(String[] args) //Main Class to receive data
    {
        Receiver receiver=new Receiver("Receiver", 50000);//sets new receiver with port 50000
        receiver.start(); //starts looks for incoming data from port 50000
    }
}