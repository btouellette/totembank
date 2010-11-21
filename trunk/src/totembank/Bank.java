package totembank;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Bank {

	private static HashMap<Integer,Account> accounts;
	private static HashSet<Login> users;
	
	static private Bank instance;

    Cipher ecipher;
    Cipher dcipher;
	
	private Bank(){
		accounts = new HashMap<Integer,Account>();
		users = new HashSet<Login>();
		initAccounts();
		initLogin();
		try {
			SecretKey key = new SecretKeySpec("14835925932".getBytes(), "DES");
            ecipher = Cipher.getInstance("DES");
            dcipher = Cipher.getInstance("DES");
            ecipher.init(Cipher.ENCRYPT_MODE, key);
            dcipher.init(Cipher.DECRYPT_MODE, key);

        } catch (javax.crypto.NoSuchPaddingException e) {
        } catch (java.security.NoSuchAlgorithmException e) {
        } catch (java.security.InvalidKeyException e) {
        }
		byte[] keyBytes = new byte[1024];
		Random r = new Random(23305284);
		r.nextBytes(keyBytes);
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
    public String encrypt(String str) {
        try {
            // Encode the string into bytes using utf-8
            byte[] utf8 = str.getBytes("UTF8");
            // Encrypt
            byte[] enc = ecipher.doFinal(utf8);
            // Encode bytes to base64 to get a string
            return new sun.misc.BASE64Encoder().encode(enc);
        } catch (javax.crypto.BadPaddingException e) {
        } catch (IllegalBlockSizeException e) {
        } catch (UnsupportedEncodingException e) {
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
        } catch (IllegalBlockSizeException e) {
        } catch (UnsupportedEncodingException e) {
        } catch (java.io.IOException e) {
        }
        return null;
    }
    
	public void sendTransaction(){
		//TODO: Set this
		String sendString = "";
		MultipleRing.send(encrypt(sendString));
	}
	
	public void deliver(String m){
		String message = decrypt(m);
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