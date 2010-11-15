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
            /**
	 * 
	 */
	private static final long serialVersionUID = -2640957957037913868L;
			int idprocess;
            int seqNum;

            public RetranReq (int id, int num){
                idprocess = id;
                seqNum = num;
            }
}
