/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package totembank;
import java.io.*;
/**
 *
 * @author Quentin
 */
public class Message implements Serializable{
    private static final long serialVersionUID = 154634656545456L;
    int ringID;
        int seqNum = -1;
        long tStamp;


    public int getRingID(){
        return ringID;
    }
    public Message(int ring){
        ringID = ring;
        tStamp = System.currentTimeMillis();


    }

}
