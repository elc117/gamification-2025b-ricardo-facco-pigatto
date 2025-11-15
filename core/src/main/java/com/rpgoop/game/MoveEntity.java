package com.rpgoop.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MoveEntity extends Entity {
    private final String file;
    private final Float spawnX;
    private final Float spawnY;

    public MoveEntity(String texturePath, float startX, float startY, float width, float height, String file, Float spawnX, Float spawnY) {
        super(texturePath, startX, startY, width, height);

        this.file = file;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, pos.x, pos.y, width, height);
    }

    public String getFile() { 
        return file; 
    }

    public Float getSpawnX() { 
        return spawnX; 
    }

    public Float getSpawnY() { 
        return spawnY; 
    }
}