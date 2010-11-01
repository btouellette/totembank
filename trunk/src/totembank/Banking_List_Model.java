package totembank;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

public class Banking_List_Model extends DefaultListModel{

	private static final long serialVersionUID = -6927758244571794565L;

	private ArrayList<Balance> balance;
	public Banking_List_Model(){
		initList();
	}
	private void initList() {
		
		balance = new ArrayList<Balance>();
		balance.add(new Balance(3.15,new UserPin("Brian",1111)));
		balance.add(new Balance(31.5,new UserPin("Quentin",2222)));
		balance.add(new Balance(315,new UserPin("Paul",3333)));
		balance.add(new Balance(100000,new UserPin("Adith",4444)));

		this.addElement(balance.get(0).getUserPin());
		this.addElement(balance.get(1).getUserPin());
		this.addElement(balance.get(2).getUserPin());
		this.addElement(balance.get(3).getUserPin());		
		
	}
	
	public ArrayList<Balance> getBalance(){
		return balance;
	}
	
	public Object getElementAt(int arg0){
		return ((UserPin)this.get(arg0)).getUserName();
	}
	
	
}

class UserPin{
	
	private String userName;
	private int userPin;
	
	public UserPin(String name, int pin){
		userName = name;
		userPin = pin;
	}
	public String getUserName() {
		return userName;
	}
	public int getUserPin() {
		return userPin;
	}
	
	public boolean equals(Object o){
		return false;
		
	}
}

class Balance{
	private double amount;
	private UserPin pin;
	
	public Balance(double a, UserPin p){
		amount = a;
		pin = p;
	}
	
	public double getAmount(){
		return amount;
	}
	
	public UserPin getUserPin(){
		return pin;
	}
	
	public void setAmount(double a){
		amount = a;
	}
}
