package com.rpgoop.game;

import java.util.Set;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.math.Vector3;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private FitViewport viewport;
    private MapScene mapScene;

    @Override
    public void create() {
        batch = new SpriteBatch();
        viewport = new FitViewport(1536, 1024);

        MapModel mapModel = MapLoader.load("json_files/map1.json");
        mapScene = new MapScene(mapModel);
    }

    @Override
    public void render() {
        if (Gdx.input.justTouched()) {
            int sx = Gdx.input.getX();
            int sy = Gdx.input.getY();

            Vector3 world = new Vector3(sx, sy, 0f);
            viewport.unproject(world);
            mapScene.setPlayerTarget(world.x, world.y);
        }

        mapScene.update(Gdx.graphics.getDeltaTime(), viewport);
        
        String next = mapScene.consumeRequestedNextMap();
        if (next != null) {
            Float[] spawn = mapScene.consumeRequestedSpawn();
            Set<String> completedQuizzes = mapScene.consumeCompletedQuizzes();
            
            mapScene.dispose();
            MapModel m = MapLoader.load(next);
            mapScene = new MapScene(m);
            mapScene.setCompletedQuizzes(completedQuizzes);
            
            if(spawn != null) {
                PlayerModel p = mapScene.getPlayerModel();
                p.x = spawn[0];
                p.y = spawn[1];
                p.targetX = spawn[0];
                p.targetY = spawn[1];
            }
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        mapScene.render(batch, viewport.getWorldWidth(), viewport.getWorldHeight(), Gdx.graphics.getDeltaTime());
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        mapScene.dispose();
        batch.dispose();
    }
}
