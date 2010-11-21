package totembank;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/** This class defines messages. Messages can be standard message or token or Gateway Messages.
 * Messages has to be serializable to be sent.
 */
public class Message implements Serializable, Cloneable {

    /** Used for serialization*/
    private static final long serialVersionUID = 154634656545456L;
    /** IDs of the rings the message was in */
    /** */
    List<Integer> ringIDs = new ArrayList<Integer>();
    /** Sequence Number within the local ring */
    int seqNum = -1;
    /** Timestamp*/
    long tStamp;
    String message;
    
    public Message() {
        tStamp = System.currentTimeMillis() + Process.getInstance().timeOffset();
    }

    /** Constructor
    @param ring id of the origin ring*/
    public Message(int ring) {
        ringIDs.add(ring);
        tStamp = System.currentTimeMillis() + Process.getInstance().timeOffset();
    }
    
    protected Message clone() {
    	Message copy = new Message();
    	copy.ringIDs = new ArrayList<Integer>(ringIDs);
    	copy.seqNum = seqNum;
    	copy.tStamp = tStamp;
    	copy.message = message;
    	return copy;
    }
}
