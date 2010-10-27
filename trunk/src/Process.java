package totembank;

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
		List<String> ringAddresses;

		public Ring() {
		}
	}
}
