package org.caster.client.states;

import com.jme3.app.state.AbstractAppState;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import org.caster.client.GameData;

/**
 * Created by IntelliJ IDEA.
 * User: demoth
 * Date: 8/17/12
 * Time: 12:37 AM
 * Main appstate with 3d objects;
 * implements screencontroller for Hud
 */
public class InGameState extends AbstractCasterState implements ScreenController{

    public InGameState(GameData data) {
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
}
