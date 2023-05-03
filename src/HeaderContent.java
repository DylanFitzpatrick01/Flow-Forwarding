import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.time.LocalTime;

/**
 * Class for packet content that represents file information
 *
 */
public class HeaderContent extends PacketContent {

    String string;
    LocalTime time;
    String employee;
    String doctor;

    /**
     * Constructor that takes in information about a file.
     * @param string
     * @param time
     * @param employee
     * @param doctor
     */
    HeaderContent(String string, LocalTime time, String employee, String doctor) {
        type = APPLICATION;
        this.string = string;
        this.time = time;
        this.employee = employee;
        this.doctor = doctor;
    }


    /**
     * Constructs an object out of a datagram packet.
     * @param oin reads in file information
     */
    protected HeaderContent(ObjectInputStream oin) {
        try {
            type = APPLICATION;
            string = oin.readUTF();
            time = (LocalTime) oin.readObject();
            employee =  oin.readUTF();
            doctor = oin.readUTF();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Writes the content into an ObjectOutputStream
     * @param oout writes file information
     */
    protected void toObjectOutputStream(ObjectOutputStream oout) {
        try {
            oout.writeUTF(string);
            oout.writeObject(time);
            oout.writeUTF(employee);
            oout.writeUTF(doctor);
        }
        catch(Exception e) {e.printStackTrace();}
    }


    /**
     * Returns the content of the packet as String.
     *
     * @return Returns the content of the packet as String.
     */
    public String toString() {
        return "\nString: " + string + "\nSource: " + employee + "\nDestination: " + doctor;
    }

    /**
     * Returns the string contained in the packet.
     *
     * @return Returns the string contained in the packet.
     */
    public String getString() {
        return string;
    }

    /**
     * Returns the time contained in the packet.
     *
     * @return Returns the time contained in the packet.
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * Returns the source address contained in the packet.
     *
     * @return Returns the source address contained in the packet.
     */
    public String getEmployee() {
        return employee;
    }

    /**
     * Returns the destination address contained in the packet
     * @return Returns the destination address contained in the packet
     */
    public String getDoctor(){ return doctor; }
}
