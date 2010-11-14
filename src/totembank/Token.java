package totembank;

import java.io.*;
import java.util.*;

public class Token extends Message{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ringID;			//ring identifier
	private long tStamp;		//time stamp
	private int seqNum;			//sequence number
	private int aru;			//all received up to field
	private ArrayList<RetranReq> retransList;	//retransmission list
	
	public Token(){
		this(0);	
	}
	
	public Token(int ring){
		super(ring);
                ringID = ring;
		tStamp = System.currentTimeMillis();
		seqNum = 0;
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
		tStamp = System.currentTimeMillis();
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
	
	public int getAru(){
		return aru;
	}
	
	public void setAru( int aru ){
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
	
	public void checkValues(){
		System.out.println("Ring ID: " + ringID);
		System.out.println("Time Stamp: " + tStamp);
		System.out.println("Sequence Number: " + seqNum);
		System.out.println("ARU: " + aru);
		System.out.println("Retransmission List: " + retransList);
	}


//	public static void main(String[] args) {
//
//		Thread t = new Thread(new ProcessNode());
//		t.start();
//
//		Token p = new Token(3);
//		p.checkValues();
//		ArrayList<Integer> myList = new ArrayList<Integer>(3);
//		myList.add(0);
//		myList.add(2);
//		myList.add(3);
//		p.setRetranList(myList);
//		p.setAru(2);
//		p.setSeqNum(1);
//		p.setTimeStamp();
//		p.checkValues();
//		FileOutputStream fos = null;
//		ObjectOutputStream out = null;
//		try
//		{
//			fos = new FileOutputStream("testing");
//			out = new ObjectOutputStream(fos);
//			out.writeObject(p);
//			out.close();
//		}
//		catch(IOException ex)
//		{
//			ex.printStackTrace();
//		}
//
//		Token q = null;
//		FileInputStream fis = null;
//		ObjectInputStream in = null;
//		try {
//			fis = new FileInputStream("testing");
//			in = new ObjectInputStream(fis);
//			q = (Token)in.readObject();
//			in.close();
//		}
//		catch(IOException ex)
//		{
//			ex.printStackTrace();
//		}
//		catch(ClassNotFoundException ex)
//		{
//			ex.printStackTrace();
//		}
//		System.out.println("*************************************");
//		q.checkValues();
//	}
}
