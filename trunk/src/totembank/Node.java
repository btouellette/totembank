package totembank;

import java.net.*;

/**
Defines a Node. There are two points of view for Nodes.
 * For other Nodes within the rings, Nodes are viewed by their id except in the Bottom Layer where Ip Address and port number are useful to pass the token
 * For itself, a Process implements nodes but the port number does not represent anything. Indeed a Gateway is a process in several ring, so with several port numbers
 */
public class Node {

    /** Id of the process*/
    int id;
    /** IP address*/
    InetAddress ip;
    /** Port number of the process*/
    int port;

    /** Constructor. */
    public Node(InetAddress ip, int port, int id) {
        this.id = id;
        this.ip = ip;
        this.port = port;
    }

    public Node() {
    }

    /** @return IP address*/
    public InetAddress getIp() {
        return ip;
    }

    /** @return Return the port number*/
    public int getPort() {
        return port;
    }
}
