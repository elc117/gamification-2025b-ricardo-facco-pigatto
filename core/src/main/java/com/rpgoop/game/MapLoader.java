package com.rpgoop.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public final class MapLoader {
    public static MapModel load(String jsonPath) {
        FileHandle fh = Gdx.files.internal(jsonPath);
        JsonReader reader = new JsonReader();
        JsonValue root = reader.parse(fh);
        MapModel model = new MapModel();

        // background
        model.background = root.getString("background");

        // player
        JsonValue p = root.get("player");
        if (p != null) {
            MapModel.PlayerSpec ps = new MapModel.PlayerSpec();
            ps.image = p.getString("image");
            ps.x = p.getFloat("x", 0f);
            ps.y = p.getFloat("y", 0f);
            ps.scale = p.getFloat("scale", 0.35f);
            model.player = ps;
        }

        // entities
        JsonValue ents = root.get("entities");
        if (ents != null && ents.isArray()) {
            for (JsonValue e : ents) {
                MapModel.EntitySpec es = new MapModel.EntitySpec();
                es.type  = e.getString("type", null);
                es.image = e.getString("image");
                es.x = e.getFloat("x", 0f);
                es.y = e.getFloat("y", 0f);
                es.w = e.getFloat("w");
                es.h = e.getFloat("h");
                model.entities.add(es);
            }
        }
        return model;
    }
}
