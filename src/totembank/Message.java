package totembank;

import java.io.*;

/** This class defines messages. Messages can be standard message or token or Gateway Messages.
 * Messages has to be serializable to be sent.
 */
public class Message implements Serializable, Cloneable {

    /** Used for serialization*/
    private static final long serialVersionUID = 154634656545456L;
    /** ID of the ring of origin of the message */
    int ringIDOrigin;
    /** ID of the most recent ring the message was in */
    int ringID;
    /** Sequence Number within the local ring */
    int seqNum = -1;
    /** Timestamp*/
    long tStamp;
    
    public Message() {
        tStamp = System.currentTimeMillis() + Process.getInstance().timeOffset();
    }

    /** Constructor
    @param ring id of the origin ring*/
    public Message(int ring) {
        ringIDOrigin = ring;
        tStamp = System.currentTimeMillis() + Process.getInstance().timeOffset();
    }
    
    protected Message clone() {
    	Message copy = new Message();
    	copy.ringIDOrigin = ringIDOrigin;
    	copy.ringID = copy.ringID;
    	copy.seqNum = seqNum;
    	copy.tStamp = tStamp;
    	return copy;
    }
}
