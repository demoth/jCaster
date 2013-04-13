package org.caster.client.protocol;

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
    public void join(String charId) {
        //To change body of implemented methods use File | Settings | File Templates.
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

                    break;
                case "environment":
                    addEnvironment(o, data);
                    break;
            }
        }
    }

    private void addEnvironment(JSONObject o, GameData data) {
        data.location = o.getString("location");
        data.turn = o.getInt("turn");
        JSONArray cells = o.getJSONArray("cells");
        for (int i = 0; i < cells.length(); i++) {
            JSONObject cell = cells.getJSONObject(i);
            addCell(cell, data);
        }
    }

    private void addCell(JSONObject cell, GameData data) {
        System.out.println("Added: " + cell.get("type"));
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
}
