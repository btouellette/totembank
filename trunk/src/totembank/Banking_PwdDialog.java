package totembank;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class Banking_PwdDialog extends JDialog{

	private static final long serialVersionUID = 8755657995365933230L;
	
	private JPasswordField pwdForm;
	private JLabel pwdLabel;
	private Integer currentPin = 0;
	
	public Banking_PwdDialog(){
		super((JFrame)null, "Password Authentication");
		pwdForm = new JPasswordField(20);
		pwdForm.setBounds(141, 34, 146, 20);
		pwdForm.setEditable(false);
		getContentPane().setLayout(null);
		this.getContentPane().add(pwdForm);
		
		pwdLabel = new JLabel("Enter your 4 digit pin below");
		pwdLabel.setBounds(141, 11, 162, 14);
		this.getContentPane().add(pwdLabel);
		
		JButton btnNum1 = new JButton("1");
		btnNum1.setBounds(100, 76, 59, 51);
		getContentPane().add(btnNum1);
		btnNum1.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				String text;
				text = String.valueOf(Banking_PwdDialog.this.pwdForm.getPassword());
				if(text==null){
					Banking_PwdDialog.this.pwdForm.setText("1");
				}
				else{
					Banking_PwdDialog.this.pwdForm.setText(text+"1");
				}
				
			}
		});
		
		JButton btnNum2 = new JButton("2");
		btnNum2.setBounds(169, 76, 59, 51);
		getContentPane().add(btnNum2);
		btnNum2.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				String text;
				text = String.valueOf(Banking_PwdDialog.this.pwdForm.getPassword());
				if(text==null){
					Banking_PwdDialog.this.pwdForm.setText("2");
				}
				else{
					Banking_PwdDialog.this.pwdForm.setText(text+"2");
				}
				
			}
		});
		
		JButton btnNum3 = new JButton("3");
		btnNum3.setBounds(238, 76, 59, 51);
		getContentPane().add(btnNum3);
		btnNum3.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				String text;
				text = String.valueOf(Banking_PwdDialog.this.pwdForm.getPassword());
				if(text==null){
					Banking_PwdDialog.this.pwdForm.setText("3");
				}
				else{
					Banking_PwdDialog.this.pwdForm.setText(text+"3");
				}
				
			}
		});
		JButton btnNum4 = new JButton("4");
		btnNum4.setBounds(100, 138, 59, 51);
		getContentPane().add(btnNum4);
		btnNum4.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				String text;
				text = String.valueOf(Banking_PwdDialog.this.pwdForm.getPassword());
				if(text==null){
					Banking_PwdDialog.this.pwdForm.setText("4");
				}
				else{
					Banking_PwdDialog.this.pwdForm.setText(text+"4");
				}
				
			}
		});
		JButton btnNum5 = new JButton("5");
		btnNum5.setBounds(169, 138, 59, 51);
		getContentPane().add(btnNum5);
		btnNum5.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				String text;
				text = String.valueOf(Banking_PwdDialog.this.pwdForm.getPassword());
				if(text==null){
					Banking_PwdDialog.this.pwdForm.setText("5");
				}
				else{
					Banking_PwdDialog.this.pwdForm.setText(text+"5");
				}
				
			}
		});
		JButton btnNum6 = new JButton("6");
		btnNum6.setBounds(238, 138, 59, 51);
		getContentPane().add(btnNum6);
		btnNum6.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				String text;
				text = String.valueOf(Banking_PwdDialog.this.pwdForm.getPassword());
				if(text==null){
					Banking_PwdDialog.this.pwdForm.setText("6");
				}
				else{
					Banking_PwdDialog.this.pwdForm.setText(text+"6");
				}
				
			}
		});
		JButton btnNum7 = new JButton("7");
		btnNum7.setBounds(100, 200, 59, 51);
		getContentPane().add(btnNum7);
		btnNum7.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				String text;
				text = String.valueOf(Banking_PwdDialog.this.pwdForm.getPassword());
				if(text==null){
					Banking_PwdDialog.this.pwdForm.setText("7");
				}
				else{
					Banking_PwdDialog.this.pwdForm.setText(text+"7");
				}
				
			}
		});
		JButton btnNum8 = new JButton("8");
		btnNum8.setBounds(169, 200, 59, 51);
		getContentPane().add(btnNum8);
		btnNum8.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				String text;
				text = String.valueOf(Banking_PwdDialog.this.pwdForm.getPassword());
				if(text==null){
					Banking_PwdDialog.this.pwdForm.setText("8");
				}
				else{
					Banking_PwdDialog.this.pwdForm.setText(text+"8");
				}
				
			}
		});
		JButton btnNum9 = new JButton("9");
		btnNum9.setBounds(238, 200, 59, 51);
		getContentPane().add(btnNum9);
		btnNum9.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				String text;
				text = String.valueOf(Banking_PwdDialog.this.pwdForm.getPassword());
				if(text==null){
					Banking_PwdDialog.this.pwdForm.setText("9");
				}
				else{
					Banking_PwdDialog.this.pwdForm.setText(text+"9");
				}
				
			}
		});
		JButton btnEnter = new JButton("Enter");
		btnEnter.setBounds(335, 138, 89, 51);
		getContentPane().add(btnEnter);
		btnEnter.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				String text;
				text = String.valueOf(Banking_PwdDialog.this.pwdForm.getPassword());
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
						//Bank.getInstance().
						//Banking_PwdDialog.this.setVisible(false);
					}	
				}			
			}
		});
		
		JButton btnClose = new JButton("Close");
		btnClose.setBounds(335, 200, 89, 51);
		getContentPane().add(btnClose);
		btnClose.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				Banking_PwdDialog.this.setVisible(false);
			}
		});
		
		JButton btnBack = new JButton("<------");
		btnBack.setBounds(335, 76, 89, 51);
		getContentPane().add(btnBack);
		btnBack.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				String text;
				text = String.valueOf(Banking_PwdDialog.this.pwdForm.getPassword());
				if(text!=null){
					Banking_PwdDialog.this.pwdForm.setText(text.substring(0, text.length()-1));
				}
			}
		});
		
		
		this.setModalityType(DEFAULT_MODALITY_TYPE);
		this.setLocation(new Point(100,100));
		this.setPreferredSize(new Dimension(600,400));
		
	}
	
	public int getCurrentPin(){
		return currentPin.intValue();
	}
	public void resetCurrentPin(){
		currentPin = 0;
	}
}
