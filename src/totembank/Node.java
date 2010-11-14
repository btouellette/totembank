/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package totembank;
    import java.net.* ; // MulticastSocket, InetAddress, DatagramPacket,...
    import java.io.* ; // exceptions comme UnknownHostException, SocketException,...
/**
 *
 * @author Quentin
 */
public class Node {
    int id;
    InetAddress ip;
    int port;

    public Node(InetAddress ip, int port, int id){
        this.id = id;
        this.ip = ip;
        this.port = port;
    }
    public Node(){
    }

    public InetAddress getIp(){
        return ip;
    }

    public int getPort(){
        return port;
    }
}
