package com.rpgoop.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Player extends Entity {
    private final TextureRegion f0, f1;
    private TextureRegion current;

    private final Vector2 target = new Vector2();
    private final Vector2 tmp = new Vector2();

    private float speed = 200f;     
    private float timer = 0f;       
    private boolean facingLeft = false;

    public Player(String sheetPath, float startX, float startY, float scale) {
        super(sheetPath, startX, startY); 

        int frameW = texture.getWidth() / 2;
        int frameH = texture.getHeight();
        f0 = new TextureRegion(texture, 0,       0, frameW, frameH);
        f1 = new TextureRegion(texture, frameW,  0, frameW, frameH);
        current = f0;

        this.width  = frameW / scale;
        this.height = frameH / scale;

        target.set(pos);
    }

    public void setTargetWorld(float x, float y) {
        target.set(x, y);
    }

    public void setSpeed(float pxPerSecond) { 
        this.speed = pxPerSecond; 
    }

    @Override
    public void update(float dt) {
        tmp.set(target).sub(pos);
        float dist = tmp.len();

        if (dist > 0.5f) {
            if (tmp.x < -0.5f) facingLeft = true;
            else if (tmp.x > 0.5f) facingLeft = false;

            float step = Math.min(dist, speed * dt);
            tmp.nor().scl(step);
            pos.add(tmp);

            timer += dt;
            boolean toggle = ((int)(timer / 0.25f) & 1) == 1;
            current = toggle ? f1 : f0;
        } else {
            timer = 0f;
            current = f0;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if (!visible) return;

        if (facingLeft) {
            batch.draw(current, pos.x + width, pos.y, -width, height);
        } else {
            batch.draw(current, pos.x, pos.y, width, height);
        }
    }
}