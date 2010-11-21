package totembank;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.JButton;

public class Banking_Panel extends JPanel{
	
	public Banking_Panel() {
		setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		this.setPreferredSize(new Dimension(700,300));
		setLayout(null);
		
		JLabel lblUserName = new JLabel("Please Enter Your Username :");
		lblUserName.setBounds(10, 100, 229, 23);
		add(lblUserName);
		
		textField = new JTextField();
		textField.setBounds(249, 101, 157, 20);
		add(textField);
		textField.setColumns(10);
		textField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a){
				
			}
		});
		
		JLabel lblPwd = new JLabel("Please Enter Your Password :");
		lblPwd.setBounds(10, 149, 229, 23);
		add(lblPwd);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(249, 150, 157, 20);
		add(passwordField);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(261, 181, 89, 23);
		add(btnLogin);
		btnLogin.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				boolean isValid = Bank.getInstance().isValidLogin(new Login(textField.getText(),String.valueOf(passwordField.getPassword()),-1));
				if(!isValid){
					JOptionPane.showMessageDialog(null, "Invalid username/password combo! Login Failed.");
				}
				else{
					Banking_Panel.this.setVisible(false);
					
				}
				
			}
		});
		
		JLabel lblWelcomeToTotem = new JLabel("WELCOME TO TOTEM BANK OF GEORGIA TECH");
		lblWelcomeToTotem.setBounds(58, 11, 292, 14);
		add(lblWelcomeToTotem);
		
		JLabel img_label = new JLabel(new ImageIcon("totem.jpg"));
		img_label.setBounds(416, 11, 238, 252);
		add(img_label);
		
		Banking_PwdDialog bpdialog = new Banking_PwdDialog();
		
	}
	
	

	private static final long serialVersionUID = 6570309247216454426L;
	private JTextField textField;
	private JPasswordField passwordField;
}
