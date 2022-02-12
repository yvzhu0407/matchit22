package com.yvzhu.game.matchit2;

import com.yvzhu.game.ui.Frame;
import com.yvzhu.game.ui.StdDraw;
import com.yvzhu.game.ui.TileEngine.TERenderer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * @author zhangyudong.yvzhu@bytedance.com
 * @since 2022/2/11
 */
public class Game {
    public static final int WIDTH = 82;
    public static final int HEIGHT = 32;
    private TERenderer tr = null;
    private boolean isPlayerRound = false;
    private BufferedReader reader = null;
    private Frame frame;
    private Frame player;
    private Frame mp;
    private int seed = 100;
    private final String preStr = "note:";
    private String note = "";

    public void playWithKeyBoard() {
        if (!isPlayerRound) {
            initStdDraw();
        }
        tenderMain();
        if (reader == null) {
            reader = new BufferedReader(new InputStreamReader(System.in));
        }
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                switch (c) {
                    case 'n':
                    case 'N':
                        StringBuilder sb = new StringBuilder();
                        while (true) {
                            if (StdDraw.hasNextKeyTyped()) {
                                char c1 = StdDraw.nextKeyTyped();
                                if (c1 >= '0' && c1 <= '9') {
                                    sb.append(c1);
                                } else {
                                    break;
                                }
                            }
                        }
                        setSeed(Integer.parseInt(sb.toString()));
                        frame = new Frame();
                        mp = mpFactory(frame, 10, 10, seed);
                        player = new Frame(mp, 1, 1, 2, 2);
                        player.setPlayer(true);
                        isPlayerRound = true;
                        tr.renderFrame(frame.getMp());
                        break;
                    case 'L':
                    case 'l':
                        if (frame != null) {
                            tr.renderFrame(frame.getMp());
                            isPlayerRound = true;
                        }
                        break;
                    case 'q':
                    case 'Q':
                        System.exit(0);
                        break;
                    default:
                        break;
                }
                if (isPlayerRound) {
                    break;
                }
            }
        }
        Position pre = null, cur;
        while (isPlayerRound) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                if (c == 'w' || c == 'W') {
                    mp.move(player, 0, -1);
                } else if (c == 'a' || c == 'A') {
                    mp.move(player, 1, 0);
                } else if (c == 's' || c == 'S') {
                    mp.move(player, 0, 1);
                } else if (c == 'd' || c == 'D') {
                    mp.move(player, -1, 0);
                } else if (c == 'b' || c == 'B') {
                    isPlayerRound = false;
                    playWithKeyBoard();
                } else if (c == ' ') {
                    int x = player.getxOffset() + 1, y = player.getyOffset() + 1;
                    if (mp.exist(x, y)) {
                        int val = mp.getVal(x, y);
                        cur = new Position(x, y, val);
                        if (pre != null) {
                            if (pre.getVal() == val && PathUtil.hasPath(mp.getRawMp(), pre, cur)) {
                                mp.clear(pre.getX(), pre.getY());
                                mp.clear(x, y);
                                cur = null;
                            }
                        }
                        pre = cur;
                    }
                }
                tr.renderFrame(frame.getMp());
            }
        }
    }

    private void tenderMain() {
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(40, 25, "YVZHU Game");
        StdDraw.text(40, 15, "New Game (N/n) ");
        StdDraw.text(40, 19, "Quit (Q/q) ");

        StdDraw.show();
        StdDraw.pause(100);
    }

    private void initStdDraw() {
        StdDraw.enableDoubleBuffering();
        tr = new TERenderer();
        tr.initialize(WIDTH, HEIGHT);
    }

    public Frame mpFactory(Frame father, int w, int h, int seed) {
        assert father != null;
        w = Math.min(father.getW(), w);
        h = Math.min(father.getH(), h);

        Frame frame = new Frame(father, w, h, (father.getW() - w) / 2, (father.getH() - h) / 2);
        Random random = new Random(seed);
        for (int i = 1; i <= w; i++) {
            for (int j = 1; j <= h; j++) {
                frame.update(i, j, random.nextInt(8) + 1);
            }
        }
        return frame;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }
}
