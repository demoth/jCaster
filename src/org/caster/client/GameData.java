package org.caster.client;

import com.jme3.scene.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: daniil
 * Date: 08.04.13
 * Time: 22:03
 * To change this template use File | Settings | File Templates.
 */
public class GameData {
    public Node playerNode;
    public Node worldNode;

    public Map<String,Map<String, String>> creatures = new HashMap<>();
}
