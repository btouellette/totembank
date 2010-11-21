package totembank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import totembank.Process.Ring;

public class MultipleRing {
	private static HashMap<Integer, List<Message>> receivedMessages = new HashMap<Integer, List<Message>>();

	static void addRingID(Integer ringID){
		receivedMessages.put(ringID, new ArrayList<Message>());
	}
	
	static void send(String sendString) {
		HashMap<Integer, Ring> processRings = Process.getInstance().getRings();
		Message msg = new Message();
		msg.ringIDOrigin = processRings.keySet().iterator().next();
		msg.message = sendString;
    	for(Integer ringID : processRings.keySet()) {
    		msg.ringID = ringID;
			processRings.get(ringID).getSingleRing().send(msg.clone());
    	}
	}
	
	// Getting a message from the single ring protocol
    static void receive(Message m) {
    	// Forward to other rings
    	HashMap<Integer, Ring> processRings = Process.getInstance().getRings();
    	for(Integer ringID : processRings.keySet()) {
    		if(ringID != m.ringID) {
    			processRings.get(ringID).getSingleRing().send(m.clone());
    		}
    	}
    	// Add it to the proper list in receivedMessages
    	receivedMessages.get(m.ringIDOrigin).add(m);
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
    	receivedMessages.get(earliestRing).remove(earliest);
    	deliver(earliest);
    }

    // Send the message to the application
    static void deliver(Message m) {
    	Bank.getInstance().deliver(m.message);
        System.out.println("Message " + m.seqNum + " on ring " + m.ringIDOrigin + " delivered to application");
    }
}
