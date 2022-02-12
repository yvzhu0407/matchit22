package com.yvzhu.game.matchit2;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * @author zhangyudong.yvzhu@bytedance.com
 * @since 2022/2/11
 */
public class PathUtil {
    private static final int[] DX = {0, 0, -1, 1};
    private static final int[] DY = {-1, 1, 0, 0};

    public static boolean hasPath(int[][] mp, Position pre, Position cur) {
        int w = mp.length, h = mp.length;
        PriorityQueue<Node> pq = new PriorityQueue<>();
        int tx = cur.getX(), ty = cur.getY();
        Node root = new Node(pre.getX(), pre.getY(), getDistance(pre.getX(), pre.getY(), tx, ty));
        pq.add(root);
        Set<Integer> seen = new HashSet<>();
        int maxTime = 100;
        while (pq.size() > 0 && maxTime-- > 0) {
            Node nd = pq.poll();
            seen.add(nd.getCx() * 1000 + nd.getCy());
            if (nd.getCorner() > 2) continue;
            if (nd.getCx() == tx && nd.getCy() == ty)return true;
            for (int i = 0; i < DX.length; i++) {
                int nx = nd.getCx() + DX[i], ny = nd.getCy() + DY[i];
                if (nx < 0 || nx >= w || ny < 0 || ny >= h || seen.contains(nx * 1000 + ny)) continue;
                if (mp[nx][ny] !=0 && (nx != tx || ny != ty)) {
                    continue;
                }
                Node newNode = new Node(nx, ny, getDistance(nx, ny, tx, ty));
                newNode.setCost(nd.getCost() + 1);
                int dr = i < 2 ? 1 : 2;
                newNode.setCorner(nd.getPreDirect() == 0 ? 0 : (dr == nd.getPreDirect() ? nd.getCorner() : nd.getCorner() + 1));
                newNode.setPreDirect(dr);
                pq.add(newNode);
            }
        }

        return false;
    }

    public static int getDistance(int x, int y, int tx, int ty) {
        return Math.abs(x - tx) + Math.abs(y - ty);
    }
}