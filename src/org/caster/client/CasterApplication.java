package org.caster.client;

import com.jme3.app.SimpleApplication;
import org.caster.client.protocol.CasterJSONProtocol;
import org.caster.client.protocol.CasterProtocol;
import org.json.JSONException;

import java.io.IOException;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by IntelliJ IDEA.
 * User: demoth
 * Date: 8/13/12
 * Time: 3:23 PM
 * <p/>
 * Main application class. Responsible for initializing all states, controls and resources.
 */
public class CasterApplication extends SimpleApplication {

    private CasterProtocol protocol;

    public CasterApplication(CasterProtocol protocol) {
        this.protocol = protocol;
    }

    @Override
    public void simpleInitApp() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public static void main(String[] args) throws IOException, JSONException {
        Queue<String> in = new ConcurrentLinkedQueue<>();
        Queue<String> out = new ConcurrentLinkedQueue<>();
        Socket socket = new Socket("localhost",8888);
        new CasterApplication(new CasterJSONProtocol(in, out));
        new ServerReader(in, socket);
        new ServerWriter(out, socket);
    }

    public CasterProtocol getProtocol() {
        return protocol;
    }
}
