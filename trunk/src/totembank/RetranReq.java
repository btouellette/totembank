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
public class RetranReq implements Serializable{
            int idprocess;
            int seqNum;

            public RetranReq (int id, int num){
                idprocess = id;
                seqNum = num;
            }
}
