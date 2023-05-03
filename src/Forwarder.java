import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Forwarder extends Node {
    InetSocketAddress nextAddress;
    Hashtable<InetSocketAddress, List<InetSocketAddress>> forwardingTable = new Hashtable<>();
    Hashtable<InetSocketAddress, List<InetSocketAddress>> CPForwardingTable = new Hashtable<>();
    Hashtable<InetSocketAddress, List<InetSocketAddress>> ISPForwardingTable = new Hashtable<>();
    Hashtable<InetSocketAddress, List<InetSocketAddress>> GW1ForwardingTable = new Hashtable<>();
    Hashtable<InetSocketAddress, List<InetSocketAddress>> GW2ForwardingTable = new Hashtable<>();
    Hashtable<InetSocketAddress, List<InetSocketAddress>> GW3ForwardingTable = new Hashtable<>();
    List<InetSocketAddress> CPlist = new ArrayList<>();
    List<InetSocketAddress> ISPlist = new ArrayList<>();
    List<InetSocketAddress> GW1list = new ArrayList<>();
    List<InetSocketAddress> GW2list = new ArrayList<>();
    List<InetSocketAddress> GW3list = new ArrayList<>();
    Controller controller;

    // make sure hash table is not null
    {
        assert false;
        controller = new Controller(forwardingTable);
    }

    Forwarder(String dstHost, int dstPort, int srcPort) {
        try {
            nextAddress = new InetSocketAddress(dstHost, dstPort);
            socket = new DatagramSocket(srcPort);
            listener.go();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    public void printForwardingTable(List<InetSocketAddress> list) {
        for (InetSocketAddress element : list) {
            System.out.println(
                    "  " + Constants.ANSI_YELLOW + (list.indexOf(element) + 1) + ". " + Constants.ANSI_RESET +
                            element);
        }
        System.out.println(Constants.ANSI_BRIGHT_BLACK + "----------------------------------" + Constants.ANSI_RESET);
    }

    public synchronized void sendPacket(InetSocketAddress currentAddress, DatagramPacket packet, int direction) throws IOException, InterruptedException {
        packet.setSocketAddress(forwardingTable.get(currentAddress).get(direction));
        Thread.sleep(2000);
        socket.send(packet);
        System.out.println("Forwarded to " + Constants.ANSI_CYAN + forwardingTable.get(currentAddress).get(direction) + Constants.ANSI_RESET);
    }

    public synchronized void onReceipt(DatagramPacket packet) throws IOException, InterruptedException {
        PacketContent content = PacketContent.fromDatagramPacket(packet);
        if (content.getType() == PacketContent.APPLICATION) {
            DatagramPacket response;
            response = new AckPacketContent("Received request for appointment at " + Constants.ANSI_GREEN + ((HeaderContent) content).getTime()
                    + Constants.ANSI_RESET + ".").toDatagramPacket();
            response.setSocketAddress(packet.getSocketAddress());
            socket.send(response);
            if (("CsLaptop" + packet.getSocketAddress()).equals(Constants.CS_LAPTOP_ADDRESS.toString())) {
                if (GW1ForwardingTable.isEmpty()) {
                    for (InetSocketAddress address : Constants.addresses) {
                        if (controller.matches2(forwardingTable, Constants.GW1_ADDRESS, address)) {
                            GW1list.add(address);
                        }
                    }
                    controller.fillForwardingTable(GW1ForwardingTable, Constants.GW1_ADDRESS, GW1list);
                }
                Thread.sleep(500);
                System.out.println("---\n" + Constants.ANSI_BRIGHT_BLACK + "---------------------------------- \n" +
                        "| GW1's forwarding table:         |\n" +
                        "----------------------------------" + Constants.ANSI_RESET);
                printForwardingTable(GW1list);
                Thread.sleep(500);
                System.out.println("---\nReceived appointment request for " + Constants.ANSI_GREEN
                        + ((HeaderContent) content).getTime() + Constants.ANSI_RESET + " from " + Constants.ANSI_CYAN + packet.getSocketAddress() + Constants.ANSI_RESET);
                Thread.sleep(500);
                System.out.println("---\nForwarding appointment request for " + Constants.ANSI_GREEN
                        + ((HeaderContent) content).getTime() + Constants.ANSI_RESET + "\n---");
                sendPacket(Constants.GW1_ADDRESS, packet, 1);
            } else if (("PsLaptop" + packet.getSocketAddress()).equals(Constants.PS_LAPTOP_ADDRESS.toString())) {
                if (GW2ForwardingTable.isEmpty()) {
                    for (InetSocketAddress address : Constants.addresses) {
                        if (controller.matches2(forwardingTable, Constants.GW2_ADDRESS, address)) {
                            GW2list.add(address);
                        }
                    }
                    controller.fillForwardingTable(GW2ForwardingTable, Constants.GW2_ADDRESS, GW2list);
                }
                Thread.sleep(500);
                System.out.println("---\n" + Constants.ANSI_BRIGHT_BLACK + "---------------------------------- \n" +
                        "| GW2's forwarding table:         |\n" +
                        "----------------------------------" + Constants.ANSI_RESET);
                printForwardingTable(GW2list);
                Thread.sleep(500);
                System.out.println("---\nReceived appointment request for " + Constants.ANSI_GREEN
                        + ((HeaderContent) content).getTime() + Constants.ANSI_RESET + " from " + Constants.ANSI_CYAN + packet.getSocketAddress() + Constants.ANSI_RESET);
                Thread.sleep(500);
                System.out.println("---\nForwarding appointment request for " + Constants.ANSI_GREEN
                        + ((HeaderContent) content).getTime() + Constants.ANSI_RESET + "\n---");
                sendPacket(Constants.GW2_ADDRESS, packet, 1);
            } else if (("MsLaptop" + packet.getSocketAddress()).equals(Constants.MS_LAPTOP_ADDRESS.toString())) {
                if (GW3ForwardingTable.isEmpty()) {
                    for (InetSocketAddress address : Constants.addresses) {
                        if (controller.matches2(forwardingTable, Constants.GW3_ADDRESS, address)) {
                            GW3list.add(address);
                        }
                    }
                    controller.fillForwardingTable(GW3ForwardingTable, Constants.GW3_ADDRESS, GW3list);
                }
                Thread.sleep(500);
                System.out.println("---\n" + Constants.ANSI_BRIGHT_BLACK + "---------------------------------- \n" +
                        "| GW3's forwarding table:         |\n" +
                        "----------------------------------" + Constants.ANSI_RESET);
                printForwardingTable(GW3list);
                Thread.sleep(500);
                System.out.println("---\nReceived appointment request for " + Constants.ANSI_GREEN
                        + ((HeaderContent) content).getTime() + Constants.ANSI_RESET + " from " + Constants.ANSI_CYAN + packet.getSocketAddress() + Constants.ANSI_RESET);
                Thread.sleep(500);
                System.out.println("---\nForwarding appointment request for " + Constants.ANSI_GREEN
                        + ((HeaderContent) content).getTime() + Constants.ANSI_RESET + "\n---");
                sendPacket(Constants.GW3_ADDRESS, packet, 1);
            } else if (("GW1" + packet.getSocketAddress()).equals(Constants.GW1_ADDRESS.toString()) ||
                    ("GW2" + packet.getSocketAddress()).equals(Constants.GW2_ADDRESS.toString()) ||
                    ("GW3" + packet.getSocketAddress()).equals(Constants.GW3_ADDRESS.toString())) {
                if (ISPlist.isEmpty()) {
                    for (InetSocketAddress address : Constants.addresses) {
                        if (controller.matches4(forwardingTable, Constants.ISP_ADDRESS, address)) {
                            ISPlist.add(address);
                        }
                    }
                    controller.fillForwardingTable(ISPForwardingTable, Constants.ISP_ADDRESS, ISPlist);
                }
                Thread.sleep(500);
                System.out.println("---\n" + Constants.ANSI_BRIGHT_BLACK + "---------------------------------- \n" +
                        "| ISP's forwarding table:        |\n" +
                        "----------------------------------" + Constants.ANSI_RESET);
                printForwardingTable(ISPlist);
                Thread.sleep(500);
                System.out.println("---\nReceived appointment request for " + Constants.ANSI_GREEN + ((HeaderContent) content).getTime() +
                        Constants.ANSI_RESET + " from " + Constants.ANSI_CYAN + packet.getSocketAddress() + Constants.ANSI_RESET);
                Thread.sleep(500);
                System.out.println("---\nForwarding appointment request for:\n" + Constants.ANSI_GREEN + ((HeaderContent) content).getTime() +
                        Constants.ANSI_RESET + "\n---");
                sendPacket(Constants.ISP_ADDRESS, packet, 3);
            } else if (("ISP" + packet.getSocketAddress()).equals(Constants.ISP_ADDRESS.toString())) {
                if (CPlist.isEmpty()) {
                    for (InetSocketAddress address : Constants.addresses) {
                        if (controller.matches2(forwardingTable, Constants.CP_ADDRESS, address)) {
                            CPlist.add(address);
                            Thread.sleep(500);
                        }
                    }
                    controller.fillForwardingTable(CPForwardingTable, Constants.CP_ADDRESS, CPlist);
                }
                Thread.sleep(500);
                System.out.println("---\n" + Constants.ANSI_BRIGHT_BLACK + "---------------------------------- \n" +
                        "| CP's forwarding table:        |\n" +
                        "----------------------------------" + Constants.ANSI_RESET);
                printForwardingTable(CPlist);
                Thread.sleep(500);
                System.out.println("---\nReceived appointment request for " + Constants.ANSI_GREEN + ((HeaderContent) content).getTime()
                        + Constants.ANSI_RESET + " from " + Constants.ANSI_CYAN + packet.getSocketAddress() + Constants.ANSI_RESET);
                Thread.sleep(500);
                System.out.println("--- \nForwarding appointment request for: " + Constants.ANSI_GREEN +
                        ((HeaderContent) content).getTime() + Constants.ANSI_RESET + "\n---");
                if (((HeaderContent) content).getDoctor().equals("Anderson")){
                    sendPacket(Constants.CP_ADDRESS, packet, 1);
                }
                else if (((HeaderContent) content).getDoctor().equals("Smith")) {
                    sendPacket(Constants.CP_ADDRESS, packet, 2);
                } else System.out.println("Doctor requested is invalid...");
            } else System.out.println("---\nNot a valid address. ");
        }
        if (content.getType() == PacketContent.ACK) {
            Thread.sleep(1000);
            System.out.println(content);
        }
        Thread.sleep(500);
    }

    public synchronized void start() throws Exception {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n---\nWaiting...");
        this.wait();
    }

    public static void main(String[] args) {
        try {
            (new Forwarder(Constants.C_DST_NODE, Constants.EMPLOYEE_PORT, Constants.FORWARDER_PORT)).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
