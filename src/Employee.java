import java.net.*;
import java.time.LocalTime;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

public class Employee extends Node {

    InetSocketAddress nextAddress;
    InetSocketAddress address;
    Hashtable<InetSocketAddress, List<InetSocketAddress>> forwardingTable = new Hashtable<>();
    Controller controller;
    static String username;

    // make sure hash table is not null
    {
        assert false;
        controller = new Controller(forwardingTable);
    }

    Employee(String dstHost, int dstPort, int srcPort) {
        try {
            nextAddress = new InetSocketAddress(dstHost, dstPort);
            socket = new DatagramSocket(srcPort);
            listener.go();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void onReceipt(DatagramPacket packet) throws InterruptedException {
        PacketContent content = PacketContent.fromDatagramPacket(packet);
        if (content.getType() == PacketContent.ACK) {
            Thread.sleep(1000);
            System.out.println(content);
        }
        Thread.sleep(15000);
        this.notify();
    }

    public synchronized void start() throws InterruptedException {
        System.out.println(Constants.ANSI_BRIGHT_YELLOW + "\n\n\n\n\n\n\n\n\n\n\n\n\n\nWelcome to the appointment making system! \n" +
                "Appointments are available to make between the hours of 09:00 and 17:30.\n" +
                "Appointments may only be booked at 30-minute intervals (e.g. 09:00 or 14:30)" +
                Constants.ANSI_RESET);
        boolean exit = false;
        while (!exit) {
            try {
                System.out.print("---\nEnter time for appointment (hh:mm) or type 'exit' to exit: ");
                Scanner sc = new Scanner(System.in);
                String input = sc.next();
                if (input.equalsIgnoreCase("exit")) {
                    exit = true;
                } else {
                    LocalTime time = LocalTime.parse(input);
                    Thread.sleep(500);
                    if (time.getMinute() % 30 != 0 || time.getSecond() != 0) {
                        System.out.println("---\nAppointments only available every 30 minutes");
                        Thread.sleep(500);
                    } else if (time.isBefore(LocalTime.parse("09:00")) || time.isAfter(LocalTime.parse("17:30"))) {
                        System.out.println("---\nAppointments only available from 09:00 to 17:30");
                        Thread.sleep(500);
                    } else {
                        DatagramPacket application;
                        System.out.println("Is this appointment with Dr. Anderson or Dr. Smith? ");
                        String dr = sc.next();
                        application = new HeaderContent("Creating appointment request for " + time + " with Dr. " + dr, time,
                                username, dr).toDatagramPacket();
                        System.out.println("---\nSending appointment request for " + Constants.ANSI_GREEN + time + Constants.ANSI_RESET + " to: " + Constants.ANSI_CYAN +
                                dr + Constants.ANSI_RESET);
                        if (username.equalsIgnoreCase("C")) {
                            address = forwardingTable.get(Constants.CS_LAPTOP_ADDRESS).get(0);
                        } else if (username.equalsIgnoreCase("M")) {
                            address = forwardingTable.get(Constants.MS_LAPTOP_ADDRESS).get(0);
                        } else if (username.equalsIgnoreCase("P")) {
                            address = forwardingTable.get(Constants.PS_LAPTOP_ADDRESS).get(0);
                        } else System.out.println("User does not exist.");
                        application.setSocketAddress(address);
                        socket.send(application);
                        this.wait();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        try {
            System.out.println("C");
            System.out.println("P");
            System.out.println("M");
            System.out.println("Please enter your username: ");
            Scanner sc = new Scanner(System.in);
            username = sc.next();
            if(username.equalsIgnoreCase("C")) {
                (new Employee(Constants.GW1_DST_NODE, Constants.FORWARDER_PORT, Constants.EMPLOYEE_PORT)).start();
            }
            else if (username.equalsIgnoreCase("P")){
                (new Employee(Constants.GW2_DST_NODE, Constants.FORWARDER_PORT, Constants.EMPLOYEE_PORT)).start();
            }
            else if (username.equalsIgnoreCase("M")){
                (new Employee(Constants.GW3_DST_NODE, Constants.FORWARDER_PORT, Constants.EMPLOYEE_PORT)).start();
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }
}
