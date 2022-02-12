package com.yvzhu.game.ui.TileEngine;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {
    public static final TETile PLAYER = new TETile('@', Color.white, Color.black, "player");
    public static final TETile NOTHING = new TETile(' ', Color.BLACK, Color.black, "nothing");
    public static final TETile A1_1 = new TETile('㊎', Color.RED, Color.black, "A");
    public static final TETile A2_1 = new TETile('㊍', Color.RED, Color.black, "A");
    public static final TETile A3_1 = new TETile('㊌', Color.RED, Color.black, "A");
    public static final TETile A4_1 = new TETile('㊋', Color.RED, Color.black, "A");
    public static final TETile A5_1 = new TETile('㊏', Color.RED, Color.black, "A");
    public static final TETile A6_1 = new TETile('㊐', Color.RED, Color.black, "A");
    public static final TETile A7_1 = new TETile('㊊', Color.RED, Color.black, "A");
    public static final TETile A8_1 = new TETile('㊣', Color.RED, Color.black, "A");
    public static final TETile A1 = new TETile('㊎', Color.ORANGE, Color.black, "A");
    public static final TETile A2 = new TETile('㊍', Color.magenta, Color.black, "A");
    public static final TETile A3 = new TETile('㊌', Color.green, Color.black, "A");
    public static final TETile A4 = new TETile('㊋', Color.pink, Color.black, "A");
    public static final TETile A5 = new TETile('㊏', Color.white, Color.black, "A");
    public static final TETile A6 = new TETile('㊐', Color.white, Color.black, "A");
    public static final TETile A7 = new TETile('㊊', Color.white, Color.black, "A");
    public static final TETile A8 = new TETile('㊣', Color.white, Color.black, "A");
    private static final Map<Integer, TETile> mp;
    static {
        mp = new HashMap<>(10);
        mp.put(999, PLAYER);
        mp.put(1, A1);
        mp.put(2, A2);
        mp.put(3, A3);
        mp.put(4, A4);
        mp.put(5, A5);
        mp.put(6, A6);
        mp.put(7, A7);
        mp.put(8, A8);
        mp.put(1001, A1_1);
        mp.put(1002, A2_1);
        mp.put(1003, A3_1);
        mp.put(1004, A4_1);
        mp.put(1005, A5_1);
        mp.put(1006, A6_1);
        mp.put(1007, A7_1);
        mp.put(1008, A8_1);
    }

    public static TETile int2Tile(int val) {
        return mp.getOrDefault(val, NOTHING);
    }

}


