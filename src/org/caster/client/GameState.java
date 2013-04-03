package org.caster.client;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Queue;

/**
 * Created by IntelliJ IDEA.
 * User: demoth
 * Date: 8/17/12
 * Time: 12:37 AM
 */
public class GameState extends AbstractAppState {
    private Application application;
    private WorldModel model;
    private Queue<JSONObject> out;

    public GameState(WorldModel model, Queue<JSONObject> out) {
        this.out = out;
        this.model = model;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.application = app;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            application.getInputManager().addMapping("W", new KeyTrigger(KeyInput.KEY_H));
            application.getInputManager().addMapping("N", new KeyTrigger(KeyInput.KEY_K));
            application.getInputManager().addMapping("S", new KeyTrigger(KeyInput.KEY_J));
            application.getInputManager().addMapping("E", new KeyTrigger(KeyInput.KEY_L));
            // wtf? where is this listener being deleted?
            application.getInputManager().addListener(new ActionListener() {
                @Override
                public void onAction(String name, boolean isPressed, float tpf) {
                    if (model.player != null && isPressed) {
                        try {
                            JSONObject json = new JSONObject()
                                    .put("what", "request")
                                    .put("type", "move")
                                    .put("info", name);
                            out.add(json);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, "W", "N", "S", "E");
        } else {
            application.getInputManager().deleteMapping("W");
            application.getInputManager().deleteMapping("N");
            application.getInputManager().deleteMapping("S");
            application.getInputManager().deleteMapping("E");
        }

    }
}
