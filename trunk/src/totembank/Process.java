package totembank;

import java.util.List;

class Process {
	private List<Ring> ring;

	public Process() {
	}

	public void receive(Message msg) {
	}

	public void deliver(Message msg) {
	}

	protected class Ring {
		int ringID;
		List<Message> receivedMessages;
		List<String> ringAddresses;

		public Ring() {
		}

		protected void broadcast(Message msg) {
			//TODO: broadcast to all addresses
		}
	}
}
