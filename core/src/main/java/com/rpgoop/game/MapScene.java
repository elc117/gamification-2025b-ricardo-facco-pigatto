package com.rpgoop.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MapScene {
    private final Texture backgroundTex;
    private final PlayerModel player;
    private final PlayerView playerView;
    private final List<Entity> entities = new ArrayList<>();
    private QuizScene activeQuiz;
    private Set<Entity> consumedQuizzes = new HashSet<>();
    private int completedQuizzes = 0;

    public MapScene(MapModel model) {
        this.backgroundTex = new Texture(model.background);

        MapModel.PlayerSpec p = model.player;
        this.player = new PlayerModel(p.x, p.y, 200f, 0f, 0f);
        this.playerView = new PlayerView(p.image, p.scale, this.player);

        for (MapModel.EntitySpec e : model.entities) {
            if ("quiz".equals(String.valueOf(e.type))) {
                entities.add(new QuizEntity(e.image, e.x, e.y, e.w, e.h));
            } 
            else if ("move".equals(String.valueOf(e.type))) {
                entities.add(new MoveEntity(e.image, e.x, e.y, e.w, e.h));
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

    public void update(float dt, Viewport viewport) {
        if (activeQuiz != null) {
            activeQuiz.update(dt, viewport);
            if (activeQuiz.getCompleted()) {
                activeQuiz.dispose();
                activeQuiz = null;
                completedQuizzes++;
            }
            return;
        }

        PlayerMovement.update(player, dt);
        for (Entity e : entities) e.update(dt);

        for (Entity e : entities) {
            if (e instanceof QuizEntity && !consumedQuizzes.contains(e)) {
                if (isNearPlayer(e, 80f)) { 
                    activeQuiz = new QuizScene(QuizLoader.load("quiz1.json"));
                    consumedQuizzes.add(e);
                    ((QuizEntity)e).setCheck(true);
                    break;
                }
            }
        }
    }

    private boolean isNearPlayer(Entity e, float radius) {
        float px = player.x + player.width  * 0.5f;
        float py = player.y + player.height * 0.5f;
        float ex = e.pos.x + e.width  * 0.5f;
        float ey = e.pos.y + e.height * 0.5f;
        float dx = px - ex;
        float dy = py - ey;
        return dx*dx + dy*dy <= radius*radius;
    }

    public void render(SpriteBatch batch, float worldW, float worldH, float dt) {
        if (activeQuiz != null) {
            activeQuiz.render(batch, worldW, worldH);
            return;
        }

        batch.draw(backgroundTex, 0, 0, worldW, worldH);
        for (Entity e : entities) {
            e.render(batch);
        }
        playerView.render(batch, player, dt);
    }

    public void dispose() {
        backgroundTex.dispose();
        playerView.dispose();
    }
}
