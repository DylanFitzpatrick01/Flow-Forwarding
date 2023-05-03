import java.net.InetSocketAddress;

public class Constants {
    static final int EMPLOYEE_PORT = 54321;
    static final int DSERVER_PORT = 54321;
    static final int FORWARDER_PORT = 54321;
    static final String GW1_DST_NODE = "GW1";
    static final String GW2_DST_NODE = "GW2";
    static final String GW3_DST_NODE = "GW3";
    static final String ISP_DST_NODE = "ISP";
    static final String CP_DST_NODE = "CP";
    static final String DSERVER_DST_NODE = "DServer";
    static final String DSERVER2_DST_NODE = "DServer2";
    static final String C_DST_NODE = "CsLaptop";
    static final String M_DST_NODE = "MsLaptop";
    static final String P_DST_NODE = "PsLaptop";
    static final InetSocketAddress CS_LAPTOP_ADDRESS = new InetSocketAddress(C_DST_NODE, EMPLOYEE_PORT);
    static final InetSocketAddress MS_LAPTOP_ADDRESS = new InetSocketAddress(M_DST_NODE, EMPLOYEE_PORT);
    static final InetSocketAddress PS_LAPTOP_ADDRESS = new InetSocketAddress(P_DST_NODE, EMPLOYEE_PORT);
    static final InetSocketAddress GW1_ADDRESS = new InetSocketAddress(GW1_DST_NODE, FORWARDER_PORT);
    static final InetSocketAddress GW2_ADDRESS = new InetSocketAddress(GW2_DST_NODE, FORWARDER_PORT);
    static final InetSocketAddress GW3_ADDRESS = new InetSocketAddress(GW3_DST_NODE, FORWARDER_PORT);
    static final InetSocketAddress ISP_ADDRESS = new InetSocketAddress(ISP_DST_NODE, FORWARDER_PORT);
    static final InetSocketAddress CP_ADDRESS = new InetSocketAddress(CP_DST_NODE, FORWARDER_PORT);
    static final InetSocketAddress DSERVER_ADDRESS = new InetSocketAddress(DSERVER_DST_NODE, DSERVER_PORT);
    static final InetSocketAddress DSERVER2_ADDRESS = new InetSocketAddress(DSERVER2_DST_NODE, DSERVER_PORT);
    static final InetSocketAddress[] addresses = {CS_LAPTOP_ADDRESS, MS_LAPTOP_ADDRESS, PS_LAPTOP_ADDRESS, GW1_ADDRESS,
                            GW2_ADDRESS, GW3_ADDRESS, ISP_ADDRESS, CP_ADDRESS, DSERVER_ADDRESS, DSERVER2_ADDRESS};
    static final String ANSI_RESET = "\u001B[0m";
    static final String ANSI_BLACK = "\u001B[30m";
    static final String ANSI_RED = "\u001B[31m";
    static final String ANSI_GREEN = "\u001B[32m";
    static final String ANSI_YELLOW = "\u001B[33m";
    static final String ANSI_BLUE = "\u001B[34m";
    static final String ANSI_PURPLE = "\u001B[35m";
    static final String ANSI_CYAN = "\u001B[36m";
    static final String ANSI_WHITE = "\u001B[37m";
    static final String ANSI_BRIGHT_BLACK = "\u001b[30;1m";
    static final String ANSI_BRIGHT_YELLOW = "\u001b[33;1m";
}
