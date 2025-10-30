package com.rpgoop.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class QuizScene {
    private final Texture backgroundTex;
    private boolean completed = false;

    public QuizScene(QuizModel model) {
        this.backgroundTex = new Texture(model.background);
    }

    public void update(float dt) {
        if (Gdx.input.justTouched()) {
            completed = true;
        } 
    }

    public void render(SpriteBatch batch, float worldW, float worldH) {
        batch.draw(backgroundTex, 0, 0, worldW, worldH);
    }


    public boolean getCompleted() { 
        return completed;
    }

    public void setCompleted(boolean c) {
        this.completed = c;
    }

    public void dispose() {
        backgroundTex.dispose();
    }
}
