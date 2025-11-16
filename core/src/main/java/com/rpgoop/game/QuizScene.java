package com.rpgoop.game;

import java.util.List;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

public class QuizScene {
    private final Texture backgroundTex;
    private final String question;
    private final List<String> answers;
    private final int correctIndex;
    private boolean completed = false;
    private final BitmapFont font;
    private final GlyphLayout layout = new GlyphLayout();
    private Texture falseCheckTex;
    private int wrongIndex = -1;

    private final float[][] answersSlots = {
        {370, 500}, 
        {780, 500},
        {370, 250}, 
        {780, 250}  
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
        this.falseCheckTex = new Texture("false_check.png");
    }

    public void update(float dt, Viewport viewport) {
        if (Gdx.input.justTouched()) {
            if (checkAnswer(viewport)) {
                completed = true;
            }
        }
    }

    public void render(SpriteBatch batch, float worldW, float worldH) {
        batch.draw(backgroundTex, 0, 0, worldW, worldH);
 
        layout.setText(font, question, font.getColor(), 680, Align.left, true);        
        font.draw(batch, layout, 400, 910);

        for (int i = 0; i < answers.size(); i++) {
            String text = answers.get(i);
            float x = answersSlots[i][0];
            float y = answersSlots[i][1];

            layout.setText(font, text, font.getColor(), 285, Align.center, true);
            font.draw(batch, layout, x, y);

            if (wrongIndex == i) {
                batch.draw(falseCheckTex, x+40, y-150, 200, 200);
            }
        }
    }

    public boolean checkAnswer(Viewport viewport) {
        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.unproject(touchPos); 
        float x = touchPos.x;
        float y = touchPos.y;

        for (int i = 0; i < answers.size(); i++) {
            float answerX = answersSlots[i][0] - 10;
            float answerY = answersSlots[i][1] - 100;
            float w = 300, h = 115;

            if (x >= answerX && x <= answerX + w &&
                y >= answerY && y <= answerY + h) {
                if(i == correctIndex) {
                    return true;
                } else wrongIndex = i;
            }
        }
        return false;
    }


    public boolean getCompleted() { 
        return completed; 
    }

    public void dispose() {
        backgroundTex.dispose();
        font.dispose();
    }
}
