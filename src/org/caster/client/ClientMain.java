package org.caster.client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by IntelliJ IDEA.
 * User: demoth
 * Date: 8/10/12
 * Time: 5:59 PM
 */
public class ClientMain {

    public static boolean DONE = false; // antipattern лулз

    public static void main(String[] args) {

        try {
            // four elements of asshole
            final WorldModel model = new WorldModel();
            final Queue<JSONArray> in = new ConcurrentLinkedQueue<>();
            final Queue<JSONObject> out = new ConcurrentLinkedQueue<>();
            //final Socket socket = new Socket("sorseg.dyndns.org", 8888);
            final Socket socket = new Socket("localhost", 8888);

            // three elements of bitches
            new ServerReader(in, socket).start();
            new GameEngine(in, out, model).start();
            new ServerWriter(out, socket).start();


        } catch (IOException | JSONException e) {
            e.printStackTrace();  //Tofuck
        }

    }


}
