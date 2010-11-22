/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package totembank;
import java.io.* ;
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
            	/*System.out.println("Press a key to start the Token passing");
               	reader=new InputStreamReader(System.in);
               	entree=new BufferedReader(reader);
               	line=entree.readLine();
                
               	System.out.println("test");*/
            	Token t = new Token(test);
            	BottomLayer b = Process.getInstance().getRing(test).getBLayer();
            	b.sendToken(t, id);
            }
            new Banking_GUI();
            /*while(true){
            	System.out.println("Enter any key to send a message");
            	reader=new InputStreamReader(System.in);
            	entree=new BufferedReader(reader);
            	line=entree.readLine();
            	Message m = new Message(test);
            	SingleRing s = Process.getInstance().getRing(test).getSingleRing();
            	s.send(m);
            }*/
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

