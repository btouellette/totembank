package totembank;

class Gateway extends Process {
    private List<Message> queue;
	private List<Ring> rings;

	public Gateway() {
		super();
		queue = new ArrayList<Message>();
	}

	public void receive(Message msg) {
		if (msg instanceof Token) {
			forward();
		}
		else {
			queue.add(msg);
		}
		super(msg);
	}

	private void forward(Message msg) {
		for (Message msg : queue) {
			for (Ring ring : rings) {
				if (ring.ringID != msg.ringID) {
				}
			}
		}
	}	
}
