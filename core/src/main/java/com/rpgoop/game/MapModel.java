package com.rpgoop.game;

import java.util.ArrayList;
import java.util.List;

public class MapModel {
    public String background;
    public PlayerSpec player;
    public List<EntitySpec> entities = new ArrayList<>();

    public static class PlayerSpec {
        public String image;
        public float x, y;
        public float scale = 10f;
    }

    public static class EntitySpec {
        public String image;
        public float x, y;
        public float w, h;
        public String type;
        public String file;
        public Float spawnX;
        public Float spawnY;
    }
}