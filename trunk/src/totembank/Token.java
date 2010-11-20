package totembank;

import java.util.*;

public class Token extends Message{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int ringID;			//ring identifier
	public long tStamp;		//time stamp
	public int seqNum;			//sequence number
	//public ArrayList<RetranReq> aru;			//all received up to field
	public int aru;
	public ArrayList<RetranReq> retransList;	//retransmission list
	
	public Token(){
		this(0);	
	}
	
	public Token(int ring){
		super(ring);
        ringID = ring;
		tStamp = System.currentTimeMillis() + Process.getInstance().timeOffset();
		seqNum = 0;
		//aru = new ArrayList<RetranReq>();
		aru = 0;
		retransList = new ArrayList<RetranReq>();
	}
	
	public int getRingID(){
		return ringID;
	}
	
	public long getTimeStamp(){
		return tStamp;
	}
	
	public void setTimeStamp(){
		tStamp = System.currentTimeMillis() + Process.getInstance().timeOffset();
	}
	
	public int getSeqNum(){
		return seqNum;
	}
	
	public void incSeqNum(){
		this.seqNum++;
	}
	
	public void setSeqNum( int seqNum ){
		this.seqNum = seqNum;
	}
	
	//public ArrayList<RetranReq> getAru(){
	//	return aru;
	//}
	
	public int getAru(){
		return aru;
	}
	
//	public void setAru( ArrayList<RetranReq> aru ){
//		this.aru = aru;
//	}
	
	public void setAru( int aru){
		this.aru = aru;
	}
	
	public ArrayList<RetranReq> getRetranList(){
		return retransList;
	}
        public void addReq(RetranReq r){
            retransList.add(r);
        }
	
	public void setRetranList( ArrayList<RetranReq> retransList ){
		this.retransList = retransList;
	}
	public String retransListToString(){
            String s = "";
            for (RetranReq r: retransList){
                s = s+"("+r.idprocess+", "+ r.seqNum+") ";
            }
            return s;
        }

	public void checkValues(){
		System.out.println("Ring ID: " + ringID);
		System.out.println("Time Stamp: " + tStamp);
		System.out.println("Sequence Number: " + seqNum);
		System.out.println("ARU: " + aru);
		System.out.println("Retransmission List: " + retransListToString());
	}

}
