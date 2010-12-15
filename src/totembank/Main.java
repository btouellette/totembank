/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package totembank;
import java.io.* ;

import javax.swing.SwingUtilities;
/**
 *
 * @author Quentin
 */
public class Main {
    static int test;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
        	//ConfFileGenerator.generate();
            // Input the id of the process
        	System.out.println("Select the id of the process :");
            InputStreamReader reader=new InputStreamReader(System.in);
            BufferedReader entree=new BufferedReader(reader);
            String line=entree.readLine();
            int id = Integer.parseInt(line);
            new Process(id); 
            // Asking for token to be passed
            System.out.println("Press 1 to start token and 0 to not: ");
            reader=new InputStreamReader(System.in);
            entree=new BufferedReader(reader);
            line=entree.readLine();
            int startToken = Integer.parseInt(line);
           
            if (startToken==1){
            	Token t = new Token(test);
            	BottomLayer b = Process.getInstance().getRing(test).getBLayer();
            	b.sendToken(t, id);
            }
            SwingUtilities.invokeLater(new Runnable() {
    		    public void run() {
    		        new Banking_GUI();
    		    }
    		});
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                	for(Integer i : Bank.getInstance().getAccountList().keySet()) {
                		System.out.println(Bank.getInstance().getAccountList().get(i));
                	}
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

