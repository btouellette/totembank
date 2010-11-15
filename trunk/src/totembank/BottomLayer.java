/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package totembank;

/**
 *
 * @author Quentin
 */
import java.net.*; // MulticastSocket, InetAddress, DatagramPacket,...
import java.io.*; // exceptions comme UnknownHostException, SocketException,...
import java.util.*; // pour Scanner

public class BottomLayer extends Thread {

    static BottomLayer instance;
    Set<Node> nodes;
    DatagramSocket socket;
    int port = 8000;
    int ring;

    public BottomLayer() {
        nodes = new HashSet<Node>();
    }

    void setRing(int ring){
        this.ring = ring;
    }

    void setPort(int port) {
        this.port = port;
        try {
            socket = new DatagramSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Node nextToken(int id){    // Return the next node for passing the token. If there the process is the only node, it returns the process
        int min = Integer.MAX_VALUE;
        Node nodemin = Process.getInstance();
        int next=Integer.MAX_VALUE;
        Node node = Process.getInstance();
        for (Node n : nodes){
            if (n.id > id && n.id < next ){
                next = n.id;
                node = n;
            }
            if (n.id < min){
                min = n.id;
                nodemin = n;
            }
        }
        if (next == Integer.MAX_VALUE){
            return nodemin;
        }
        else{
            return node;
        }
    }

    void addNode(Node n) {
        nodes.add(n);
    }

    void sendToken(Token t, int idprocess ){
        Node n = nextToken(idprocess);
        try {
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(buffer);
                t.setTimeStamp();
                out.writeObject(t);
                out.close();
                buffer.close();
                DatagramPacket packet = new DatagramPacket(buffer.toByteArray(), buffer.size(),n.getIp(), n.getPort());
                socket.send(packet);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        
    }

    void send(Message m) {
        for (Node n : nodes) {
            try {
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(buffer);
                out.writeObject(m);
                out.close();
                buffer.close();
                DatagramPacket packet = new DatagramPacket(buffer.toByteArray(), buffer.size(),n.getIp(), n.getPort());
                socket.send(packet);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }



    }
    public void receive() throws IOException, ClassNotFoundException{
        byte[] buf = new byte[3000];
        DatagramPacket recv = new DatagramPacket(buf, buf.length);
        socket.receive(recv);
        ByteArrayInputStream bais=new ByteArrayInputStream(buf);
        ObjectInputStream ois=new ObjectInputStream(new BufferedInputStream(bais));
        Object messagereceived = ois.readObject();
        if (messagereceived instanceof Message){
            Message m = (Message) messagereceived;
            deliver(m);
            
        }
    }

    public void deliver(Message m){
        SingleRing s = Process.getInstance().getRing(ring).getSingleRing();

        s.receive(m);
    }

    public void run() {
        while (true) {
            try {
                receive();
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        }
    }
}
