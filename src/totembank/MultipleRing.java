package totembank;

import java.util.HashMap;
import java.util.List;

import totembank.Process.Ring;

public class MultipleRing {
	private static HashMap<Integer, List<Message>> receivedMessages;

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
    	receivedMessages.get(m.ringID).add(m);
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
    	//TODO: get with Adith to figure out how he wants this
        System.out.println("Message " + m.seqNum + " on ring " + m.ringID + " delivered to application");
    }
}
