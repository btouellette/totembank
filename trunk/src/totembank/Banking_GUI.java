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
		long currentTime = System.currentTimeMillis();
		long timeLength = 800;
		Bank.getInstance().sendRandomMessage(0,currentTime,currentTime+timeLength);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().add(bpanel);
		this.pack();
		this.setVisible(true);
	}
		
}
