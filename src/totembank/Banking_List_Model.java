package totembank;

import javax.swing.DefaultListModel;

public class Banking_List_Model extends DefaultListModel{

	private static final long serialVersionUID = -6927758244571794565L;

	//private ArrayList<UserAccount> users;
	public Banking_List_Model(){
		initList();
	}
	private void initList() {
		
		this.addElement(new UserAccount("Brian",1111,912024775,300));
		this.addElement(new UserAccount("Quentin",2222,912023225,3000));
		this.addElement(new UserAccount("Paul",3333,912026789,30000));
		this.addElement(new UserAccount("Adith",4444,912024110,300000));
	}
	
	public Object getElementAt(int arg0){
		return ((UserAccount)this.get(arg0)).getUserName();
	}
	
	
}

class UserAccount{
	
	private String userName;
	private int userPin;
	private int accntNum;
	private double balance;
	
	public UserAccount(String name, int pin, int anum, int bal){
		userName = name;
		userPin = pin;
		accntNum = anum;
		balance = bal;
	}
	public String getUserName() {
		return userName;
	}
	public int getUserPin() {
		return userPin;
	}
	public int getAccountNumber(){
		return accntNum;
	}
	public double getBalance(){
		return balance;
	}
	public void setBalance(double balance){
		this.balance = balance;
	}
	public boolean equals(Object o){
		return false;
		
	}
}
