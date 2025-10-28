package com.rpgoop.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class MapLoader {
    public static MapModel load(String mapaTxtPath) {
        FileHandle fh = Gdx.files.internal(mapaTxtPath);
        String content = fh.readString("UTF-8");

        MapModel model = new MapModel();
        String[] lines = content.split("\\R");
        boolean bgSet = false;

        for (String raw : lines) {
            String l = raw.trim();
            if (l.isEmpty() || l.startsWith("#") || l.startsWith("//")) continue;

            if (!bgSet) { 
                model.background = l;
                bgSet = true;
                continue;
            }

            if (l.startsWith("PLAYER;")) {
                String[] parts = l.split(";");

                MapModel.PlayerSpec p = new MapModel.PlayerSpec();
                p.image = parts[1].trim();
                p.x = Float.parseFloat(parts[2].trim());
                p.y = Float.parseFloat(parts[3].trim());
                model.player = p;
                continue;
            }

            if(l.startsWith("QUIZ") || l.startsWith("MOVE")) {
                String[] parts = l.split(";");

                MapModel.EntitySpec q = new MapModel.EntitySpec();
                q.image = parts[1].trim();
                q.x = Float.parseFloat(parts[2].trim());
                q.y = Float.parseFloat(parts[3].trim());
                q.w = Float.parseFloat(parts[4].trim());
                q.h = Float.parseFloat(parts[5].trim());
                q.type = parts[6].trim();
                model.entities.add(q);
                continue;
            }

        }
        return model;
    }
}

