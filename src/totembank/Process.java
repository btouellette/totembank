/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package totembank;

import java.io.*;
import java.net.*;
import java.util.regex.*;
import java.util.HashMap;
import java.util.List;


/**
 *
 * @author Quentin
 */
public class Process extends Node {

    private HashMap<Integer, Ring> rings = new HashMap<Integer, Ring>();
    static private Process instance;

    static Process getInstance(){
        return instance;
    }
    
    Process(){    	//Need to fix (call from gateway.java from constructor)
    }

     Process(int id) {
        super();
        this.id = id;
        String file = "totem.conf";

        //lecture du fichier texte
        try {
            instance = this;
            InputStream ips = new FileInputStream(file);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String line;
            boolean loop = true;
            while ((line = br.readLine()) != null && loop) {
                Pattern p = Pattern.compile("^([0-9]+) ([0-9]+) ([0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}) ([0-9]+)$");
                Matcher m = p.matcher(line);
                if (m.matches() && Integer.parseInt(m.group(1)) == id) {
                    int ring = Integer.parseInt(m.group(2));
                    //Main.test = ring;
                    loop = false;
                    Ring r = new Ring(ring);
                    addRing(r);
                    BottomLayer b = new BottomLayer();
                    b.setPort(Integer.parseInt(m.group(4)));
                    InetAddress ipnode = InetAddress.getByName(m.group(3));
                    Node n = new Node(ipnode, Integer.parseInt(m.group(4)), Integer.parseInt(m.group(1)));
                    SingleRing s = new SingleRing(ring, id);
                    r.setSingleRing(s);
                    b.addNode(n);
                    b.setRing(ring);
                    r.setBottomLayer(b);
                    b.start();
                }
            }
            if (loop) {
                //Id not found
                System.out.println("id not found in the configuration file");
            } else {
                ips = new FileInputStream(file);
                ipsr = new InputStreamReader(ips);
                br = new BufferedReader(ipsr);

                while ((line = br.readLine()) != null) {
                    Pattern p = Pattern.compile("^([0-9]+) ([0-9]+) ([0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}) ([0-9]+)$");
                    Matcher m = p.matcher(line);
                    if (m.matches() && rings.containsKey(Integer.parseInt(m.group(2))) && Integer.parseInt(m.group(1)) != id) {
                        BottomLayer b = rings.get(Integer.parseInt(m.group(2))).getBLayer();
                        InetAddress ipnode = InetAddress.getByName(m.group(3));
                        Node n = new Node(ipnode, Integer.parseInt(m.group(4)), Integer.parseInt(m.group(1)));
                        b.addNode(n);
                    }
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    Ring getRing(int ring){
        return rings.get(ring);
    }

    private void addRing(Ring r){
        rings.put(r.ringID, r);
    }
    int getId(){
        return id;
    }

    protected class Ring {
                int ringID;
                List<Message> receivedMessages;
                List<String> ringAddresses;
                BottomLayer bLayer;
                SingleRing sRing;

                public Ring(int ringID) {
                    this.ringID =ringID;
                }
                void setBottomLayer (BottomLayer b){
                    bLayer = b;
                }
                void setSingleRing (SingleRing s){
                    sRing = s;
                }
                public BottomLayer getBLayer(){
                    return bLayer;
                }

                public SingleRing getSingleRing(){
                    return sRing;
                }

                protected void broadcast(Message msg) {
                        //TODO: broadcast to all addresses
                }
        }

    public static void main( String args[]){
    	new Process(10);
    }
}



