package org.caster.client.protocol;

import org.caster.client.GameData;
import org.json.JSONArray;

import java.util.Queue;

/**
 * Created by IntelliJ IDEA.
 * User: daniil
 * Date: 08.04.13
 * Time: 21:47
 * To change this template use File | Settings | File Templates.
 */
public class CasterJSONProtocol implements CasterProtocol {
    private Queue<JSONArray> in;
    private Queue<JSONArray> out;
    

    public CasterJSONProtocol(Queue<JSONArray> in, Queue<JSONArray> out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public void chat(String message) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void login(String username, String password) {
        //To change body of implemented methods use File | Settings | File Templates.
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
    public void showInventory() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void showMap() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void showSkills() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void showStats() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void processMessage(String message, GameData data) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
