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
    private final Texture endBackgroundTex;
    private final PlayerModel player;
    private final PlayerView playerView;
    private final List<Entity> entities = new ArrayList<>();

    private QuizScene activeQuiz;
    private QuizEntity activeQuizOwner;
    private Set<String> completedQuizzes = new HashSet<>();

    private String requestedNextMap = null;
    private Float requestedSpawnX = null;
    private Float requestedSpawnY = null;
    private String requestedMusic = null;

    public MapScene(MapModel model) {
        this.backgroundTex = new Texture(model.background);
        this.endBackgroundTex = new Texture("endbackground.png");

        MapModel.PlayerSpec p = model.player;
        this.player = new PlayerModel(p.x, p.y, 200f, 0f, 0f);
        this.playerView = new PlayerView(p.image, p.scale, this.player);

        for (MapModel.EntitySpec e : model.entities) {
            if ("quiz".equals(String.valueOf(e.type))) {
                entities.add(new QuizEntity(e.image, e.x, e.y, e.w, e.h, e.file));
            } 
            else if ("move".equals(String.valueOf(e.type))) {
                entities.add(new MoveEntity(e.image, e.x, e.y, e.w, e.h, e.file, e.spawnX, e.spawnY, e.music));
            }
        }
    }

    public void update(float dt, Viewport viewport) {
        if (activeQuiz != null) {
            activeQuiz.update(dt, viewport);
            if (activeQuiz.getCompleted()) {
                activeQuiz.dispose();
                activeQuiz = null;
                activeQuizOwner.setCheck(true);
                completedQuizzes.add(activeQuizOwner.getFile());
                activeQuizOwner = null;

            }
            return;
        }

        PlayerMovement.update(player, dt);
        for (Entity e : entities) e.update(dt);

        for (Entity e : entities) {
            if (e instanceof QuizEntity) {
                QuizEntity qe = (QuizEntity)e;

                if (completedQuizzes.contains(qe.getFile())) {
                    qe.setCheck(true);
                    continue;
                }

                if (isNearPlayer(qe, 80f)) {
                    String path = qe.getFile();
                    activeQuiz = new QuizScene(QuizLoader.load(path));
                    activeQuizOwner = qe;
                    break;
                } 
            }
            else if (e instanceof MoveEntity) {
                MoveEntity me = (MoveEntity)e;
                if (isNearPlayer(me, 80f)) {
                    requestedNextMap = me.getFile();
                    requestedSpawnX = me.getSpawnX();
                    requestedSpawnY = me.getSpawnY();
                    requestedMusic = me.getMusic();
                    break;
                }
            }
        }
    }

    public void render(SpriteBatch batch, float worldW, float worldH, float dt) {
        if (activeQuiz != null) {
            activeQuiz.render(batch, worldW, worldH);
            return;
        }

        if(completedQuizzes.size() < 6) {
            batch.draw(backgroundTex, 0, 0, worldW, worldH);
            for (Entity e : entities) {
                e.render(batch);
            }
            playerView.render(batch, player, dt);
        } else {
            batch.draw(endBackgroundTex, 0, 0, worldW, worldH);
        } 
    }

    public void setPlayerTarget(float worldX, float worldY) {
        player.targetX = worldX - player.width  * 0.5f;
        player.targetY = worldY - player.height * 0.5f;
    }

    public PlayerModel getPlayerModel() {
        return player;
    }

    public boolean hasActiveQuiz() {
        return activeQuiz != null;
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

    public String consumeRequestedNextMap() {
        String tmp = requestedNextMap;
        requestedNextMap = null;
        return tmp;
    }

    public Set<String> consumeCompletedQuizzes(){
        return new HashSet<>(completedQuizzes);
    }
    public void setCompletedQuizzes(Set<String> completedQuizzes) {
        this.completedQuizzes.addAll(completedQuizzes);
    }

    public Float[] consumeRequestedSpawn() {
        if(requestedSpawnX == null || requestedSpawnY == null) return null;
        Float[] tmp = new Float[] {requestedSpawnX, requestedSpawnY};
        requestedSpawnX = null;
        requestedSpawnY = null;
        return tmp;
    }

    public String consumeRequestedMusic() {
        String tmp = requestedMusic;
        requestedMusic = null;
        return tmp;
    }

    public void dispose() {
        backgroundTex.dispose();
        endBackgroundTex.dispose();
        playerView.dispose();
        for (Entity e : entities) e.dispose();
    }
}
