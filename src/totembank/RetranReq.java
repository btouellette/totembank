package totembank;

import java.io.*;

/**
 * Define a tuple of two integers for a Retransmission Request
 */
public class RetranReq implements Serializable {

    private static final long serialVersionUID = -2640957957037913868L;
    /** Id of the process requesting the retransmission*/
    int idprocess;
    /** Sequence Number requested */
    int seqNum;

    public RetranReq(int id, int num) {
        idprocess = id;
        seqNum = num;
    }
}
