import java.io.*;
import java.net.*;
import java.util.*;
public class Client
{
    public void clientStart(int receiverPort, int senderPort)
    {
        byte[] Address = {127,0,0,1};
        String strAddress = "127.0.0.1";
        System.out.println("++Choose Option++");
        System.out.println("(1) - Send Text Message Through UDP");
        System.out.println("(2) - Send File Through TCP");
        System.out.println("(3) - Inform and Update");
        Scanner input = new Scanner(System.in);
        String select = input.next();
        if (select.equals("1")) //Send Text Message Through UDP
        {
            System.out.println("Enter Text To Send Over UDP(no spaces)\n");
            Scanner input2 = new Scanner(System.in);
            Receiver clientReceiver1;
            clientReceiver1 = new Receiver("Client", receiverPort);
            clientReceiver1.start();
            try
            {
                Sender clientSender1 = new Sender();
                clientSender1.startSender(Address, receiverPort);
                String data = input2.next();
                clientSender1.rdtSend(data.getBytes());
                System.out.println(clientReceiver1.getData()+"hello");
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        else if (select.equals("2")) //Transfer File Through TCP
        {
             System.out.println("****Enter Requesting File Location*****");
            Scanner input3 = new Scanner(System.in);
            String fileLoc = input.next();
            System.out.println("****Enter Destination Location*****");
            Scanner input4 = new Scanner(System.in);
            String destLoc = input.next();
            String fileNm = (fileLoc.substring(fileLoc.lastIndexOf("\\") + 1));
            System.out.println(fileLoc + "  -  " + destLoc + "  -  " + fileNm + "\n  -" + destLoc + "\\" + fileNm);
            
            TCPServer fs = new TCPServer(2002, destLoc, fileNm);
            new Thread(fs).start();

            TCPClient fc = new TCPClient(strAddress, 2002, fileLoc);

        }
        else if(select.equals("3"))
        {
           File folder = new File("C:\\Users\\Yagna\\IdeaProjects\\P2P\\client1Files");
           File[] listOfFiles = folder.listFiles();
           String files = "";
           for(int i = 0; i < listOfFiles.length; i++)
           {
               files += (listOfFiles[i].getName() + "," + strAddress + "," + receiverPort + ";");
           }
           Sender clientSender2 = new Sender();
           try {
               clientSender2.startSender(Address, 3002);
               clientSender2.rdtSend(files.getBytes());
           }catch(Exception e){
               e.printStackTrace();
           }
        }

    }
}

