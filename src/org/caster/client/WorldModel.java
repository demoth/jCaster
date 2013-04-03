package org.caster.client;

import com.jme3.material.Material;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: demoth
 * Date: 8/12/12
 * Time: 11:06 PM
 */
public class WorldModel {
    // for use in screen transfer
    public String nextScreen = "";

    public boolean wasJoining = false;

    // for use in gui; current user's creatures
    public List<String> creatures = new ArrayList<>();

    // main world node; Already acts as a Map<string,Node>
    public Node worldNode = new Node("terrain");

    // todo remove
    // like floor or wall. Should be populated at the start of the GameEngine thread
    public Map<String, Spatial> tileTypes;
    public Map<String, Material> materials;

    // objects by ids; this collection contatins item, objects and so on - everything that is visible
    public Map<Integer, Node> objects = new HashMap<>();

    // id of player's creature
    public Integer player = null;

}
