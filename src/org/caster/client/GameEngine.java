package org.caster.client;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: demoth
 * Date: 8/13/12
 * Time: 3:23 PM
 * <p/>
 * Draws the model and stores commands from user in 'out'
 */
public class GameEngine extends SimpleApplication {
    private Queue<JSONObject> out;
    private Queue<JSONArray> in;
    private WorldModel model;
    private org.slf4j.Logger logger;

    private Nifty nifty;
    private ScreenController controller;
    private GameState gameState;

    public GameEngine(Queue<JSONArray> in, Queue<JSONObject> out, WorldModel model) {
        this.in = in;
        this.out = out;
        this.model = model;
        this.controller = new CasterScreenController(out, model);
        this.logger = LoggerFactory.getLogger(GameEngine.class);
        this.rootNode.attachChild(model.worldNode);
        this.gameState = new GameState(model, out);

        // go fuck yourself you asshole
        Logger.getLogger("").setLevel(Level.SEVERE);
    }

    @Override
    public void simpleInitApp() {
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
        this.nifty = niftyDisplay.getNifty();

        // todo remove
        //assetManager.registerLocator("/media/f9157386-4243-4142-836c-e97e978bb8ba/media/devel/src/java/client/jCaster/res", FileLocator.class);
        nifty.fromXml("Interface/gui.xml", "login", controller);
        guiViewPort.addProcessor(niftyDisplay);

        stateManager.attach(gameState);

        inputManager.setCursorVisible(true);
        inputManager.addMapping("menu", new KeyTrigger(KeyInput.KEY_F6));
        inputManager.addListener(new ActionListener() {
            @Override
            public void onAction(String name, boolean isPressed, float tpf) {
                // toggle game state
                if (isPressed)
                    gameState.setEnabled(!gameState.isEnabled());
            }
        }, "menu");
        flyCam.setEnabled(false);


        loadModels();
    }

    @Deprecated
    private void loadModels() {
        // generalize preloading
        model.tileTypes = new LinkedHashMap<>();
        model.tileTypes.put("floor", assetManager.loadModel("Models/Terrain/floor.obj"));
        model.tileTypes.put("wall", assetManager.loadModel("Models/Terrain/wall.obj"));
        model.materials = new LinkedHashMap<>();

        Material floor = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        floor.setTexture("ColorMap", assetManager.loadTexture("Models/Terrain/floor.jpg"));
        model.materials.put("floor", floor);
        Material wall = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        wall.setTexture("ColorMap", assetManager.loadTexture("Models/Terrain/wall.jpg"));
        model.materials.put("wall", wall);
    }

    @Override
    public void update() {
        super.update();
        readIn();

        // todo remove/refactor
        if (!model.nextScreen.isEmpty()) {
            nifty.gotoScreen(model.nextScreen);
            model.nextScreen = "";
        }


    }

    private void readIn() {
        if (!in.isEmpty()) {
            try {
                JSONArray jsa = in.poll();
                logger.info("got" + jsa.toString());
                for (int i = 0; i < jsa.length(); i++) {
                    JSONObject json = jsa.getJSONObject(i);
                    switch (json.getString("what")) {
                        case "environment":
                            updateEnvironment(json);
                            break;
                        case "login":
                            addCreaturesToGUI(json.get("creatures"));
                            break;
                        case "fail":
                            break;
                        case "response":
                            processResponse(json);
                            break;
                        case "exit":
                            break;
                    }
                }

            } catch (JSONException e) {
                logger.error("Error reading json: " + e.getMessage());
            }
        }
    }

    private void processResponse(JSONObject json) throws JSONException {
        switch (json.getString("type")) {
            case "move":
                if (!json.getString("info").equalsIgnoreCase("ok"))
                    break;
                int id = json.getInt("source");
                if (model.objects.containsKey(id)) {
                    Node object = model.objects.get(id);
                    String target_cell = json.getString("target_cell");
                    int x = Integer.parseInt(target_cell.split(",")[0]);
                    int y = Integer.parseInt(target_cell.split(",")[1]);
                    object.setLocalTranslation(x, 1, y);
                }
                break;
        }
    }

    private void addCreaturesToGUI(Object creatures) throws JSONException {
        if (creatures instanceof JSONArray) {
            model.creatures = new ArrayList<>();
            for (int i = 0; i < ((JSONArray) creatures).length(); i++) {
                JSONObject creature = ((JSONArray) creatures).getJSONObject(i);
                model.creatures.add("" + creature.getInt("id"));
            }
            nifty.gotoScreen("creature_select");
            Screen screen = nifty.getScreen("creature_select");
            DropDown niftyControl = screen.findNiftyControl("dropDown", DropDown.class);
            niftyControl.clear();
            niftyControl.addAllItems(model.creatures);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        ClientMain.DONE = true;
    }

    /*
     * updates environment and objects
     */
    private void updateEnvironment(JSONObject environment) throws JSONException {
        Iterator keys = environment.keys();
        while (keys.hasNext()) {
            String key = keys.next().toString();
            // skip some keys
            if (key.equals("what"))
                continue;
            // parse coords
            int x = Integer.parseInt(key.split(",")[0]);
            int y = Integer.parseInt(key.split(",")[1]);
            Spatial tile = createTile(environment, key, x, y);
            model.worldNode.detachChildNamed(key);
            model.worldNode.attachChild(tile);

            // adding objects
            if (environment.getJSONObject(key).has("objects")) {
                JSONArray jsa = environment.getJSONObject(key).getJSONArray("objects");
                for (int i = 0; i < jsa.length(); i++) {
                    JSONObject someObject = jsa.getJSONObject(i);
                    if (!model.objects.containsKey(someObject.getInt("id"))) {
                        // new node with geometry
                        Node objectNode = new Node("id" + someObject.getInt("id"));
                        objectNode.attachChild(createObject(key, x, y));
                        model.objects.put(someObject.getInt("id"), objectNode);
                        model.worldNode.attachChild(objectNode);
                    }
                }
            }
        }
        // updating for the first time;
        if (model.wasJoining) {
            flyCam.setEnabled(true);
            if (!rootNode.hasChild(model.worldNode))
                rootNode.attachChild(model.worldNode);
            gameState.setEnabled(true);
            model.nextScreen = "game";
            model.wasJoining = false;
        }
    }

    @Deprecated
    private Spatial createTile(JSONObject environment, String key, int x, int y) throws JSONException {
        String terrain = environment.getJSONObject(key).get("terrain").toString();
        Spatial tile = model.tileTypes.get(terrain).clone();
        tile.setMaterial(model.materials.get(terrain));
        tile.setName(key);
        tile.move(x, 0, y);
        return tile;
    }

    @Deprecated
    private Geometry createObject(String key, int x, int y) {
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.setColor("Color", ColorRGBA.Orange);

        Geometry geometry = new Geometry("x" + key, new Box(0.3f, 1, 0.3f));
        geometry.move(x, 1, y);
        geometry.setMaterial(material);
        return geometry;
    }
}
