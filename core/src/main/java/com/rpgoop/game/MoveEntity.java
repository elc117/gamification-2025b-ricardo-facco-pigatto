package com.rpgoop.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MoveEntity extends Entity {
    private final String file;

    public MoveEntity(String texturePath, float startX, float startY, float width, float height, String file) {
        super(texturePath, startX, startY, width, height);

        this.file = file;
    }

    @Override
    public void update(float dt) {
        
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, pos.x, pos.y, width, height);
    }

    public String getFile() { 
        return file; 
    }
}