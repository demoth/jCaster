package org.caster.client;

import com.jme3.app.state.AbstractAppState;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Queue;

/**
 * Created by IntelliJ IDEA.
 * User: demoth
 * Date: 8/16/12
 * Time: 1:15 AM
 */
public class CasterScreenController extends AbstractAppState implements ScreenController {
    private Queue<JSONObject> out;
    private WorldModel model;
    private Nifty nifty;
    private Screen screen;

    public CasterScreenController(Queue<JSONObject> out, WorldModel model) {
        this.out = out;
        this.model = model;
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
    }

    @Override
    public void onStartScreen() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onEndScreen() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @SuppressWarnings("unused")
    public void login() throws JSONException {
        String login = this.nifty.getScreen("login").findNiftyControl("login_field", TextField.class).getText();
        String password = this.nifty.getScreen("login").findNiftyControl("password_field", TextField.class).getText();
        out.add(new JSONObject()
                .put("what", "login")
                .put("login", login)
                .put("passw", password));

    }

    @SuppressWarnings("unused")
    public void join() throws JSONException {
        String crid = nifty.getScreen("creature_select").findNiftyControl("dropDown", DropDown.class).getSelection().toString();
        // todo check
        model.player = Integer.valueOf(crid);
        model.wasJoining = true;
        out.add(new JSONObject()
                .put("what", "join")
                .put("crid", crid));
    }

}
