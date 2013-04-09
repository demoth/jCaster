package org.caster.client.gui;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import org.caster.client.CasterApplication;
import org.caster.client.GameData;
import org.caster.client.states.AbstractCasterState;
import org.caster.client.states.InGameState;

/**
 * Created by IntelliJ IDEA.
 * User: demoth
 * Date: 8/16/12
 * Time: 1:15 AM
 * Controller for Mainmenu, options, login,
 */
public class MenuScreenController extends AbstractCasterState implements ScreenController {

    public MenuScreenController() {
        //To change body of created methods use File | Settings | File Templates.
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onStartScreen() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onEndScreen() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * disable this state, enable ingamestate
     */
    public void startGame() {

    }
}
