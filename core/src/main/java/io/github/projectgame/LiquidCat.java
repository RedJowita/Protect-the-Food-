package io.github.projectgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LiquidCat {

    private final Texture texture;
    private final Sprite sprite;

    private float x = 200;
    private float y = 150;
    private final float speed = 250;

    private boolean facingRight = true;
    private boolean attacking = false;
    private boolean attackHitApplied = false;
    private float attackTimer = 0;

    private static final float ATTACK_DURATION = 0.2f;

    public LiquidCat() {
        texture = new Texture(
            Gdx.files.internal("assets/liquidCat.png")
        );

        sprite = new Sprite(texture);
        sprite.setSize(55, 55);
        sprite.setPosition(x, y);
    }

    public void update(float delta) {
        float movement = speed * delta;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= movement;
            facingRight = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += movement;
            facingRight = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            y += movement;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            y -= movement;
        }

        x = Math.max(0,
            Math.min(x, Gdx.graphics.getWidth() - sprite.getWidth()));

        y = Math.max(0,
            Math.min(y, Gdx.graphics.getHeight() - sprite.getHeight()));

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            attacking = true;
            attackTimer = ATTACK_DURATION;
            attackHitApplied = false;
        }

        if (attacking) {
            attackTimer -= delta;

            if (attackTimer <= 0) {
                attacking = false;
            }
        }

        sprite.setPosition(x, y);
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public boolean canHitRat(Rat rat) {
        if (!attacking || attackHitApplied || !rat.isAlive()) {
            return false;
        }

        float catCenterX = x + sprite.getWidth() / 2;
        float catCenterY = y + sprite.getHeight() / 2;

        float ratCenterX = rat.getX() + rat.getWidth() / 2;
        float ratCenterY = rat.getY() + rat.getHeight() / 2;

        float dx = ratCenterX - catCenterX;
        float dy = ratCenterY - catCenterY;

        boolean inFront = facingRight ? dx > 0 : dx < 0;

        return inFront
            && Math.abs(dx) <= 65
            && Math.abs(dy) <= 35;
    }

    public void markAttackHit() {
        attackHitApplied = true;
    }

    public boolean isAttacking() {
        return attacking;
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

    public boolean isFacingRight() {
        return facingRight;
    }

    public void dispose() {
        texture.dispose();
    }
}
