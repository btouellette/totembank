package totembank;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Bank {

	private static HashMap<Integer,Account> accounts;
	private static HashSet<Login> users;	
	private static Bank instance;
	private static int bankID;
	
	private double testCount;
	public boolean hasCompletedTransaction;
	public boolean hasTransactionFailed = false;
	
    Cipher ecipher;
    Cipher dcipher;

	private Bank(){
		accounts = new HashMap<Integer,Account>();
		users = new HashSet<Login>();
		bankID = (int) Math.round(Math.random()*100 + Math.random()*100); 
		initAccounts();
		initLogin();
		try {
			// This key must be 16 bytes
			SecretKey key = new SecretKeySpec("eS^@M?DV1,!3'gyu".getBytes(), "AES");
            ecipher = Cipher.getInstance("AES");
            dcipher = Cipher.getInstance("AES");
            ecipher.init(Cipher.ENCRYPT_MODE, key);
            dcipher.init(Cipher.DECRYPT_MODE, key);
        } catch (javax.crypto.NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (java.security.InvalidKeyException e) {
            e.printStackTrace();
        }
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
	
	public boolean isValidPin(int pin,String user){
		for(Login log: users){
			if(log.getPin()==pin && log.getUsername().equals(user)){
				return true;
			}
		}
		return false;
	}
	
	/*Encryption scheme */
    public String encrypt(String str) {
        try {
            // Encode the string into bytes using utf-8
            byte[] utf8 = str.getBytes("UTF8");
            // Encrypt
            byte[] enc = ecipher.doFinal(utf8);
            // Encode bytes to base64 to get a string
            return new sun.misc.BASE64Encoder().encode(enc);
        } catch (javax.crypto.BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String decrypt(String str) {
        try {
            // Decode base64 to get bytes
            byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
            // Decrypt
            byte[] utf8 = dcipher.doFinal(dec);
            // Decode using utf-8
            return new String(utf8, "UTF8");
        } catch (javax.crypto.BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
	public void sendTransaction(int pin, String withdraw){
		hasCompletedTransaction = false;
		String sendString = String.valueOf(bankID) + " " +
			String.valueOf(accounts.get(pin).getAccountNumber())+" "+ withdraw;
		MultipleRing.send(encrypt(sendString));
	}
	
	public void deliver(String key){
		String message = decrypt(key);
		Pattern p = Pattern.compile("([0-9]*) ([0-9]*) (-?[0-9]*.?[0-9]+)");
		Matcher m = p.matcher(message);
		if(m.matches()){
			int id = Integer.valueOf(m.group(1));
			int acntNum = Integer.valueOf(m.group(2));
			double amount = Double.valueOf(m.group(3));
			
			for(Account a: accounts.values()){
				if(a.getAccountNumber()==acntNum){
					if((a.getBalance() < Math.abs(amount)) && amount < 0){
						if(id==bankID){
							hasTransactionFailed = true;
						}
						
					}
					else{
						a.setBalance(a.getBalance()+amount);
					}
					if(id==bankID){
						hasCompletedTransaction = true;
					}
					break;
				}
			}
		}
		else{
			System.out.println("Something went wrong....");
		}
	}
	
	public HashMap<Integer,Account> getAccountList(){
		return accounts;
	}
	
	
	public void sendRandomMessage(double count,long startTime,final long endTime){
		
		if(System.currentTimeMillis()> endTime) {
			Timer timer = new Timer();
			timer.schedule(new TimerTask(){ 
				public void run(){
                                    System.out.println("Start send at "+ System.currentTimeMillis());
                                    for(Integer i : Bank.getInstance().getAccountList().keySet()) {
		        		//System.out.print(Bank.getInstance().getAccountList().get(i).getUserName() + ": ");
		        		//System.out.println(Bank.getInstance().getAccountList().get(i).getBalance());
		        	} 
				} 
			}, 50000);
			return;
		}
		testCount = Math.pow(-1, count);
		final double AMT;
		Random gen = new Random();
		if(gen.nextBoolean()) {
			int rand = gen.nextInt(10);
			if(rand > 3) { 
				AMT = -3000;
			} else {
				AMT = 3000;
			}
		} else {
			 AMT = 200*testCount;
		}
		Timer timer = new Timer();
		timer.schedule(new TimerTask(){ 
			public void run(){
				sendTransaction(3333,String.valueOf(AMT));
				long currentTime = System.currentTimeMillis();
				sendRandomMessage(testCount,currentTime,endTime); 
			} 
		}, Math.round(Math.random()*1000));
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