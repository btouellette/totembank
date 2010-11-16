/** Class to generate Configuration File*/
package totembank;

import java.io.*;
import java.net.*;
import java.util.regex.*;

public class ConfFileGenerator {

    /** Generate Conf file in totem.conf for processes present in this single machine
    Parameter for generation are entered with keyboard.
    They are : The id of the first node, the number of processes executed on this machine and the number of ring
     *Id of processes are assigned incrementally. The id of the first is used to obtained consequently different id for different machine
    by assigning a range of id to each machine.
    Rings are chosen at random (uniform distribution)*/
    public static void generate() {
        try {
            System.out.println("Generate Conf File for TotemBank");
            System.out.println("Index start ?");
            InputStreamReader reader = new InputStreamReader(System.in);
            BufferedReader entree = new BufferedReader(reader);
            String line = entree.readLine();
            int idstart = Integer.parseInt(line);
            System.out.println("Number of process ?");
            reader = new InputStreamReader(System.in);
            entree = new BufferedReader(reader);
            line = entree.readLine();
            int n = Integer.parseInt(line);
            System.out.println("Number of rings ?");
            reader = new InputStreamReader(System.in);
            entree = new BufferedReader(reader);
            line = entree.readLine();
            int nring = Integer.parseInt(line);
            int ring;
            int id;
            int port;
            String fileaddress = "totem.conf"; // address of the conf file


            try {
                File f = new File(fileaddress);
                f.delete();
                FileWriter fw = new FileWriter(fileaddress, true);
                BufferedWriter output = new BufferedWriter(fw);
                String ip;
                Pattern p;
                Matcher m;
                for (int i = 0; i < n; i++) {
                    ring = (int) Math.rint((nring - 1) * Math.random()); // generate at random the id of ring
                    ip = InetAddress.getLocalHost().toString();
                    String s = ".*/([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3})";
                    p = Pattern.compile(s);
                    m = p.matcher(ip);
                    if (m.matches()) {
                        ip = m.group(1);
                    }
                    // ip = m.group();
                    id = idstart + i;
                    port = 8000 + i;
                    output.write(id + " " + ring + " " + ip + " " + port + "\n");
                }
                output.flush();

                output.close();
                System.out.println("Conf File Generated");
            } catch (IOException ioe) {
                System.out.print("Error : ");
                ioe.printStackTrace();
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        generate();
    }
}
