package org.caster.client.protocol;

import com.jme3.app.FlyCamAppState;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.scene.Spatial;
import org.caster.client.CasterApplication;
import org.caster.client.GameData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Queue;

/**
 * Created by IntelliJ IDEA.
 * User: daniil
 * Date: 08.04.13
 * Time: 21:47
 */
public class CasterJSONProtocol implements CasterProtocol {
    private Queue<String> in;
    private Queue<String> out;

    private AssetManager am;

    public CasterJSONProtocol(Queue<String> in, Queue<String> out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public void chat(String message) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void login(String username, String password) {
        JSONObject request = new JSONObject();
        request.put("what", "login").put("login", username).put("passw", password);
        out.add(request.toString());
    }

    @Override
    public void join(Integer charId) {
        JSONObject request = new JSONObject();
        request.put("what", "join").put("crid", charId);
        out.add(request.toString());
    }

    @Override
    public void createCharacter(String properties) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void action(String type, String... details) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void disconnect() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void getInventory() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void getTerrain() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void getSkills() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void getStats() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void processMessages(GameData data) {
        while (!in.isEmpty()) {
            JSONObject o = new JSONObject(in.poll());
            switch (o.getString("what")) {
                case "login":
                    addCreatures(o, data);
                    break;
                case "joined":
                    CasterApplication.getInstance().getStateManager().attach(new FlyCamAppState());
                    break;
                case "environment":
                    addEnvironment(o, data);
                    break;
                default:
                    System.out.println("Got: " + o.toString());
            }
        }
    }

    private void addEnvironment(JSONObject o, GameData data) {
        data.location = o.getInt("location");
        data.turn = o.getInt("turn");
        JSONArray cells = o.getJSONArray("cells");
        for (int i = 0; i < cells.length(); i++) {
            JSONObject cell = cells.getJSONObject(i);
            addCell(cell, data);
        }
    }

    private void addCell(JSONObject cell, GameData data) {
        am = CasterApplication.getInstance().getAssetManager();
        Spatial tile = am.loadModel("Models/Terrain/" + cell.getString("type") + ".obj");
        Material material = new Material(am, "Common/MatDefs/Misc/Unshaded.j3md");
        material.setTexture("ColorMap", am.loadTexture("Textures/" + cell.getString("type") + ".jpg"));
        tile.setMaterial(material);
        JSONArray coords = cell.getJSONArray("coords");
        float x = (float) coords.getDouble(0);
        float y = (float) coords.getDouble(1);
        tile.setLocalTranslation(x, 0.0f, y);
        data.worldNode.attachChild(tile);
        //System.out.println("Added: " + cell.get("type"));
    }

    private void addCreatures(JSONObject o, GameData data) {
        JSONArray crits = o.getJSONArray("creatures");
        for (int i = 0; i < crits.length(); i++) {
            JSONObject crit = crits.getJSONObject(i);
            HashMap<String, String> map = new HashMap<>();
            map.put("cls", crit.getString("cls"));
            map.put("name", crit.getString("name"));
            map.put("model", crit.getString("model"));

            data.creatures.put(crit.getInt("id"), map);
            data.loggedIn = true;


        }
    }

    public void setAm(AssetManager am) {
        this.am = am;
    }
}
