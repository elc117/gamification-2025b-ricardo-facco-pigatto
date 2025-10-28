package com.rpgoop.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class QuizEntity extends Entity {
    public QuizEntity(String texturePath, float startX, float startY, float width, float height) {
        super(texturePath, startX, startY, width, height);
    }

    @Override
    public void update(float dt) {
        
    }

    @Override
    public void render(SpriteBatch batch) {
        if (!visible) return;
        batch.draw(texture, pos.x, pos.y, width, height);
    }
}
