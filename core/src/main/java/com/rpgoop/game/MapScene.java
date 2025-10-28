package com.rpgoop.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MapScene {
    private final Texture backgroundTex;

    private final PlayerModel player;
    private final PlayerView playerView;

    private final List<Entity> entities = new ArrayList<>();

    public MapScene(MapModel model) {
        this.backgroundTex = new Texture(model.background);

        MapModel.PlayerSpec p = model.player;
        this.player = new PlayerModel(p.x, p.y, 200f, 0f, 0f);
        this.playerView = new PlayerView(p.image, p.scale, this.player);

        for (MapModel.EntitySpec e : model.entities) {
            float w = (e.w != null ? e.w : -1f);
            float h = (e.h != null ? e.h : -1f);
            if ("quiz".equalsIgnoreCase(String.valueOf(e.type))) {
                entities.add(w > 0 && h > 0
                    ? new QuizEntity(e.image, e.x, e.y, w, h)
                    : new QuizEntity(e.image, e.x, e.y, 64, 64)); // fallback
            } else if ("move".equalsIgnoreCase(String.valueOf(e.type))) {
                entities.add(w > 0 && h > 0
                    ? new MoveEntity(e.image, e.x, e.y, w, h)
                    : new MoveEntity(e.image, e.x, e.y, 64, 64));
            }
        }
    }

    public void setPlayerTarget(float worldX, float worldY) {
        player.targetX = worldX - player.width  * 0.5f;
        player.targetY = worldY - player.height * 0.5f;
    }

    public PlayerModel getPlayerModel() {
        return player;
    }

    public void update(float dt) {
        PlayerMovement.update(player, dt);
        for (Entity e : entities) e.update(dt);
    }

    public void render(SpriteBatch batch, float worldW, float worldH, float dt) {
        batch.draw(backgroundTex, 0, 0, worldW, worldH);
        for (Entity e : entities) e.render(batch);
        playerView.render(batch, player, dt);
    }

    public void dispose() {
        backgroundTex.dispose();
        playerView.dispose();
    }
}
