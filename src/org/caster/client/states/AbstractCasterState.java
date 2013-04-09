package org.caster.client.states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import org.caster.client.CasterApplication;

/**
 * Created with IntelliJ IDEA.
 * User: daniil
 * Date: 09.04.13
 * Time: 13:14
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractCasterState extends AbstractAppState {
    protected AppStateManager stateManager;
    protected CasterApplication app;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        this.stateManager = stateManager;
        this.app = (CasterApplication) app;
    }
}
