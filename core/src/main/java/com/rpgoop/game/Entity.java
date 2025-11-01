package com.rpgoop.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Entity {
    protected Texture texture;
    protected Vector2 pos = new Vector2(); 
    protected float width;
    protected float height;
    protected boolean visible = true;


    public Entity(String texturePath, float x, float y) {
        this.texture = new Texture(texturePath);
        this.width  = texture.getWidth();
        this.height = texture.getHeight();
        this.pos.set(x, y);
    }

    public Entity(String texturePath, float x, float y, float w, float h) {
        this.texture = new Texture(texturePath);
        this.width = w;
        this.height = h;
        this.pos.set(x, y);
    }

    public void render(SpriteBatch batch) {
        if (!visible) return;
        batch.draw(texture, pos.x, pos.y, width, height);
    }

    public void update(float dt) { 
    }

    public void dispose() {
        texture.dispose();
    }

    public float getX() { 
        return pos.x; 
    }
    public float getY() { 
        return pos.y; 
    }
    public float getWidth() { 
        return width; 
    }
    public float getHeight() { 
        return height; 
    }
    public void setPosition(float x, float y) { 
        pos.set(x, y); 
    }
    public void setVisible(boolean v) { 
        visible = v;
    }
}