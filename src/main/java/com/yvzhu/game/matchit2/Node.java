package com.yvzhu.game.matchit2;

import lombok.Data;

/**
 * @author zhangyudong.yvzhu@bytedance.com
 * @since 2022/2/11
 */
@Data
public class Node implements Comparable<Node> {
    private int preDirect = 0;
    private int cost = 0;
    private int cx,cy;
    private int distance;
    private int corner = 0;
    public Node(int x,int y,int distance) {
        this.cx = x;
        this.cy = y;
        this.distance = distance;
    }

    @Override
    public int compareTo(Node o) {
        if (distance == o.getDistance()) {
            return cost - o.getCost();
        }
        return distance - o.getDistance();
    }
}
