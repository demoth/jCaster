package org.caster.client.protocol;

import com.jme3.asset.AssetManager;
import org.caster.client.GameData;

/**
 * Created by IntelliJ IDEA.
 * User: daniil
 * Date: 08.04.13
 * Time: 21:48
 */
public interface CasterProtocol {
    // MISC
    public void setAm(AssetManager am);

    ////////////////////
    // From client
    ////////////////////
    public void chat(String message);

    /**
     * @param username
     * @param password
     * @return "success" or "fail"
     */
    public void login(String username, String password);

    public void join(Integer charId);

    public void createCharacter(String properties);

    public void action(String type, String... details);

    public void disconnect();

    public void getInventory();

    public void getTerrain();

    public void getSkills();

    public void getStats();

    ////////////////////
    // From server
    ////////////////////
    public void processMessages(GameData data);
}
