package totembank;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

public class TransactionDialog extends JDialog{

	private static final long serialVersionUID = 5793932589254061192L;
	private JTextField currentField;
	private JTextField transField;
	private JTextField newField;

	public TransactionDialog(){
		super((JFrame)null,"Transaction");
		getContentPane().setLayout(null);
		this.setPreferredSize(new Dimension(600,400));
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setModalityType(DEFAULT_MODALITY_TYPE);
		
		JLabel lblCurrentBalance = new JLabel("CURRENT BALANCE");
		lblCurrentBalance.setBounds(10, 17, 130, 14);
		getContentPane().add(lblCurrentBalance);
		
		currentField = new JTextField();
		currentField.setBounds(207, 14, 187, 20);
		getContentPane().add(currentField);
		currentField.setColumns(10);
		currentField.setEditable(false);
		
		JLabel lblTransactionAmount = new JLabel("TRANSACTION AMOUNT");
		lblTransactionAmount.setBounds(10, 45, 152, 14);
		getContentPane().add(lblTransactionAmount);
		
		transField = new JTextField();
		transField.setBounds(207, 42, 187, 20);
		getContentPane().add(transField);
		transField.setColumns(10);
		transField.setEditable(false);
		
		JLabel lblNewBalance = new JLabel("NEW BALANCE");
		lblNewBalance.setBounds(10, 90, 130, 14);
		getContentPane().add(lblNewBalance);
		
		newField = new JTextField();
		newField.setBounds(207, 87, 187, 20);
		getContentPane().add(newField);
		newField.setColumns(10);
		newField.setEditable(false);
		
		JButton btnW = new JButton("WITHDRAWAL");
		btnW.setBounds(10, 138, 130, 23);
		getContentPane().add(btnW);
		
		JButton btnD = new JButton("DEPOSIT");
		btnD.setBounds(207, 138, 121, 23);
		getContentPane().add(btnD);
		
		JButton btnClose = new JButton("CLOSE TRANSACTIONS");
		btnClose.setBounds(87, 228, 170, 23);
		getContentPane().add(btnClose);
		
		btnW.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				String withdraw = JOptionPane.showInputDialog(TransactionDialog.this, "Enter Withdrawal Amount");
				if(withdraw !=null){
					if(!withdraw.matches("[0-9]*.?[0-9]+")){
						JOptionPane.showMessageDialog(null, "Invalid input!!!");	
					}
					else{
						if(Double.valueOf(withdraw)>Double.valueOf(currentField.getText())){
							JOptionPane.showMessageDialog(null, "Withdrawal amount higher than balance.");
						}
						else{
							transField.setText("-" + withdraw);
							newField.setText(String.valueOf(Double.valueOf(currentField.getText())-Double.valueOf(withdraw)));
							
							// have to communicate this new balance
							// multicast newField.gettext by starting a new thread that obtains the lock of process.java an updates client message
							// have another thread that is listening for incoming messages from process.java
						}
					}
				}
					
			}
		});
		
		btnD.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				String deposit = JOptionPane.showInputDialog(TransactionDialog.this, "Enter Deposit Amount");
				if(!deposit.matches("[0-9]*.?[0-9]+")){
					JOptionPane.showMessageDialog(null, "Invalid input!!!");	
				}
				else{
					if(Double.valueOf(deposit)>Double.valueOf(currentField.getText())){
						JOptionPane.showMessageDialog(null, "Withdrawal amount higher than balance.");
					}
					else{
						transField.setText("+" + deposit);
						newField.setText(String.valueOf(Double.valueOf(currentField.getText())+Double.valueOf(deposit)));
					}
				}
			}
		});
		
		btnClose.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				TransactionDialog.this.setVisible(false);
			}
		});
	}
	

	public void setCurrentField(double amount){
		currentField.setText(String.valueOf(amount));
	}
	
	public String getNewField(){
		return newField.getText();
	}
}
