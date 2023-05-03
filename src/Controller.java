import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.*;

public class Controller {

    List<InetSocketAddress> DServerList = new ArrayList<>();
    List<InetSocketAddress> DServer2List = new ArrayList<>();
    List<InetSocketAddress> CPList = new ArrayList<>();
    List<InetSocketAddress> ISPList = new ArrayList<>();
    List<InetSocketAddress> GW1List = new ArrayList<>();
    List<InetSocketAddress> GW2List = new ArrayList<>();
    List<InetSocketAddress> GW3List = new ArrayList<>();
    List<InetSocketAddress> CsLaptopList = new ArrayList<>();
    List<InetSocketAddress> PsLaptopList = new ArrayList<>();
    List<InetSocketAddress> MsLaptopList = new ArrayList<>();

    Controller(Hashtable<InetSocketAddress, List<InetSocketAddress>> forwardingTable) {
        add();
        forwardingTable.put(Constants.DSERVER_ADDRESS, DServerList);
        forwardingTable.put(Constants.DSERVER2_ADDRESS, DServer2List);
        forwardingTable.put(Constants.CP_ADDRESS, CPList);
        forwardingTable.put(Constants.ISP_ADDRESS, ISPList);
        forwardingTable.put(Constants.GW1_ADDRESS, GW1List);
        forwardingTable.put(Constants.GW2_ADDRESS, GW2List);
        forwardingTable.put(Constants.GW3_ADDRESS, GW3List);
        forwardingTable.put(Constants.CS_LAPTOP_ADDRESS, CsLaptopList);
        forwardingTable.put(Constants.PS_LAPTOP_ADDRESS, PsLaptopList);
        forwardingTable.put(Constants.MS_LAPTOP_ADDRESS, MsLaptopList);

    }

    public void add() {
        CsLaptopList.add(Constants.GW1_ADDRESS);
        PsLaptopList.add(Constants.GW2_ADDRESS);
        MsLaptopList.add(Constants.GW3_ADDRESS);
        CPList.add(Constants.ISP_ADDRESS);
        CPList.add(Constants.DSERVER_ADDRESS);
        CPList.add(Constants.DSERVER2_ADDRESS);
        ISPList.add(Constants.GW1_ADDRESS);
        ISPList.add(Constants.GW2_ADDRESS);
        ISPList.add(Constants.GW3_ADDRESS);
        ISPList.add(Constants.CP_ADDRESS);
        GW1List.add(Constants.CS_LAPTOP_ADDRESS);
        GW1List.add(Constants.ISP_ADDRESS);
        GW2List.add(Constants.PS_LAPTOP_ADDRESS);
        GW2List.add(Constants.ISP_ADDRESS);
        GW3List.add(Constants.MS_LAPTOP_ADDRESS);
        GW3List.add(Constants.ISP_ADDRESS);
        DServerList.add(Constants.CP_ADDRESS);
        DServer2List.add(Constants.CP_ADDRESS);
    }

    public boolean matches2(Hashtable<InetSocketAddress, List<InetSocketAddress>> table, InetSocketAddress Key, InetSocketAddress Value){
        return table.get(Key).get(0) == Value || table.get(Key).get(1) == Value;
    }

    public boolean matches4(Hashtable<InetSocketAddress, List<InetSocketAddress>> table, InetSocketAddress Key, InetSocketAddress Value){
        return table.get(Key).get(0) == Value || table.get(Key).get(1) == Value || table.get(Key).get(2) == Value || table.get(Key).get(3) == Value;
    }

    public synchronized Hashtable<InetSocketAddress, List<InetSocketAddress>> fillForwardingTable(Hashtable<InetSocketAddress,
            List<InetSocketAddress>> table, InetSocketAddress origin, List<InetSocketAddress> list) throws InterruptedException {
        for(int i = 0; i < list.size(); i++){
            System.out.println("---\nAdding " + Constants.ANSI_CYAN + list.get(i) + Constants.ANSI_RESET +
                    " to " + Constants.ANSI_CYAN + origin.getHostName() + Constants.ANSI_RESET + "'s forwarding table. ");
            Thread.sleep(500);
        }
        table.put(origin, list);
        return table;
    }
}
