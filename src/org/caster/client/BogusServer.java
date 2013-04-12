package org.caster.client;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Queue;

/**
 * Created with IntelliJ IDEA.
 * User: daniil
 * Date: 09.04.13
 * Time: 15:36
 */
public class BogusServer extends Thread {
    Queue<String> in;
    Queue<String> out;
    CasterApplication app;

    boolean loggedIn = false;
    boolean joined = false;

    @Override
    public void run() {
        while (!app.isDone()) {
            // parse in
            while (!in.isEmpty()) {
                String message = in.poll();
                JSONObject object = new JSONObject(message);
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
            if (loggedIn) {
                sendEnvironment();
            }
            try {
                sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    private void sendEnvironment() {
        JSONObject response = new JSONObject();
        response.put("what", "environment");
        response.put("location", "342f23");
        response.put("turn", 12421);
        char line1[] = {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'};
        char line2[] = {'#', '.', '.', '.', '.', '.', '.', '.', '#', '#', '#', '#'};
        char line3[] = {'#', '#', '#', '#', '.', '.', '#', '#', '#', '#', '.', '#'};
        char line4[] = {'#', '.', '.', '#', '.', '.', '.', '.', '.', '.', '.', '#'};
        char line5[] = {'#', '.', '.', '.', '.', '.', '.', '#', '#', '.', '.', '#'};
        char line6[] = {'#', '#', '#', '#', '.', '#', '.', '#', '.', '#', '.', '#'};
        char line7[] = {'#', '.', '.', '.', '.', '#', '#', '#', '.', '#', '.', '#'};
        char line8[] = {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'};
        char maps[][] = {line1, line2, line3, line4, line5, line6, line7, line8};
        int width = 12, height = 8;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                JSONObject cell = new JSONObject();
                if (maps[i][j] == '#') {
                    cell.put("type", "wall");
                } else {
                    cell.put("type", "floor");
                }
                cell.append("coords", i).append("coords", j);
                response.append("cells", cell);
            }
        }

        out.add(response.toString());
    }

    private void request(JSONObject object) {
        //To change body of created methods use File | Settings | File Templates.
    }

    private void doJoin(JSONObject object) {
        if (loggedIn) {
            if (object.getString("crid").equals("id1") ||
                    object.getString("crid").equals("id2"))

                joined = true;
        }
    }

    private void login(JSONObject object) {
        String login = object.getString("login");
        String password = object.getString("password");
        if (login.equals("demoth") && password.equals("1234")) {
            JSONArray critters = createCreatures();
            JSONObject response = new JSONObject();
            response.put("what", "login")
                    .put("creatures", critters);
            out.add(response.toString());
            //loggedIn = true;
        } else {
            System.out.println("WRONG USER");
        }
    }

    private JSONArray createCreatures() {
        JSONArray array = new JSONArray();
        JSONObject crit1 = new JSONObject();
        crit1.put("type", "creature")
                .put("cls", "kob")
                .put("model", "kob1")
                .put("name", "Cool Kobold")
                .put("id", "id1");
        JSONObject crit2 = new JSONObject();
        crit2.put("type", "creature")
                .put("cls", "naga")
                .put("model", "nag1")
                .put("name", "Medusa")
                .put("id", "id2");
        array.put(crit1).put(crit2);
        return array;
    }

    public BogusServer(Queue<String> in, Queue<String> out, CasterApplication casterApplication) {
        app = casterApplication;
        this.out = in;
        this.in = out;
    }
}
