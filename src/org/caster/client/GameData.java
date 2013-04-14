package org.caster.client;

import com.jme3.scene.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: daniil
 * Date: 08.04.13
 * Time: 22:03
 */
public class GameData {
    public Node playerNode;
    public Node worldNode;

    public Map<Integer, HashMap<String, String>> creatures = new HashMap<>();
    public String location;
    public int turn;

    // Used in menuscreencontroller for indication that client should populate creature-list-box
    public boolean loggedIn = false;
}
