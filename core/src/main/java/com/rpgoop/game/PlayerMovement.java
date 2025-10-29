package com.rpgoop.game;

public final class PlayerMovement {
    public static void update(PlayerModel p, float dt) {
        float dx = p.targetX - p.x;
        float dy = p.targetY - p.y;
        float dist2 = dx*dx + dy*dy;
        float dist = (float)Math.sqrt(dist2);

        if (dist > 0.5f) {
            if (dx < -0.5f)      p.facingLeft = true;
            else if (dx > 0.5f)  p.facingLeft = false;

            float step = Math.min(dist, p.speed * dt);
            float nx = dx / dist;
            float ny = dy / dist;
            p.x += nx * step;
            p.y += ny * step;
        } else {
            p.x = p.targetX;
            p.y = p.targetY;
        }
    }
}