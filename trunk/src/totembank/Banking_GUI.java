package totembank;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;

public class Banking_GUI extends JFrame{

	private static final long serialVersionUID = 1893299294062806697L;

	public Banking_GUI(){
		this.setTitle("Totem Banking Application Login");
		Bank.getInstance();		// initalize bank account information
		Banking_Panel bpanel = new Banking_Panel();
		bpanel.addComponentListener(new ComponentAdapter(){
			public void componentHidden(ComponentEvent e){
				Banking_GUI.this.setVisible(false);
			}
			public void componentShown(ComponentEvent e){
				Banking_GUI.this.setVisible(true);
			}
			 
		});
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().add(bpanel);
		this.pack();
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		
		/*SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		        new Banking_GUI();
		    }
		});*/
	}	
}
