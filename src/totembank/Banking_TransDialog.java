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
import javax.swing.SwingWorker;

public class Banking_TransDialog extends JDialog{

	private static final long serialVersionUID = 5793932589254061192L;
	private JTextField currentField;
	private JTextField transField;
	private JTextField newField;
	private int currentPin;
	private boolean oldTransaction = false;

	public Banking_TransDialog(int pin){
		super((JFrame)null,"Transaction");
		currentPin = pin;
		getContentPane().setLayout(null);
		this.setMinimumSize(new Dimension(600,400));
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
		currentField.setText(String.valueOf(Bank.getInstance().getAccountList().get(currentPin).getBalance()));
		
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
				if(oldTransaction){
					currentField.setText(String.valueOf(Bank.getInstance().getAccountList().get(currentPin).getBalance()));
					transField.setText(null);
				}
				final String withdraw = JOptionPane.showInputDialog(Banking_TransDialog.this, "Enter Withdrawal Amount");
				if(withdraw !=null){
					if(!withdraw.matches("[0-9]*.?[0-9]+")){
						JOptionPane.showMessageDialog(null, "Invalid input!!!");	
					}
					else{
						if(Double.valueOf(withdraw)<0){
							JOptionPane.showMessageDialog(null, "Amount cannot be negative.");
						}
						if(Double.valueOf(withdraw)>Double.valueOf(currentField.getText())){
							JOptionPane.showMessageDialog(null, "Withdrawal amount higher than balance.");
						}
						else{
							transField.setText("-" + withdraw);
							oldTransaction = true;
							
							final JDialog waitForTrans = new JDialog((JFrame)null,true);
							waitForTrans.add(new JLabel("Updating balance in system. Please Wait..."));
							waitForTrans.setMinimumSize(new Dimension(300,100));
							waitForTrans.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
							
							SwingWorker<String,Integer> worker = new SwingWorker<String,Integer>(){
					
								protected String doInBackground() throws Exception {
									Bank.getInstance().sendTransaction(currentPin,"-"+withdraw);
									while(!Bank.getInstance().hasCompletedTransaction);
									return null;
									
								}
								
								protected void done(){
									waitForTrans.setVisible(false);
									newField.setText(String.valueOf(Bank.getInstance().getAccountList().get(currentPin).getBalance()));
								}	
							};
							worker.execute();
							waitForTrans.setVisible(true);
						}
					}
				}
					
			}
		});
		
		btnD.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(oldTransaction){
					currentField.setText(String.valueOf(Bank.getInstance().getAccountList().get(currentPin).getBalance()));
					transField.setText(null);
				}
				final String deposit = JOptionPane.showInputDialog(Banking_TransDialog.this, "Enter Deposit Amount");
				if(!deposit.matches("[0-9]*.?[0-9]+")){
					JOptionPane.showMessageDialog(null, "Invalid input!!!");	
				}
				else{
					if(Double.valueOf(deposit)<0){
						JOptionPane.showMessageDialog(null, "Cannot deposit negative number.");
					}
					else{
						transField.setText(deposit);
						oldTransaction = true;
						
						final JDialog waitForTrans = new JDialog((JFrame)null,true);
						waitForTrans.add(new JLabel("Updating balance in system. Please Wait..."));
						waitForTrans.setMinimumSize(new Dimension(300,100));
						waitForTrans.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	
						SwingWorker<String,Integer> worker = new SwingWorker<String,Integer>(){
						
							protected String doInBackground() throws Exception {
								Bank.getInstance().sendTransaction(currentPin,deposit);
								while(!Bank.getInstance().hasCompletedTransaction);
								return null;
							}
							
							protected void done(){
								waitForTrans.setVisible(false);
								newField.setText(String.valueOf(Bank.getInstance().getAccountList().get(currentPin).getBalance()));
							}
							
						};
						worker.execute();
						waitForTrans.setVisible(true);
					}
				}
			}
		});
		
		btnClose.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				currentPin = -1;
				Banking_TransDialog.this.setVisible(false);
				Banking_Panel.sessionOn = false;
			}
		});
		
		this.setVisible(true);
		
	}
}
