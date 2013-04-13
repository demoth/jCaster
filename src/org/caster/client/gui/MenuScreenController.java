package org.caster.client.gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.ListBox;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import org.caster.client.CasterApplication;
import org.caster.client.states.AbstractCasterState;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: demoth
 * Date: 8/16/12
 * Time: 1:15 AM
 * Controller for Mainmenu, options, login,
 */
public class MenuScreenController extends AbstractCasterState implements ScreenController {

    private Nifty nifty;
    private Screen screen;

    public MenuScreenController() {
        this.app = CasterApplication.getInstance();
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
    }

    @Override
    public void onStartScreen() {
        app.getStateManager().attach(this);
        System.out.println("attached: " + screen.getScreenId());
    }

    @Override
    public void onEndScreen() {
        app.getStateManager().detach(this);
        System.out.println("detached: " + screen.getScreenId());
    }


    public void gotoScreen(String screen) {
        nifty.gotoScreen(screen);
    }

    public void join() {

    }

    public void login() {
        String username = screen.findNiftyControl("textfield-username", TextField.class).getRealText();
        String password = screen.findNiftyControl("textfield-password", TextField.class).getRealText();
        app.getProtocol().login(username, password);
        nifty.gotoScreen("char-select-screen");
        // do nothing, if we receive confirmation, CasterProtocol class will redirect us from here
    }

    private class ListItem {
        public String name;
        public Integer id;

        private ListItem(String name, Integer id) {
            this.name = name;
            this.id = id;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    @Override
    public void update(float tpf) {
        if (app.getData().loggedIn) {
            ListBox<ListItem> listbox = screen.findNiftyControl("listbox-select", ListBox.class);
            for (Map.Entry<Integer, HashMap<String, String>> crit : app.getData().creatures.entrySet()) {
                Integer id = crit.getKey();
                String name = crit.getValue().get("name");
                listbox.addItem(new ListItem(name, id));
            }
            app.getData().loggedIn = false;
        }
    }
}
