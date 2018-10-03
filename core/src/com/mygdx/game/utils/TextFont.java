package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class TextFont {

    public static final String ALL_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "abcdefghijklmnopqrstuvwxyz"
            + "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
            + "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"
            + "1234567890.,:;_¡!¿?\"'+-*/()[]={}";

    private static BitmapFont fontRusso;
    private static BitmapFont fontSeymour;

    public static BitmapFont getRusso() {
        if (fontRusso == null) {
            fontRusso = loadFont("RussoOne.ttf");
        }
        return fontRusso;
    }

    public static BitmapFont getSeymour() {
        if (fontSeymour == null) {
            fontSeymour = loadFont("SeymourOne.ttf");
        }
        return fontSeymour;
    }

    private static BitmapFont loadFont(String file) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(file));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 16;
        parameter.characters = ALL_CHARACTERS;
        return generator.generateFont(parameter);
    }
}
