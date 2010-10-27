package totembank;

import java.util.ArrayList;
import java.util.List;

class Gateway extends Process {
	private List<GatewayRing> rings;

	public Gateway() {
		super();
		rings = new ArrayList<GatewayRing>();
	}

	public void receive(Message msg) {
		// If we get a token then forward any pending messages
		// Otherwise queue it in all other rings
		if (msg instanceof Token) {
			forward((Token)msg);
		} else {
			queue(msg);
		}
		super.receive(msg);
	}

	private void forward(Token token) {
		for (GatewayRing ring : rings) {
			// If we have the token for this ring broadcast any queued messages
			if (ring.ringID == token.ringID) {
				for (Message msg : ring.queuedMessages) {
					// Assign new sequence number based on this ring's token and broadcast
					token.seqNum++;
					msg.seqNum = token.seqNum;
					ring.broadcast(msg);
				}
				ring.queuedMessages.clear();
			} 
		}
	}

	private void queue(Message msg) {
		for (GatewayRing ring : rings) {
			// If the message isn't from this ring we need to forward it
			// Queue it for when we get the token
			if (ring.ringID != msg.ringID) {
				ring.queuedMessages.add(msg);
			}
		}
	}

	private class GatewayRing extends Ring {
		List<Message> queuedMessages;

		public GatewayRing() {
			super();
			queuedMessages = new ArrayList<Message>();
		}

		protected void broadcast(Message msg) {
			// Ensure that this message is properly branded as from this ring
			msg.ringID = ringID;
			super.broadcast(msg);
		}
	}
}
