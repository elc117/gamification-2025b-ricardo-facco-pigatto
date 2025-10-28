package com.rpgoop.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MapScene {
    private final Texture backgroundTex;
    private final Player player;
    private final List<Entity> entities = new ArrayList<>();

    public MapScene(MapModel model) {
        backgroundTex = new Texture(model.background);

        MapModel.PlayerSpec p = model.player;
        player = new Player(p.image, p.x, p.y, p.scale);

        for (MapModel.EntitySpec e : model.entities) {
            entities.add(new QuizEntity(e.image, e.x, e.y, e.w, e.h));
            entities.add(new MoveEntity(e.image, e.x, e.y, e.w, e.h));
        }
    }

    public Player getPlayer() { 
        return player; 
    }

    public void update(float dt) {
        for (Entity e : entities) e.update(dt);
        player.update(dt);
    }

    public void render(SpriteBatch batch, float worldW, float worldH) {
        batch.draw(backgroundTex, 0, 0, worldW, worldH);
        for (Entity e : entities) e.render(batch);
        player.render(batch);
    }

    public void dispose() {
        backgroundTex.dispose();
        for (Entity e : entities) e.dispose();
        player.dispose();
    }
}
