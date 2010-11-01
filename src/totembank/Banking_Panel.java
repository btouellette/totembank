package totembank;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;

public class Banking_Panel extends JPanel{

	private static final long serialVersionUID = 6570309247216454426L;

	private JList userList;
	private JScrollPane userListPane;
	private JLabel labelOfList;
	private JButton buttonSelect;
	private Banking_PwdDialog pwdialog;
	private Banking_TransDialog tDialog;
	public Banking_Panel(){
		this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		this.setPreferredSize(new Dimension(400,400));
		setLayout(null);
		addBankingComponents();
	}
	private void addBankingComponents() {
		
		
		labelOfList = new JLabel("Current users in system");
		labelOfList.setBounds(12, 11, 446, 14);
		this.add(labelOfList);
		
		userListPane = new JScrollPane();
		userListPane.setBounds(12, 27, 56, 91);
		this.add(userListPane);
		
		userList = new JList(new Banking_List_Model());
		userListPane.setViewportView(userList);
		userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
		//tDialog = new TransactionDialog();
		
		pwdialog = new Banking_PwdDialog();
		pwdialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		pwdialog.addWindowListener(new WindowAdapter(){

			public void windowDeactivated(WindowEvent e){
				if(pwdialog.getCurrentPin()!=0){
					Banking_List_Model blm = (Banking_List_Model)userList.getModel();
					UserPin up = (UserPin)blm.get(userList.getSelectedIndex());
					if(up.getUserPin() != pwdialog.getCurrentPin()){
						JOptionPane.showMessageDialog(null, "The entered pin value .does not match!");
					}
					else{
						tDialog = new Banking_TransDialog();
						tDialog.addWindowListener(new WindowAdapter(){
							public void windowDeactivated(WindowEvent e){
								//String v = tDialog.getNewField();
								if(!tDialog.getNewField().equals("")){
									((Banking_List_Model)userList.getModel()).
									getBalance().get(userList.getSelectedIndex()).setAmount(Double.valueOf(tDialog.getNewField()));
								}	
							}
						});
						tDialog.setCurrentField(blm.getBalance().get(userList.getSelectedIndex()).getAmount());
						tDialog.pack();
						tDialog.setVisible(true); 
					}
					pwdialog.resetCurrentPin();
					
				}
				
			 }
		});
		
		buttonSelect = new JButton("Select this user");
		buttonSelect.setBounds(10, 129, 127, 23);
		buttonSelect.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(!userList.isSelectionEmpty()){
					pwdialog.pack();
					pwdialog.setVisible(true);
				}
				else{
					JOptionPane.showMessageDialog(null, "No user selected!!");
				}
			}
		});
		this.add(buttonSelect);	
	}
	
}
