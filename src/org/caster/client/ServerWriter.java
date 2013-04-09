package org.caster.client;

import org.json.JSONException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Queue;

/**
 * Created by IntelliJ IDEA.
 * User: demoth
 * Date: 8/13/12
 * Time: 3:23 PM
 * <p/>
 */
public class ServerWriter extends Thread {
    private Queue<String> out;
    private BufferedWriter writer;
    //private Logger logger;

    public ServerWriter(Queue<String> out, Socket socket) throws IOException, JSONException {
        this.out = out;
        this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        //this.logger = LoggerFactory.getLogger(ServerWriter.class);
        //this.logger.info("Server Writer is up!");
    }

    @Override
    public void run() {

    }
}
