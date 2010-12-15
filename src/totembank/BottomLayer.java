/**
This class implements the Bottom Layer protocol (Network communications in best effort with UDP)*/
package totembank;

import java.net.*;
import java.io.*;
import java.util.*;

public class BottomLayer extends Thread {

    /** Instance of BottomLayer used to guarantee Singleton*/
    static BottomLayer instance;
    /** List of nodes of the ring. The list is updated when a change occur*/
    Set<Node> nodes;
    /** Socket used for transmission and reception*/
    DatagramSocket socket;
    /** Port assigned to this Process on the machine. Each process on a single machine is assigned to a different port*/
    int port;
    /** ID of the ring */
    int ring;

    /** Constructor. It creates only a empty list of nodes*/
    public BottomLayer() {
        nodes = new HashSet<Node>();
    }

    /** Set the ring identifier
    @param ring value of ring identifier*/
    void setRing(int ring) {
        this.ring = ring;
    }

    /** Set the port value and creates the socket
    @param port port number*/
    void setPort(int port) {
        this.port = port;
        try {
            socket = new DatagramSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Determine the next node which has to receive the token. Nodes are placed sorted in the ring in numerical order.
    @param id id of the process executing the method
    @return Return the next node for passing the token. If there the process is the only node, it returns the process*/
    Node nextToken(int id) {
        int min = Integer.MAX_VALUE;
        Node nodemin = Process.getInstance();
        int next = Integer.MAX_VALUE;
        Node node = Process.getInstance();
        for (Node n : nodes) {                   //Determine min id node in the list and the min id superior to this process id
            if (n.id > id && n.id < next) {
                next = n.id;
                node = n;
            }
            if (n.id < min) {
                min = n.id;
                nodemin = n;
            }
        }
        if (next == Integer.MAX_VALUE) { // If there is no id superior, the following node has the minimum id
            return nodemin;
        } else {
            return node;
        }
    }
    
    /** Determine the next node which has to receive the token ack. Nodes are placed sorted in the ring in numerical order.
    @param id id of the process executing the method
    @return Return the next node for passing the token ack. If there the process is the only node, it returns the process*/
    Node prevToken(int id) {
        int max = Integer.MIN_VALUE;
        Node nodemax = Process.getInstance();
        int prev = Integer.MIN_VALUE;
        Node node = Process.getInstance();
        for (Node n : nodes) {                   //Determine max id node in the list and the max id superior to this process id
            if (n.id < id && n.id > prev) {
                prev = n.id;
                node = n;
            }
            if (n.id > max) {
                max = n.id;
                nodemax = n;
            }
        }
        if (prev == Integer.MIN_VALUE) { // If there is no id superior, the previous node has the max id
        	System.out.println("Returning prev token: " + nodemax.id);
            return nodemax;
        } else {
        	System.out.println("Returning prev token: " + node.id);
            return node;
        }
    }
    
    /** Add a node to the list of nodes of the ring
    @param n Node to be added*/
    void addNode(Node n) {
        nodes.add(n);
    }

    /** Pass the token to the following node after define it
    @param t Token handled by this Node
    @param idprocess Id of the node executing the method (sender, not receiver)*/
    void sendToken(Token t, int idprocess) {
        Node n = nextToken(idprocess);
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(buffer);
            t.setTimeStamp();
            out.writeObject(t);
            out.close();
            buffer.close();
            DatagramPacket packet = new DatagramPacket(buffer.toByteArray(), buffer.size(), n.getIp(), n.getPort());
            SingleRing s = Process.getInstance().getRing(ring).getSingleRing();
            s.nbmessagesreceived = s.nbmessagesreceived + buffer.size();
            socket.send(packet);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    
    /** Pass the token to the following node after define it
    @param m Token handled by this Node
    @param idprocess Id of the node executing the method (sender, not receiver)*/
    void sendTokenAck(int idprocess) {
    	Message m = new Message();
    	m.ringIDs.add(ring);
    	m.message = "ACK TOKEN";
        Node n = prevToken(idprocess);
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(buffer);
            out.writeObject(m);
            out.close();
            buffer.close();
            DatagramPacket packet = new DatagramPacket(buffer.toByteArray(), buffer.size(), n.getIp(), n.getPort());
            SingleRing s = Process.getInstance().getRing(ring).getSingleRing();
            s.nbmessagesreceived = s.nbmessagesreceived + buffer.size();
            socket.send(packet);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    /** Multicast a message to the nodes of the rings. Creates datagram and serializes object Message
    The multicast is realized without use native Multicast in Java but by sending message to each node in the list of nodes of the ring
    @param m Message to be sent*/
    void send(Message m) {
        for (Node n : nodes) {
            try {
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(buffer);
                out.writeObject(m);
                out.close();
                buffer.close();
                DatagramPacket packet = new DatagramPacket(buffer.toByteArray(), buffer.size(), n.getIp(), n.getPort());
                SingleRing s = Process.getInstance().getRing(ring).getSingleRing();
                if ((m.message.equals("GV")) ){
                    s.nbmessagesreceived = s.nbmessagesreceived + buffer.size();
                }
                else
                    s.nbmessagesreceived = s.nbmessagesreceived + buffer.size();
                    s.nbmessagesreceivedtrue = s.nbmessagesreceivedtrue + buffer.size();
                {

                }
                socket.send(packet);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }



    }

    /** Listen the port the receive message. Then delivers
    @throws IOException @see IOException
    @throws ClassNotFoundException This method test if the message received is a instance of class Message. This test throws this exception when class is not found*/
    public void receive() throws IOException, ClassNotFoundException {
        byte[] buf = new byte[3000];
        DatagramPacket recv = new DatagramPacket(buf, buf.length);
        socket.receive(recv);
        ByteArrayInputStream bais = new ByteArrayInputStream(buf);
        ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(bais));
        Object messagereceived = ois.readObject();
        if (messagereceived instanceof Message) {
            Message m = (Message) messagereceived;
            deliver(m);

        }
    }

    /** Delivers the message to the Single Ring Protocol
    @param m Message to be delivered*/
    public void deliver(Message m) {
        SingleRing s = Process.getInstance().getRing(ring).getSingleRing();

        s.receive(m);
    }

    /** Run the thread to listen the port*/
    public void run() {
        while (true) {
            try {
                receive();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
