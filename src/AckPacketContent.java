import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Class for packet content that represents acknowledgements
 *
 */
public class AckPacketContent extends PacketContent {

    String info;

    /**
     * Constructor that takes in information about a file.
     * @param info information about the file
     */
    AckPacketContent(String info) {
        type = ACK;
        this.info = info;
    }

    /**
     * Constructs an object out of a datagram packet.
     * @param oin reads in the file info
     */
    protected AckPacketContent(ObjectInputStream oin) {
        try {
            type = ACK;
            info = oin.readUTF();
        }
        catch(Exception e) {e.printStackTrace();}
    }

    /**
     * Writes the content into an ObjectOutputStream
     * @param oout writes the file info
     */
    protected void toObjectOutputStream(ObjectOutputStream oout) {
        try {
            oout.writeUTF(info);
        }
        catch(Exception e) {e.printStackTrace();}
    }



    /**
     * Returns the content of the packet as String.
     *
     * @return Returns the content of the packet as String.
     */
    public String toString() {
        return "---\n" + Constants.ANSI_PURPLE +"ACK: " + Constants.ANSI_RESET + info;
    }

    /**
     * Returns the info contained in the packet.
     *
     * @return Returns the info contained in the packet.
     */
    public String getPacketInfo() {
        return info;
    }
}
