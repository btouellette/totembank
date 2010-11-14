/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package totembank;
import java.util.*;
/**
 *
 * @author Quentin
 */
public class SingleRing {

    List<Message> queuedMessages;
    List<Message> receivedMessages;
    List<Message> notDeliveredMessages;
    Token token = null;
    int ring;
    int maxReceived = 0;
    int processid;
    int lastseqNum=-1;
    int lastDelivered = 0;

    public SingleRing(int ring, int id){
        queuedMessages = new ArrayList<Message>();
        receivedMessages = new ArrayList<Message>();
        notDeliveredMessages = new ArrayList<Message>();
        this.ring = ring;
        processid = id;


    }

    public void send(Message m){
        m.ringID =ring;
        queuedMessages.add(m);
        System.out.println("Message queued");
    }
    private void send(){
        if (token != null){
            for (Message m : queuedMessages){
                token.incSeqNum();
                m.seqNum=token.getSeqNum();
                m.tStamp = System.currentTimeMillis();
                Process.getInstance().getRing(ring).getBLayer().send(m);
            }
            queuedMessages.clear();
        }
    }
    public void retransmit(ArrayList<RetranReq> retranList){
        for (RetranReq r : retranList){
            for (Message m : receivedMessages){
                if (m.seqNum== r.seqNum){
                   Process.getInstance().getRing(ring).getBLayer().send(m);
                }
            }
        }
    }
    public void request(){
        int i = maxReceived;
        RetranReq req;
        while (token.getSeqNum() > maxReceived){
            req = new RetranReq(processid, i);
            //token.addReq(req);
            i++;
        }
    }

    public void clearReq(ArrayList<RetranReq> ret){
        for (RetranReq r : ret){
            if (r.idprocess == processid){
                for (Message m : receivedMessages){
                    if (m.seqNum == r.seqNum){
                        ret.remove(r);
                    }
                }
            }
        }
    }

    public void aru(){
        int min= lastseqNum +1;
        for (RetranReq r : token.getRetranList()){
                if (r.seqNum <min){
                    min = r.seqNum;
            }
        }
        if (min-1 > token.getAru()){
            token.setAru(min-1);
        }

    }

    public void deliver(){
        boolean delivering = false;
        Message mdelivered= null;
        for (Message m : notDeliveredMessages){
            if (m.seqNum == lastDelivered +1 && m.seqNum <= token.getAru()){
                deliver(m);
                lastDelivered++;
                delivering= true;
                mdelivered = m;
            }
        }
        if (delivering){
            notDeliveredMessages.remove(mdelivered);
            deliver();
        }
    }

    private void deliver (Message m){
        System.out.println("Message "+m.seqNum+" on ring "+ ring+" delivered");
    }

    public void receive (Message m){

        if (m instanceof Token ){
            token = (Token) m;
            ArrayList ret = token.getRetranList();
            clearReq(ret);
            retransmit(ret);
            token.setRetranList(ret);
            //request();
            aru();
            deliver();
            lastseqNum = token.getSeqNum();
            token.checkValues();
            send();
            try {
            Thread.currentThread().sleep(1000);}
            catch (InterruptedException e){
                e.printStackTrace();
            }
            Process.getInstance().getRing(ring).getBLayer().sendToken(token, processid);
            token = null;
        }
        else{
            System.out.println("Message "+ m.seqNum+" received");
            receivedMessages.add(m);
            notDeliveredMessages.add(m);
            if (m.seqNum == maxReceived + 1){
                maxReceived++;
            }
        }
    }
}
