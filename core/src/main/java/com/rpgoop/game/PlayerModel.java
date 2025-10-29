package com.rpgoop.game;

public final class PlayerModel {
    public float x;
    public float y;
    public float targetX;
    public float targetY;
    public float width;
    public float height;
    public float speed = 200f; 
    public boolean facingLeft = false;

    public PlayerModel(float x, float y, float speed, float width, float height) {
        this.x = x;
        this.y = y;
        this.targetX = x;
        this.targetY = y;
        this.speed = speed;
        this.width = width;
        this.height = height;
    }
}