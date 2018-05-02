import java.io.*;
import java.net.*;
import java.util.*;
public class Client
{
    private InetAddress ip;
    String strAddress = "192.168.1.12";
    public void clientStart(int receiverPort, int senderPort)
    {

        try
        {
            InetAddress ip = InetAddress.getByName(strAddress);
            byte[] address = ip.getAddress();

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("++Choose Option++");
        System.out.println("(1) - Send Text Message Through UDP");
        System.out.println("(2) - Send File Through TCP");
        Scanner input = new Scanner(System.in);
        String select = input.next();
        if (select.equals("1")) //Send Text Message Through UDP
        {
            Receiver clientReceiver;
            clientReceiver = new Receiver("Client", receiverPort);
            clientReceiver.start();
            System.out.println("Enter Text To Send Over UDP(no spaces)\n");
            Scanner input2 = new Scanner(System.in);
            String data = input.next();
            try
            {
                InetAddress ip = InetAddress.getByName(strAddress);
                byte[] address = ip.getAddress();

                Sender clientSender = new Sender();
                clientSender.startSender(address, receiverPort);
                clientSender.rdtSend(data.getBytes());
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

//        if (data.equals("0"))
//        {
//        }
//
//
//
//        if (data.equals("1"))
//        {
//            File folder = new File("C:\\Users\\Yagna\\IdeaProjects\\P2P\\client1Files");
//            File[] listOfFiles = folder.listFiles();
//            String files = "";
//            for(int i = 0; i < listOfFiles.length; i++)
//            {
//                files += (listOfFiles[i].getName() + "," + strAddress + "," + receiverPort + ";");
//            }
//            clientReceiver = new Receiver("Client", receiverPort);
//            clientReceiver.start();
//            Sender clientSender = new Sender();
//            try {
//                clientSender.startSender(address, receiverPort);
//                clientSender.rdtSend(files.getBytes());
//            }catch(Exception e){
//                e.printStackTrace();
//            }
//        }
//
//
//        if (data.equals("2"))
//        {
//
//        }


    }
}

