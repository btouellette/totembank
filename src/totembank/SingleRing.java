package totembank;

import java.util.*;

/**
 * This class implements the Single Ring Protocol
 */
public class SingleRing {

    /** List of message in the queue to be sent when the process will handle the token*/
    List<Message> queuedMessages;
    /** List of messages received*/
    List<Message> receivedMessages;
    /** List of messages received but not delivered */
    List<Message> notDeliveredMessages;
    /** Token. Null when the process does not handle the token*/
    Token token = null;
    /** Ring id*/
    int ring;
    /** Maximum Sequence Number already received*/
    int maxReceived = 0;
    /** Id of the process*/
    int processid;
    /** Sequence number of the token the last time it was handled by the process*/
    int lastseqNum = -1;
    /** Sequence Number of the last delivered message*/
    int lastDelivered = 0;
    /** Counter to artificially creates some losses for test. Not used in running application*/
    int count = 0;
    /** Last sequence number required*/
    int maxRequired = 0;
    /** Last token is store in case of node failure */
    Token oldtoken =null;
    /** Maximum number of messages to be sent when the node has the token*/
    int MaxMessagestoSend = 20;

    /**Constructor. Set ring id and process id and creates empty lists
    @param ring Ring ID
    @param id Process Id*/
    public SingleRing(int ring, int id) {
        queuedMessages = new ArrayList<Message>();
        receivedMessages = new ArrayList<Message>();
        notDeliveredMessages = new ArrayList<Message>();
        this.ring = ring;
        processid = id;


    }

    /** Add a message to queue of message. Called by upper layer
    @param m Message to be sent*/
    public void send(Message m) {
        m.ringID = ring;
        queuedMessages.add(m);
        System.out.println("Message queued");
    }

    /** Send all messages queued. Call when the process handles the token*/
    private void send() {
        int sendcount = 0;
        List<Message> temp = new ArrayList<Message>();
        if (token != null) {
            for (Message m : queuedMessages) {
                token.incSeqNum();
                m.seqNum = token.getSeqNum();
                m.tStamp = System.currentTimeMillis() + Process.getInstance().timeOffset();
                Process.getInstance().getRing(ring).getBLayer().send(m);
                temp.add(m);
                sendcount++;
                if (sendcount < MaxMessagestoSend){

                    break;
                }
            }
            queuedMessages.removeAll(temp);
        }
    }

    /** Perform retransmission of messages requested in the retransmission list of the token
    @param retranList List of Retransmission Request of the token*/
    public void retransmit(ArrayList<RetranReq> retranList) {
        for (RetranReq r : retranList) {
            for (Message m : receivedMessages) {
                if (m.seqNum == r.seqNum) {
                    Process.getInstance().getRing(ring).getBLayer().send(m);
                    System.out.println("Retransmission of " + m.seqNum);
                }
            }
        }
    }

    /** Determines if a message was not received and add a retransmission request to the token*/
    public void request() {
        int i = Math.max(maxRequired + 1, maxReceived + 1);
        RetranReq req;
        while (i <= token.getSeqNum()) {
            req = new RetranReq(processid, i);
            token.addReq(req);
            System.out.println("Request Retransmission of message " + i);
            maxRequired = i;
            i++;
        }
    }

    /** Checks if retransmission request done by this process has to be cleared or not. If yes, clears it.
    @param Retransmission List of the token*/
    public void clearReq(ArrayList<RetranReq> ret) {
        ArrayList<RetranReq> list = new ArrayList<RetranReq>();
        for (RetranReq r : ret) {
            if (r.idprocess == processid) {
                for (Message m : receivedMessages) {
                    if (m.seqNum == r.seqNum) {
                        list.add(r);
                        System.out.println("Retransmission request of message " + m.seqNum + " removed");
                    }
                }
            }
        }
        ret.removeAll(list);
    }

    /** Derives the new aru field value of the token*/
    public void aru() {
        int min = lastseqNum + 1;
        for (RetranReq r : token.getRetranList()) {
            if (r.seqNum < min) {
                min = r.seqNum;
            }
        }
        if (min - 1 > token.getAru()) {
            token.setAru(min - 1);
        }

    }

    /** Check if some received messages has to be delivered and delivers them*/
    public void deliver() {
        boolean delivering = false;
        Message mdelivered = null;
        for (Message m : notDeliveredMessages) {
            if (m.seqNum == lastDelivered + 1 && m.seqNum <= token.getAru()) {
                deliver(m);
                lastDelivered++;
                delivering = true;
                mdelivered = m;
            }
        }
        if (delivering) {
            notDeliveredMessages.remove(mdelivered);
            deliver();
        }
    }

    /** Deliver message to the upper layer
    @param m Message to be delivered*/
    private void deliver(Message m) { //TO DO here is just test implementation
    	MultipleRing.receive(m);
        System.out.println("Message " + m.seqNum + " on ring " + ring + " delivered to multiring");
    }

    /** Check if a message has already been received
    @param m Message
    @return true if the message has been received before*/
    private boolean isReceived(Message m) {
        int n = m.seqNum;
        boolean res = false;
        for (Message msg : receivedMessages) {
            res = res || msg.seqNum == n;
        }
        return res;
    }

    /** Called when a message is received
     * If this message is a token, performs the following actions in sequence :
     * Clear the retransmission List of messages no more requested
     * Retransmit messages requested
     * Request messages not received
     * Update the aru field
     * Delivers messages received which has to be delivered due to aru field value
     * Send messages in the queue
     *
     * If not queue the message in the received messages list
     *
     * @param m Message received
     */
    public void receive(Message m) {
    	// Update our lamport clock if necessary
    	long currentTime = System.currentTimeMillis() + Process.getInstance().timeOffset();
    	if (m.tStamp > currentTime) {
    		Process.getInstance().increaseTimeOffset(m.tStamp - currentTime + 1);
    	}
        if (m instanceof Token) {
            token = (Token) m;
            ArrayList<RetranReq> ret = token.getRetranList();
            clearReq(ret);
            retransmit(ret);
            token.setRetranList(ret);
            request();
            aru();
            deliver();
            lastseqNum = token.getSeqNum();
            // token.checkValues();
            send();
            try {
				Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            token.setTimeStamp();
            oldtoken = token;
            Process.getInstance().getRing(ring).getBLayer().sendToken(token, processid);
            token = null;
        } else {
            if (processid == 2 && count <= 0) {
                count++;
            } // Creates artificial losses for testing. Set the processid chosen and the count value upper to 0 to create a loss
            else if (!isReceived(m)) {
                System.out.println("Message " + m.seqNum + " received");
                receivedMessages.add(m);
                notDeliveredMessages.add(m);
                if (m.seqNum == maxReceived + 1) {
                    maxReceived++;
                }
            }
        }
    }
}
