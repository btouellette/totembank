package totembank;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Banking_GUI extends JFrame{

	private static final long serialVersionUID = 1893299294062806697L;

	public Banking_GUI(){
		this.setTitle("Totem Banking Application Login");
		Bank.getInstance();		// initalize bank account information
		Banking_Panel bpanel = new Banking_Panel();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().add(bpanel);
		this.pack();
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		        new Banking_GUI();
		    }
		});
	}	
}
