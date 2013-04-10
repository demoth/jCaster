package org.caster.client.gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import org.caster.client.CasterApplication;
import org.caster.client.states.AbstractCasterState;

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
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onEndScreen() {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void gotoScreen(String screen) {
        nifty.gotoScreen(screen);
    }

    public void login() {
        String username = screen.findNiftyControl("textfield-username", TextField.class).getText();
        String password = screen.findNiftyControl("textfield-password", TextField.class).getText();
        app.getProtocol().login(username, password);
        // do nothing, if we receive confirmation, CasterProtocol class will redirect us from here
    }
}
