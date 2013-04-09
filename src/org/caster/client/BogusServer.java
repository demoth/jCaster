package org.caster.client;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Queue;

/**
 * Created with IntelliJ IDEA.
 * User: daniil
 * Date: 09.04.13
 * Time: 15:36
 * To change this template use File | Settings | File Templates.
 */
public class BogusServer extends Thread {
    Queue<String> in;
    Queue<String> out;
    CasterApplication app;

    @Override
    public void run() {
        while (!app.isDone()) {
            // parse in
            while (!in.isEmpty()) {
                String message = in.poll();
                JSONArray array = new JSONArray(message);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    switch (object.getString("what")) {
                        case "login":
                            login(object);
                            break;
                        case "join":
                            doJoin(object);
                            break;
                        case "request":
                            request(object);
                            break;
                    }
                }
            }
        }
    }

    private void request(JSONObject object) {
        //To change body of created methods use File | Settings | File Templates.
    }

    private void doJoin(JSONObject object) {
        //To change body of created methods use File | Settings | File Templates.
    }

    private void login(JSONObject object) {
        String login = object.getString("login");
        String password = object.getString("password");
        if (login.equals("demoth") && password.equals("1234")) {
            JSONArray critters = createCreatures();
            JSONObject response = new JSONObject();
            response.put("what", "login")
                    .append("creatures", critters);
            out.add(response.toString());
        }
    }

    private JSONArray createCreatures() {
        JSONArray array = new JSONArray();
        JSONObject crit1 = new JSONObject();
        crit1.put("type", "creature")
                .put("cls", "kob")
                .put("model", "kob1")
                .put("name", "Cool Kobold")
                .put("id", "23h42l3j");
        JSONObject crit2 = new JSONObject();
        crit2.put("type", "creature")
                .put("cls", "naga")
                .put("model", "nag1")
                .put("name", "Medusa")
                .put("id", "1289nga");
        array.put(crit1).put(crit2);
        return array;
    }

    public BogusServer(Queue<String> in, Queue<String> out, CasterApplication casterApplication) {
        app = casterApplication;
    }
}
