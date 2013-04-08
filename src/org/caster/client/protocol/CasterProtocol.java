package org.caster.client.protocol;

import org.caster.client.GameData;

/**
 * Created by IntelliJ IDEA.
 * User: daniil
 * Date: 08.04.13
 * Time: 21:48
 */
public interface CasterProtocol {

    ////////////////////
    // From client
    ////////////////////
    public void chat(String message);

    public void login(String username, String password);

    public void join(String charId);

    public void createCharacter(String properties);

    public void action(String type, String... details);

    public void disconnect();

    public void showInventory();

    public void showMap();

    public void showSkills();

    public void showStats();

    ////////////////////
    // From server
    ////////////////////
    public void processMessage(String message, GameData data);
}
