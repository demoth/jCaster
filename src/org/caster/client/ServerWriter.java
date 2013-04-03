package org.caster.client;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private Queue<JSONObject> out;
    private BufferedWriter writer;
    private Logger logger;

    public ServerWriter(Queue<JSONObject> out, Socket socket) throws IOException, JSONException {
        this.out = out;
        this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.logger = LoggerFactory.getLogger(ServerWriter.class);
        this.logger.info("OK!");
    }

    @Override
    public void run() {
        while (!ClientMain.DONE) {
            try {
                Thread.sleep(1000);
                if (!out.isEmpty()) {
                    String msg = out.poll().toString();
                    logger.info("sent " + msg);
                    writer.write(msg + "\r\n");
                    writer.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        try {
            writer.write(new JSONObject().put("what", "exit").toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
