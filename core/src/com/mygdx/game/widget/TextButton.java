package com.mygdx.game.widget;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class TextButton {

    Rectangle rect;
    GlyphLayout layout;
    BitmapFont font;
    int posX, posY;

    public TextButton (String text, BitmapFont f, int x, int y) {
        layout = new GlyphLayout();
        rect = new Rectangle();
        font = f;
        posX = x;
        posY = y;

        layout.setText(font, text);
        rect.set(x, y - layout.height, layout.width, layout.height);
    }

    public boolean isClick(Vector3 touch) {
        return rect.contains(touch.x, touch.y);
    }

    public void draw(SpriteBatch batch) {
        font.draw(batch, layout, posX, posY);
    }
}
