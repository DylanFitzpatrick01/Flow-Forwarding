import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

public class DServer extends Node{

    InetSocketAddress nextAddress;
    Hashtable<InetSocketAddress, List<InetSocketAddress>> forwardingTable = new Hashtable<>();
    Controller controller;
    List<LocalTime> times = new ArrayList<>();
    List<String> users = new ArrayList<>();
    // make sure hash table is not null
    {
        assert false;
        controller = new Controller(forwardingTable);
    }

    DServer(String dstHost, int dstPort, int srcPort) {
        try {
            nextAddress = new InetSocketAddress(dstHost, dstPort);
            socket = new DatagramSocket(srcPort);
            listener.go();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    public void onReceipt(DatagramPacket packet) throws IOException, InterruptedException {
        PacketContent content = PacketContent.fromDatagramPacket(packet);

        if(content.getType() == PacketContent.APPLICATION){
            System.out.println("---\nReceived appointment request for " + Constants.ANSI_GREEN + ((HeaderContent)content).getTime() +
                    Constants.ANSI_RESET + " from " + Constants.ANSI_CYAN + packet.getSocketAddress() + Constants.ANSI_RESET);
            DatagramPacket response;
            response = new AckPacketContent("Received request for appointment at " + Constants.ANSI_GREEN + ((HeaderContent) content).getTime()
                    + Constants.ANSI_RESET + ".").toDatagramPacket();
            Thread.sleep(500);
            response.setSocketAddress(packet.getSocketAddress());
            socket.send(response);
            if(times.contains(((HeaderContent)content).getTime())){
                System.out.println("---\nAppointment for "+ Constants.ANSI_GREEN +((HeaderContent) content).getTime() + Constants.ANSI_RESET +" already exists.");
                Thread.sleep(500);
                System.out.println("---\nAppointment could not be created" + Constants.ANSI_YELLOW + " :(" + Constants.ANSI_RESET);
            }
            else {
                times.add(((HeaderContent) content).getTime());
                users.add(((HeaderContent) content).getEmployee());
                Collections.sort(times, new TimesComparator());
                System.out.println("---\nAppointment created " + Constants.ANSI_YELLOW +":)"+Constants.ANSI_RESET);
            }
            Thread.sleep(500);
            System.out.println("---\nCurrent list of appointments with : \n" + (((HeaderContent) content).getDoctor()) +
                    Constants.ANSI_BRIGHT_BLACK + "\n---------------------------------- \n" +
                    "| Time(s):                       |\n" +
                    "----------------------------------" + Constants.ANSI_RESET);
            for(LocalTime time: times){
                System.out.println(Constants.ANSI_BRIGHT_BLACK + "| " + Constants.ANSI_RESET +
                        Constants.ANSI_YELLOW + (times.indexOf(time)+1) + ". " + Constants.ANSI_RESET +
                        time + " with " + users.get(times.indexOf(time)) + Constants.ANSI_BRIGHT_BLACK +"                 |"
                        + Constants.ANSI_RESET);
            }
            System.out.print(Constants.ANSI_BRIGHT_BLACK + "---------------------------------- \n" + Constants.ANSI_RESET);
            Thread.sleep(500);
        }
    }

    public synchronized void start(){
        try {
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n---\nWaiting...");
            this.wait();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        try{
            (new DServer(Constants.CP_DST_NODE, Constants.FORWARDER_PORT, Constants.DSERVER_PORT)).start();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
