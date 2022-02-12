package com.yvzhu.game.matchit2;

import java.util.PriorityQueue;

/**
 * @author zhangyudong.yvzhu@bytedance.com
 * @since 2022/2/11
 */
public class PathUtil {
    private static int[] dx = {0, 0, -1, 1};
    private static int[] dy = {-1, 1, 0, 0};

    public static boolean hasPath(int[][] mp, Position pre, Position cur) {
        int w = mp.length, h = mp.length;
        PriorityQueue<Node> pq = new PriorityQueue<>();
        int tx = cur.getX(), ty = cur.getY();
        Node root = new Node(pre.getX(), pre.getY(), getDistance(pre.getX(), pre.getY(), tx, ty));
        pq.add(root);
        while (pq.size() > 0) {
            Node nd = pq.poll();
            if (nd.getCorner() > 2) continue;
            if (nd.getCx() == tx && nd.getCy() == ty)return true;
            for (int i = 0; i < dx.length; i++) {
                int nx = nd.getCx() + dx[i], ny = nd.getCy() + dy[i];
                if (nx < 0 || nx > w || ny < 0 || ny > h) continue;
                if (!((nx == tx && ny == ty) || (mp[nx][ny] == 0))) {
                    continue;
                }
                Node newNode = new Node(nx, ny, getDistance(nx, ny, tx, ty));
                newNode.setCost(nd.getCost() + 1);
                int dr = i < 2 ? 1 : 2;
                newNode.setCorner(dr == nd.getPreDirect() ? nd.getCorner() : nd.getCorner() + 1);
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