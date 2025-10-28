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
            Vector3 s = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            Vector3 world = viewport.unproject(s);

            float targetX = world.x - scene.getPlayer().getWidth()  / 2f;
            float targetY = world.y - scene.getPlayer().getHeight() / 2f;
            scene.getPlayer().setTargetWorld(targetX, targetY); 
        }

        scene.update(dt);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        scene.render(batch, viewport.getWorldWidth(), viewport.getWorldHeight());
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