package com.rpgoop.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public final class PlayerView {
    private final Texture sheet;
    private final TextureRegion f0;
    private final TextureRegion f1;
    private TextureRegion current;
    private float timer = 0f;
    private final int frameWpx;
    private final int frameHpx;

    public PlayerView(String sheetPath, float scale, PlayerModel model) {
        this.sheet = new Texture(Gdx.files.internal(sheetPath));
        this.frameWpx = sheet.getWidth() / 2;
        this.frameHpx = sheet.getHeight();

        this.f0 = new TextureRegion(sheet, 0,          0, frameWpx, frameHpx);
        this.f1 = new TextureRegion(sheet, frameWpx,   0, frameWpx, frameHpx);
        this.current = f0;

        if (model.width <= 0f || model.height <= 0f) {
            model.width  = frameWpx / scale;  
            model.height = frameHpx / scale;
        }
    }

    public void render(SpriteBatch batch, PlayerModel model, float dt) {
        float dx = model.targetX - model.x;
        float dy = model.targetY - model.y;
        float dist2 = dx*dx + dy*dy;

        if (dist2 > 0.5f * 0.5f) {
            timer += dt;
            boolean toggle = ((int)(timer / 0.25f) & 1) == 1;
            current = toggle ? f1 : f0;
        } else {
            timer = 0f;
            current = f0;
        }

        if (model.facingLeft) {
            batch.draw(current, model.x + model.width, model.y, -model.width, model.height);
        } else {
            batch.draw(current, model.x, model.y, model.width, model.height);
        }
    }

    public void dispose() {
        sheet.dispose();
    }
}
