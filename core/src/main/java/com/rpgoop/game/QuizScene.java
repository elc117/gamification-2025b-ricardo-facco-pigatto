package com.rpgoop.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;

public class QuizScene {
    private final Texture backgroundTex;
    private final String question;
    private final BitmapFont font;
    private final GlyphLayout layout = new GlyphLayout();
    private boolean completed = false;

    public QuizScene(QuizModel model) {
        this.backgroundTex = new Texture(model.background);
        this.question = model.question;

        this.font = new BitmapFont(); 
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getData().setScale(1.8f);
        font.setColor(Color.BLACK);
    }

    public void update(float dt) {
        if (Gdx.input.justTouched()) {
            completed = true;
        }
    }

    public void render(SpriteBatch batch, float worldW, float worldH) {
        batch.draw(backgroundTex, 0, 0, worldW, worldH);

        float wrapWidth = worldW * 0.4f; 
        layout.setText(font, question, font.getColor(), wrapWidth, Align.left, true);
        float x = (worldW - wrapWidth) / 2f;
        float y = (worldH + layout.height) / 2f + worldH * 0.27f; 
        font.draw(batch, layout, x, y);
    }

    public boolean getCompleted() { return completed; }

    public void dispose() {
        backgroundTex.dispose();
        font.dispose();
    }
}
