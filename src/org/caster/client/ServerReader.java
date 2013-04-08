package org.caster.client;

import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private Queue<JSONArray> in;
    private BufferedReader reader;
    private Logger logger;

    public ServerReader(Queue<JSONArray> in, Socket socket) throws IOException, JSONException {
        this.in = in;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        logger = LoggerFactory.getLogger(ServerReader.class);
        logger.info("OK!");
    }

    @Override
    public void run() {
        while (!ClientMain.DONE) {
            try {
                while (reader.ready()) {
                    in.add(new JSONArray(reader.readLine()));
                }
                sleep(1000); // wtf - sick?
            } catch (IOException e) {
                e.printStackTrace();  //Todo
            } catch (JSONException e) {
                e.printStackTrace();  //Todo
            } catch (InterruptedException e) {
                e.printStackTrace();  //Todo
            }

        }

    }
}
