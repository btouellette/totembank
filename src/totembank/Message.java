package totembank;

import java.io.*;

/** This class defines messages. Messages can be standard message or token or Gateway Messages.
 * Messages has to be serializable to be sent.
 */
public class Message implements Serializable {

    /** Used for serialization*/
    private static final long serialVersionUID = 154634656545456L;
    /** ID of the ring of origin of the message*/
    int ringID;
    /** Sequence Number within the local ring */
    int seqNum = -1;
    /** Timestamp*/
    long tStamp;

    /** @return Id of origin of the message*/
    public int getRingID() {
        return ringID;
    }

    /** Constructor
    @param ring id of the origin ring*/
    public Message(int ring) {
        ringID = ring;
        tStamp = System.currentTimeMillis() + Process.getInstance().timeOffset();
    }
}
