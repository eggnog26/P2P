import java.util.*;
public class Client
{
    public void clientStart(int receiverPort, int senderPort)
    {
        Receiver clientReceiver;
        System.out.println("Enter Text To Send Over UDP(no spaces)\n");
        Scanner input = new Scanner(System.in);
        String data = input.next();
        byte[] address = {127,0,0,1}; //IP address set to localhost
        String strAddress = "{127,0,0,1}";

        clientReceiver = new Receiver("Client", receiverPort);
        clientReceiver.start();
        Sender clientSender = new Sender();
        try {
            clientSender.startSender(address, receiverPort);
            clientSender.rdtSend(data.getBytes());
        }catch(Exception e)
        {
                e.printStackTrace();
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

