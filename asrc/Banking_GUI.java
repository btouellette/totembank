package totembank;
import javax.swing.JFrame;



public class Banking_GUI extends JFrame{

	private static final long serialVersionUID = -6698025393431878736L;

	public Banking_GUI(){
		this.setTitle("ATM Transaction Interface");
	}
	public static void main(String[] args) {
	
		Banking_GUI bgui = new Banking_GUI();
		bgui.add(new Banking_Panel());
		bgui.pack();
		
	}

}
