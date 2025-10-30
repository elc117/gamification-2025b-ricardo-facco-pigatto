package com.rpgoop.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class QuizLoader {
    public static QuizModel load(String jsonPath) {
        FileHandle fh = Gdx.files.internal(jsonPath);
        JsonReader reader = new JsonReader();
        JsonValue root = reader.parse(fh);
        QuizModel model = new QuizModel();

        model.background = root.getString("background");

        return model;
    }
}
