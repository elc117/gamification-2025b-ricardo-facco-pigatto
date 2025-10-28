package com.rpgoop.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.math.Vector3;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private FitViewport viewport;
    private MapScene scene;

    @Override
    public void create() {
        batch = new SpriteBatch();
        viewport = new FitViewport(1536, 1024);

        MapModel model = MapLoader.load("mapa1.txt");
        scene = new MapScene(model);
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();

        if (Gdx.input.justTouched()) {
            int sx = Gdx.input.getX();
            int sy = Gdx.input.getY();

            Vector3 world = viewport.getCamera().unproject(new Vector3(sx, sy, 0));
            scene.setPlayerTarget(world.x, world.y);
        }

        scene.update(dt);
        
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        scene.render(batch, viewport.getWorldWidth(), viewport.getWorldHeight(), dt);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        scene.dispose();
        batch.dispose();
    }
}
