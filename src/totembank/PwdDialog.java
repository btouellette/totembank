package totembank;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JFrame;

public class PwdDialog extends JDialog{

	private static final long serialVersionUID = 8755657995365933230L;
	
	private JTextField pwdForm;
	private JLabel pwdLabel;
	private Integer currentPin = 0;
	
	public PwdDialog(){
		super((JFrame)null, "Password Authentication");
		pwdForm = new JTextField(20);
		pwdForm.setBounds(141, 34, 146, 20);
		pwdForm.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				currentPin = 0;
				String text = pwdForm.getText();
				pwdForm.setText(null);
				if(!text.matches("-?\\d+")){
					JOptionPane.showMessageDialog(null, "Invalid input!!!");	
				}
				else{
					if(text.length()!=4){
						JOptionPane.showMessageDialog(null, "Pin must be 4 digits!!!");
					}
					else{
						currentPin = Integer.parseInt(text);
						PwdDialog.this.setVisible(false);
					}
					
				}	
			}
		});
		getContentPane().setLayout(null);
		this.getContentPane().add(pwdForm);
		
		pwdLabel = new JLabel("Enter your 4 digit pin below");
		pwdLabel.setBounds(141, 11, 162, 14);
		this.getContentPane().add(pwdLabel);
		
		this.setModalityType(DEFAULT_MODALITY_TYPE);
		this.setLocation(new Point(100,100));
		this.setPreferredSize(new Dimension(400,400));
		
	}
	
	public int getCurrentPin(){
		return currentPin.intValue();
	}
	public void resetCurrentPin(){
		currentPin = 0;
	}

}
