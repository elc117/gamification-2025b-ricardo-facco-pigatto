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
        model.question = root.getString("question");
        model.correctIndex = root.getInt("correctIndex");

        JsonValue answ = root.get("answers");
        for (JsonValue a : answ) {
            model.answers.add(a.asString());
        }

        return model;
    }
}
