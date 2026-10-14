package io.github.projectgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;

public class Rat {

    private final Texture texture;
    private final Sprite sprite;

    private float x;
    private float y;
    private final float speed = 65;

    private int health = 2;
    private boolean alive = true;

    public Rat(float startX, float startY) {
        texture = new Texture(
            Gdx.files.internal("assets/RatPTF.png")
        );

        sprite = new Sprite(texture);
        sprite.setSize(40, 40);

        x = startX;
        y = startY;
        sprite.setPosition(x, y);
    }

    public void update(float delta, LiquidCat cat) {
        if (!alive) {
            return;
        }

        float catCenterX = cat.getX() + cat.getWidth() / 2;
        float catCenterY = cat.getY() + cat.getHeight() / 2;
        float ratCenterX = x + sprite.getWidth() / 2;
        float ratCenterY = y + sprite.getHeight() / 2;

        float dx = catCenterX - ratCenterX;
        float dy = catCenterY - ratCenterY;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance > 1) {
            x += (dx / distance) * speed * delta;
            y += (dy / distance) * speed * delta;
        }

        sprite.setPosition(x, y);
    }

    public void draw(SpriteBatch batch) {
        if (alive) {
            sprite.draw(batch);
        }
    }

    public void takeHit() {
        if (!alive) {
            return;
        }

        health--;

        if (health <= 0) {
            alive = false;
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public int getHealth() {
        return health;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return sprite.getWidth();
    }

    public float getHeight() {
        return sprite.getHeight();
    }

    public void dispose() {
        texture.dispose();
    }
}
