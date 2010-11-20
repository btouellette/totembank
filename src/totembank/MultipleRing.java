package totembank;

import java.util.HashMap;
import java.util.List;

import totembank.Process.Ring;

public class MultipleRing {
	private HashMap<Integer, List<Message>> receivedMessages;

	// Getting a message from the single ring protocol
    static void receive(Message m) {
    	// Forward to other rings
    	HashMap<Integer, Ring> rings = Process.getInstance().getRings();
    	for(Integer ringID : rings.keySet()) {
    		if(ringID != m.ringID) {
    			rings.get(ringID).getSingleRing().send(m);
    		}
    	}
    }

    // Send the message to the application
    static void send(Message m) {
    	//TODO: get with Adith to figure out how he wants this
        System.out.println("Message " + m.seqNum + " on ring " + m.ringID + " delivered to application");
    }
}
