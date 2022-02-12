package com.yvzhu.game.matchit2;

import lombok.Data;

/**
 * TODO
 *
 * @author zhangyudong.yvzhu@bytedance.com
 * @since 2022/2/11
 */
@Data
public class Position {
    private int x, y, val;

    Position(int x, int y, int val) {
        this.x = x;
        this.y = y;
        this.val = val;
    }
}
