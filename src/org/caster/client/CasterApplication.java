package org.caster.client;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;
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
    private GameData data;
    private Nifty nifty;
    private Queue<String> in;
    private Queue<String> out;

    public CasterApplication(CasterProtocol protocol) {
        this.protocol = protocol;
    }

    @Override
    public void simpleInitApp() {
        this.setShowSettings(false); // does not work!
        assetManager.registerLocator("assets", FileLocator.class);
        // init nifty
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
        nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/gui.xml","mainmenu");
        guiViewPort.addProcessor(niftyDisplay);

    }

    @Override
    public void update() {
        super.update();
        protocol.processMessages(data);
    }

    public static void main(String[] args) throws JSONException {
        Queue<String> in = new ConcurrentLinkedQueue<>();
        Queue<String> out = new ConcurrentLinkedQueue<>();
        new CasterApplication(new CasterJSONProtocol(in, out)).start();

    }

    public CasterProtocol getProtocol() {
        return protocol;
    }

    public GameData getData() {
        return data;
    }

    public void connect(String host, int port) throws IOException, JSONException {
        Socket socket = new Socket(host, port);
        new ServerReader(in, socket).start();
        new ServerWriter(out, socket).start();
    }
}
