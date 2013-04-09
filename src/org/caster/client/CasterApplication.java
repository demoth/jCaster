package org.caster.client;

import com.jme3.app.DebugKeysAppState;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;
import org.caster.client.protocol.CasterJSONProtocol;
import org.caster.client.protocol.CasterProtocol;
import org.caster.client.states.InGameState;
import org.json.JSONException;

import java.io.IOException;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private boolean done;

    // this shit is needed, because we cannot initialize AbstractState-ScreenControllers,as they are created automatically
    private static CasterApplication instance;

    // States:
    private InGameState inGameState;

    public CasterApplication(CasterProtocol protocol) {
        super();
        instance = this;
        done = false;
        setShowSettings(false);

        stateManager.detach(stateManager.getState(StatsAppState.class));
        stateManager.detach(stateManager.getState(FlyCamAppState.class));
        stateManager.detach(stateManager.getState(DebugKeysAppState.class));

        data = new GameData();
        this.protocol = protocol;
    }


    @Override
    public void simpleInitApp() {

        assetManager.registerLocator("assets", FileLocator.class);

        inGameState = new InGameState(data);
        inGameState.initialize(stateManager, this);
        // todo more states init


        // init nifty
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
        nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/gui.xml", "main-menu");
        Logger.getLogger("de.lessvoid.nifty").setLevel(Level.WARNING);
        Logger.getLogger("NiftyInputEventHandlingLog").setLevel(Level.WARNING);

        guiViewPort.addProcessor(niftyDisplay);

        //stateManager.attach(mainMenuState);
        fakeConnect(); // todo remove debug
    }

    @Override
    public void update() {
        super.update();
        protocol.processMessages(data);
    }

    public void connect(String host, int port) throws IOException, JSONException {
        Socket socket = new Socket(host, port);
        //new ServerReader(in, socket).start();
        //new ServerWriter(out, socket).start();
    }

    public void fakeConnect() {
        //new BogusServer(in, out, this).start();
    }

    public static void main(String[] args) throws JSONException {
        Queue<String> in = new ConcurrentLinkedQueue<>();
        Queue<String> out = new ConcurrentLinkedQueue<>();
        CasterApplication app = new CasterApplication(new CasterJSONProtocol(in, out));
        app.start();
        new BogusServer(in, out, app).start();

    }

    public CasterProtocol getProtocol() {
        return protocol;
    }

    public GameData getData() {
        return data;
    }

    public static CasterApplication getInstance() {
        return instance;
    }


    public boolean isDone() {
        return done;
    }
}
