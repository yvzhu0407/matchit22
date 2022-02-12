package com.yvzhu.game.ui;

import com.yvzhu.game.ui.TileEngine.TETile;
import com.yvzhu.game.ui.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;

/**
 * Frame是一个窗口，拥有子Frame
 *
 * @author zhangyudong.yvzhu@bytedance.com
 * @since 2022/2/11
 */
public class Frame {
    private final Frame father;
    private final List<Frame> children;
    private final int h, w;
    private int[][] mp;
    private int[][] mp2;
    private TETile[][] teTileMp;
    private int xOffset;
    private int yOffset;
    private boolean update = true;
    private boolean player = false;

    private static final int MAX_WIDTH = 80, MAX_HEIGHT = 30;


    public void addChild(Frame child) {
        if (child.getW() + child.getyOffset() > w || child.getH() + child.getyOffset() > h) {
            throw new UnsupportedOperationException("father should contains child.");
        }
        children.add(child);
    }

    public Frame() {
        this(null, MAX_WIDTH, MAX_HEIGHT);
    }

    public Frame(Frame father, int w, int h) {
        this(father, w, h, 0, 0);
    }

    public Frame(Frame father, int w, int h, int xOffset, int yOffset) {
        this.father = father;
        if (father != null) {
            father.addChild(this);
        }
        if (h <= 0 || w <= 0) {
            throw new UnsupportedOperationException("size should be positive.");
        }
        this.h = Math.min(h, MAX_HEIGHT);
        this.w = Math.min(w, MAX_WIDTH);
        this.yOffset = yOffset;
        this.xOffset = xOffset;
        children = new ArrayList<>();
        mp = new int[w + 2][h + 2];
        mp2 = new int[w + 2][h + 2];
    }

    public int[][] getRawMp() {
        for(int i = 0;i < w + 1;i++){
            for(int j = 0;j < h + 1;j ++){
                mp2[i][j] = mp[i][j];
            }
        }
        for (Frame child : children) {
            child.getRawMp();
            if (child.isPlayer()) {
                updateByPlayer(child);
            } else {
                updateByChild(child);
            }
        }
        return mp2;
    }

    private void updateByPlayer(Frame child) {
        int[][] c_mp = child.getRawMp();
        for (int i = 1; i <= child.getW(); i++) {
            for (int j = 1; j <= child.getH(); j++) {
                mp2[i + child.getxOffset()][j + child.getyOffset()] = 1000 + mp[i + child.getxOffset()][j + child.getyOffset()];
            }
        }
        child.setUpdate(true);
    }

    private void updateByChild(Frame child) {
        int[][] c_mp = child.getRawMp();
        for (int i = 1; i <= child.getW(); i++) {
            for (int j = 1; j <= child.getH(); j++) {
                mp2[i + child.getxOffset()][j + child.getyOffset()] = c_mp[i][j];
            }
        }
        child.setUpdate(true);
    }

    public TETile[][] getMp() {
        if (teTileMp == null) {
            teTileMp = new TETile[w + 2][h + 2];
            for (int i = 0; i < h + 2; i++) {
                teTileMp[0][i] = Tileset.int2Tile(0);
                teTileMp[w + 1][i] = Tileset.int2Tile(0);
            }
            for (int i = 0; i < w + 2; i++) {
                teTileMp[i][0] = Tileset.int2Tile(0);
                teTileMp[i][h + 1] = Tileset.int2Tile(0);
            }
        }
        getRawMp();
        for (int i = 1; i <= w; i++) {
            for (int j = 1; j <= h; j++) {
                teTileMp[i][j] = Tileset.int2Tile(mp2[i][j]);
            }
        }
        return teTileMp;
    }

    public void clear(int x, int y) {
        update(x, y, 0);
    }

    public void update(int x, int y, int val) {

        if (x <= 0 || x > w || y <= 0 || y > h) return;
        mp[x][y] = val;
        mp2[x][y] = val;
        for (Frame child : children) {
            if (child.isUpdate()) continue;
            child.update(x, y, val);
            child.setUpdate(false);
        }
        update = false;
    }

    public boolean exist(int x, int y) {
        return x > 0 && x <= w && y > 0 && y <= h && mp[x][y] > 0;
    }

    public int getVal(int x, int y) {
        for (Frame child : children) {
            if (!child.isUpdate()) {
                updateByChild(child);
            }
        }
        return mp[x][y];
    }

    public int getH() {
        return h;
    }

    public int getW() {
        return w;
    }

    public int getxOffset() {
        return xOffset;
    }

    public int getyOffset() {
        return yOffset;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public boolean isPlayer() {
        return player;
    }

    public void setPlayer(boolean player) {
        this.player = player;
    }

    public void move(int x, int y) {
        if (xOffset - x < 0 || yOffset - y < 0) {
            return;
        }
        if (father.getW() - 1 < xOffset - x || father.getH() - 1 < yOffset - y) {
            return;
        }
        xOffset -= x;
        yOffset -= y;
        this.update = false;
    }
}
