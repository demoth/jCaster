package org.caster.client;

import org.json.JSONException;
import org.json.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Queue;

/**
 * Created by IntelliJ IDEA.
 * User: demoth
 * Date: 8/12/12
 * Time: 10:22 PM
 * <p/>
 * Reads commands from server and stores it in teh queueueueueu
 */
public class ServerReader extends Thread {

    private Queue<String> in;
    private BufferedReader reader;
   // private Logger logger;

    public ServerReader(Queue<String> in, Socket socket) throws IOException, JSONException {
        this.in = in;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //logger = LoggerFactory.getLogger(ServerReader.class);
        //logger.info("ServerReader is up!");
    }

    @Override
    public void run() {
        while (!CasterApplication.getInstance().done) {
            try {
                if (reader.ready()) {
                    in.add(reader.readLine());
                }
                sleep(100);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }
    }
}
