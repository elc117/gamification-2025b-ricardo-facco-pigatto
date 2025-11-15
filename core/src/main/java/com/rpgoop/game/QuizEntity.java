package com.rpgoop.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class QuizEntity extends Entity {
    private boolean check = false;
    private static Texture checkTex;
    private final String file;

    public QuizEntity(String texturePath, float startX, float startY, float width, float height, String file) {
        super(texturePath, startX, startY, width, height);
        checkTex = new Texture("check.png");

        this.file = file;
    }

    public void setCheck(boolean c) { 
        check = c; 
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, pos.x, pos.y, width, height);
        if (check) {
            batch.draw(checkTex, pos.x, pos.y, width, height);
        }
    }

    public String getFile() { 
        return file; 
    }
}