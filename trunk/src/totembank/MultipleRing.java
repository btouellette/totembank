package totembank;

import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import totembank.Process.Ring;

public class MultipleRing {
	// List of messages received stored by source ring
	private static ConcurrentHashMap<Integer, List<Message>> receivedMessages = new ConcurrentHashMap<Integer, List<Message>>();
	// The highest timestamp delivered on each ring to the application
	// Used to prevent multiple paths in the topology from causing multiple deliveries
	private static ConcurrentHashMap<Integer, Long> mostRecentTimestamp = new ConcurrentHashMap<Integer, Long>();

	static void addRingID(Integer ringID){
		receivedMessages.put(ringID, new ArrayList<Message>());
	}
	
	static void send(String sendString) {
		HashMap<Integer, Ring> processRings = Process.getInstance().getRings();
		Message msg = new Message();
		int ringID = processRings.keySet().iterator().next();
		msg.message = sendString;
		processRings.get(ringID).getSingleRing().send(msg);

    }

	static void sendGV(String sendString) {
		HashMap<Integer, Ring> processRings = Process.getInstance().getRings();
		for(Integer ringID : processRings.keySet()) {
			Message msg = new Message();
			msg.message = sendString;
			processRings.get(ringID).getSingleRing().send(msg);
		}
    }
	
	// Getting a message from the single ring protocol
    static void receive(Message m) {
    	// Forward to other rings
    	HashMap<Integer, Ring> processRings = Process.getInstance().getRings();
    	for(Integer ringID : processRings.keySet()) {
    		if(!m.ringIDs.contains(ringID)) {
                        Message m2 = m.clone();
                        m2.oriSeqNum = m.seqNum;
    			processRings.get(ringID).getSingleRing().send(m2);

    		}
    	}
    	// Add it to the proper list in receivedMessages
    	receivedMessages.get(m.ringIDs.get(0)).add(m);
    }
    
    // Check whether we can deliver any messages on to the application
    static void deliver() {
    	Message earliest = null;
    	int earliestRing = 0;
    	for(Integer ringID : receivedMessages.keySet()) {
        	// If any of the rings are empty we can't deliver
    		if(receivedMessages.get(ringID).isEmpty()) {
    			return;
    		}
    		// Find the message with the lowest timestamp among all queued messages
    		for(Message m : receivedMessages.get(ringID)) {
    			if(earliest == null || m.tStamp < earliest.tStamp) {
    				earliest = m;
    				earliestRing = ringID;
    			}
    		}
    	}
    	// No list is empty so deliver earliest and unqueue it
    	// Ensure that it isn't a replicated message before delivery
    	receivedMessages.get(earliestRing).remove(earliest);
    	if(mostRecentTimestamp.get(earliestRing) == null ||
    	   mostRecentTimestamp.get(earliestRing) < earliest.tStamp) {
	    	mostRecentTimestamp.put(earliestRing, earliest.tStamp);
	    	deliver(earliest);
    	}
    }

    // Send the message to the application
    static void deliver(Message m) {
    	if(m.message.equals("GV")) {
    		//System.out.println("GV " + m.seqNum + " on ring " + m.ringIDs.get(0) + " delivered to application");
    	    return;
    	}
        else{
    	Bank.getInstance().deliver(m.message);
        System.out.println("Message " + m.oriSeqNum + " on ring " + m.ringIDs.get(0) + " delivered to application");
        //System.out.println(m.message);
        }
    }
    
    static int GVcount = 0;
	static void guaranteeVector() {
		if(GVcount == 9) {
			sendGV("GV");
			GVcount = 0;
		} else {
			GVcount++;
		}
	}
}
