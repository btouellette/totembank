/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package totembank;
import java.io.*;
import java.net.*;
import java.util.regex.*;
/**
 *
 * @author Quentin
 */
public class ConfFileGenerator {

    public static void generate(){
    try{
        System.out.println("Generate Conf File");
    System.out.println("Index start ?");
            InputStreamReader reader=new InputStreamReader(System.in);
            BufferedReader entree=new BufferedReader(reader);
            String line=entree.readLine();
            int idstart = Integer.parseInt(line);
            System.out.println("Nombre de process ?");
            reader=new InputStreamReader(System.in);
            entree=new BufferedReader(reader);
            line=entree.readLine();
            int n = Integer.parseInt(line);
            System.out.println("Nombre de rings ?");
            reader=new InputStreamReader(System.in);
            entree=new BufferedReader(reader);
            line=entree.readLine();
            int nring = Integer.parseInt(line);
            int ring;
            int id;
            int port;
            String fileaddress = "totem.conf";

			//on met try si jamais il y a une exception
			try
			{
                                File f= new File(fileaddress);
                                f.delete();
				FileWriter fw = new FileWriter(fileaddress, true);
				BufferedWriter output = new BufferedWriter(fw);
				            String ip;
            Pattern p;
            Matcher m;
            for(int i=0; i<n; i++){
                ring =(int) Math.rint(nring*Math.random());
                ip = InetAddress.getLocalHost().toString();
                System.out.println(ip);
                String s = ".*/([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3})";
                p = Pattern.compile(s);
                m = p.matcher(ip);
                if (m.matches()){
                System.out.println("bou");
                ip = m.group(1);
                }
               // ip = m.group();
                System.out.println(ip);
                id = idstart+i;
                port = 8000 + i;
                output.write(id + " " + ring + " " + ip +" "+ port +"\n");
            }
				//on marque dans le fichier ou plutot dans le BufferedWriter qui sert comme un tampon(stream)

				//on peut utiliser plusieurs fois methode write

				output.flush();
				//ensuite flush envoie dans le fichier, ne pas oublier cette methode pour le BufferedWriter

				output.close();
				//et on le ferme
				System.out.println("fichier créé");
			}
			catch(IOException ioe){
				System.out.print("Erreur : ");
				ioe.printStackTrace();
				}



    }
    catch (IOException e) {
        System.out.println(e.toString());
    }
    }
}
