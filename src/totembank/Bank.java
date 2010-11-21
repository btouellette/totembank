package totembank;

import java.util.HashMap;
import java.util.HashSet;

public class Bank {

	private static HashMap<Integer,Account> accounts;
	private static HashSet<Login> users;
	
	static private Bank instance;
	
	private Bank(){
		accounts = new HashMap<Integer,Account>();
		users = new HashSet<Login>();
		initAccounts();
		initLogin();
	}
	public static Bank getInstance(){
		if(instance == null){
			instance = new Bank();
		}
		return instance;
	}
	
	public void addNewAccount(int pin, Account ua){
		accounts.put(pin, ua);
	}
	
	private void initAccounts(){
		addNewAccount(1111, new Account("Brian",912022775,31.00));
		addNewAccount(2222, new Account("Quentin",912023772,310.00));
		addNewAccount(3333, new Account("Paul",912024660,3100.00));
		addNewAccount(4444, new Account("Adith",912024110,31000.00));
	}
	
	public void addNewLogin(Login log){
		users.add(log);
	}
	
	private void initLogin(){
		addNewLogin(new Login("bouellette","bigdog106",1111));
		addNewLogin(new Login("qcat","tarantino",2222));
		addNewLogin(new Login("paulM","pm",3333));
		addNewLogin(new Login("adawg","balderdash",4444));
	}
	
	public boolean isValidLogin(Login log){
		for(Login login : users) {
			if(login.equals(log)){
				return true;
			}
		}
		return false;
	}
	/*Encryption scheme */
	public void sendTransaction(){
		//MultipleRing.send(sendString);
	}
	
	public void deliver(String m){
		
	}
	
}

class Login{
	private String username;
	private String password;
	private int pin;
	
	public Login(String u, String p, int pin){
		setUsername(u);
		setPassword(p);
		this.setPin(pin);
	}

	public boolean equals(Login o){
		if(o.username.equals(this.username) && o.password.equals(this.password)){
				return true;
		}
		return false;
	}
	/* getters and setters */
	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public int getPin() {
		return pin;
	}

	
}

class Account{
	
	private String accntName;
	private int accntNum;
	private double balance;
	
	public Account(String name, int anum, double bal){
		accntName = name;
		accntNum = anum;
		balance = bal;
	}
	public String getUserName() {
		return accntName;
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
}