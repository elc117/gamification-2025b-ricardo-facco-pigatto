package com.rpgoop.game;

import java.util.List;
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
    private final List<String> answers;
    private final int correctIndex;
    private boolean completed = false;
    private final BitmapFont font;
    private final GlyphLayout layout = new GlyphLayout();

    private final float[][] answersSlots = {
        {415, 500}, 
        {815, 500},
        {415, 250}, 
        {815, 250}  
    };

    public QuizScene(QuizModel model) {
        this.backgroundTex = new Texture(model.background);
        this.question = model.question;
        this.answers = model.answers;
        this.correctIndex = model.correctIndex;

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

        float questionWidth = worldW * 0.4f; 
        layout.setText(font, question, font.getColor(), questionWidth, Align.left, true);        
        font.draw(batch, layout, 440, 910);

        for (int i = 0; i < answers.size(); i++) {
            String text = answers.get(i);
            float x = answersSlots[i][0];
            float y = answersSlots[i][1];

            layout.setText(font, text, font.getColor(), 200, Align.center, true);
            font.draw(batch, layout, x, y);
        }
    }

    public boolean getCompleted() { 
        return completed; 
    }

    public void dispose() {
        backgroundTex.dispose();
        font.dispose();
    }
}
