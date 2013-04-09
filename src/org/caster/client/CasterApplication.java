package org.caster.client;

import com.jme3.app.DebugKeysAppState;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.app.state.AppState;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;
import org.caster.client.gui.MenuScreenController;
import org.caster.client.protocol.CasterJSONProtocol;
import org.caster.client.protocol.CasterProtocol;
import org.caster.client.states.InGameState;
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

    // States:
    private InGameState inGameState;
    private MenuScreenController mainMenuState;

    public CasterApplication(CasterProtocol protocol) {
        super();
        stateManager.detach(stateManager.getState(StatsAppState.class));
        stateManager.detach(stateManager.getState(FlyCamAppState.class));
        stateManager.detach(stateManager.getState(DebugKeysAppState.class));
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

        inGameState = new InGameState(data);

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
