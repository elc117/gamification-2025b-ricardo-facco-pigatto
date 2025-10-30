package com.rpgoop.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public final class PlayerView {
    private final Texture sheet;
    private final TextureRegion idle;
    private final TextureRegion f1;
    private final TextureRegion f2;
    private final TextureRegion f3;
    private final TextureRegion f4;
    private TextureRegion current;
    private float timer = 0f;
    private final int frameWpx;
    private final int frameHpx;

    public PlayerView(String sheetPath, float scale, PlayerModel model) {
        this.sheet = new Texture(Gdx.files.internal(sheetPath));
        this.frameWpx = sheet.getWidth() / 5;
        this.frameHpx = sheet.getHeight();

        this.idle = new TextureRegion(sheet, 0 * frameWpx, 0, frameWpx, frameHpx);
        this.f1 = new TextureRegion(sheet, 1 * frameWpx, 0, frameWpx, frameHpx);
        this.f2 = new TextureRegion(sheet, 2 * frameWpx, 0, frameWpx, frameHpx);
        this.f3 = new TextureRegion(sheet, 3 * frameWpx, 0, frameWpx, frameHpx);
        this.f4 = new TextureRegion(sheet, 4 * frameWpx, 0, frameWpx, frameHpx);
        this.current = idle;

        if (model.width <= 0f || model.height <= 0f) {
            model.width  = frameWpx / scale;  
            model.height = frameHpx / scale;
        }
    }

    public void render(SpriteBatch batch, PlayerModel model, float dt) {
        float dx = model.targetX - model.x;
        float dy = model.targetY - model.y;
        float dist2 = dx*dx + dy*dy;
    
        timer += dt;
        if (dist2 > 0.5f * 0.5f) {
            int frameIndex = (int)(timer / 0.15f) % 4; 
            switch (frameIndex) {
                case 0: current = f1; break;
                case 1: current = f2; break;
                case 2: current = f3; break;
                default: current = f4; break;
            }
        } else {
            current = idle;
            timer = 0f;
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
