package totembank;

import java.io.*;
import java.net.*;
import java.util.regex.*;
import java.util.HashMap;
import java.util.List;

/**
 * This class handles the whole stack of the node
 * It handles initialization and commom parameters
 */
public class Process extends Node {

    /** List of rings associated with node. A single element for no-gateway nodes*/
    private HashMap<Integer, Ring> rings = new HashMap<Integer, Ring>();
    /** Instance of the process to guarantee the singleton*/
    static private Process instance;
    // Lamport clock offset amount
    private long timeOffset = 0;
    
    /** @return Return this process*/
    static Process getInstance() {
        return instance;
    }
    Process(){
    	
    }
    

    /** Process creation and initialization by reading the configuration file
    @param id Id assigned to this process*/
    Process(int id) {
        super();
        this.id = id;
        //TODO populate MultipleRing.receivedMessages to have an empty list for every ring in the network 
        String file = "totem.conf"; // address of the configuation file

        try {
            instance = this;
            InputStream ips = new FileInputStream(file);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String line;
            boolean loop = true;
            /* Note: this while loop needs to check for multiple rings in case node is a gateway. Right now it doesn't which means we need to modify
             * configuration file to accommodate for it
             */
            while ((line = br.readLine()) != null && loop) {
                Pattern p = Pattern.compile("^([0-9]+) ([0-9]+) ([0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}) ([0-9]+)$"); //regex for a line in the conf file [idprocess ringid IpAddress port]
                Matcher m = p.matcher(line);
                if (m.matches() && Integer.parseInt(m.group(1)) == id) {
                    int ring = Integer.parseInt(m.group(2));
                    Main.test = ring;	// used for test purposes currently.....
                    loop = false;
                    Ring r = new Ring(ring); // Creates the ring associated
                    addRing(r);
                    BottomLayer b = new BottomLayer(); //Creates the BottomLayer
                    b.setPort(Integer.parseInt(m.group(4)));
                    InetAddress ipnode = InetAddress.getByName(m.group(3));
                    Node n = new Node(ipnode, Integer.parseInt(m.group(4)), Integer.parseInt(m.group(1)));
                    b.addNode(n);
                    b.setRing(ring);
                    SingleRing s = new SingleRing(ring, id); //Create the Single Ring Layer
                    r.setSingleRing(s);
                    r.setBottomLayer(b);
                    MultipleRing.instantiateList();	// create list for the multiple ring protocol
                    MultipleRing.addRingID(ring);
                    b.start();
                    
                }
            }
            if (loop) {
                //Id not found
                System.out.println("id not found in the configuration file");
            } 
            else {
                ips = new FileInputStream(file);
                ipsr = new InputStreamReader(ips);
                br = new BufferedReader(ipsr);
                // Add nodes in the same ring in the Node List of the BottomLayer
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

    /** Return a ring given the id
     * @param ring Ring identifier*/
    Ring getRing(int ring) {
        return rings.get(ring);
    }

    /** Add a ring in the ring list
    @param r Ring to be added*/
    private void addRing(Ring r) {
        rings.put(r.ringID, r);
    }
    
    HashMap<Integer, Ring> getRings() {
    	return rings;
    }

    /** @return Return the id of the process*/
    int getId() {
        return id;
    }

    /** Defines a ring*/
    protected class Ring {

        /** Id of the ring*/
        int ringID;
        List<Message> receivedMessages;
        List<String> ringAddresses;
        /** Bottom Layer associated for this process to this ring*/
        BottomLayer bLayer;
        /** Single Ring Layer associated for this process to this ring*/
        SingleRing sRing;

        /** Constructor*/
        public Ring(int ringID) {
            this.ringID = ringID;
        }

        /** Attach a Bottom Layer to this ring
        @param b Bottom Layer to be attached*/
        void setBottomLayer(BottomLayer b) {
            bLayer = b;
    }

        /** Attach a Single Ring Layer to this ring
        @param s Single Ring Layer to be attached*/
        void setSingleRing(SingleRing s) {
            sRing = s;
        }

        /** @return Return the Bottom Layer attached to this ring*/
        public BottomLayer getBLayer() {
            return bLayer;
        }

        /** @return Return the Single Ring Layer attached to this ring*/
        public SingleRing getSingleRing() {
            return sRing;
        }

        protected void broadcast(Message msg) {
            //TODO: broadcast to all addresses
        }
    }

	public long timeOffset() {
		return timeOffset;
	}

	public void increaseTimeOffset(long increase) {
		timeOffset += increase;
	}
}



