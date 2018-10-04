package com.mygdx.game.widget;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class ToggleButton {

    Rectangle rect;
    GlyphLayout layout;
    BitmapFont font;
    int posX, posY;
    float textMargin;

    boolean isCheck = false;
    Texture check;
    Texture uncheck;

    long lastToggleTime = 0;

    public ToggleButton (String text, BitmapFont f, int x, int y) {
        layout = new GlyphLayout();
        rect = new Rectangle();
        font = f;
        posX = x;
        posY = y;

        check = new Texture("check.png");
        uncheck  = new Texture("uncheck.png");

        textMargin = 1.2f * check.getWidth();

        layout.setText(font, text);
        rect.set(x, y - layout.height, layout.width + textMargin, layout.height);
        System.out.println("x: " + x + " y: " + y + " w: " + rect.width + " h: " + rect.height);
    }

    public boolean isClick(Vector3 touch) {
        if (lastToggleTime == 0 || (System.currentTimeMillis() - lastToggleTime) > 500) {
            return rect.contains(touch.x, touch.y);
        }
        return false;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(isCheck ? check : uncheck, posX, posY - check.getHeight());
        font.draw(batch, layout, posX + textMargin, posY);
    }

    public void toggle() {
        isCheck = !isCheck;
        lastToggleTime = System.currentTimeMillis();
    }

    public void dispose() {
        check.dispose();
        uncheck.dispose();
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean isChecked) {
        isCheck = isChecked;
    }
}
