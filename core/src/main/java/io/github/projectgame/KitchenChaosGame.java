package io.github.projectgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class KitchenChaosGame extends ApplicationAdapter {

    private SpriteBatch batch;
    private Texture kitchen;

    private LiquidCat cat;
    private Rat rat;

    private ShapeRenderer attackShape;

    @Override
    public void create() {
        batch = new SpriteBatch();

        kitchen = new Texture(
            Gdx.files.internal("assets/KitchenPTF.png")
        );

        cat = new LiquidCat();

        attackShape = new ShapeRenderer();

        // Spawn the first rat near the black hole.
        rat = new Rat(
            Gdx.graphics.getWidth() * 0.92f,
            Gdx.graphics.getHeight() * 0.42f
        );
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        // Update characters
        cat.update(delta);
        rat.update(delta, cat);

        // Apply one hit per attack
        if (cat.canHitRat(rat)) {
            rat.takeHit();
            cat.markAttackHit();
        }

        // Clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw kitchen and characters
        batch.begin();

        batch.draw(
            kitchen,
            0, 0,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight()
        );

        rat.draw(batch);
        cat.draw(batch);

        batch.end();

        // Draw the small blue attack effect
        if (cat.isAttacking()) {
            attackShape.setProjectionMatrix(
                batch.getProjectionMatrix()
            );

            attackShape.begin(ShapeRenderer.ShapeType.Filled);
            attackShape.setColor(
                0.35f, 0.85f, 1f, 1f
            );

            float slashX;

            if (cat.isFacingRight()) {
                slashX = cat.getX() + cat.getWidth();
            } else {
                slashX = cat.getX() - 22;
            }

            attackShape.rect(
                slashX,
                cat.getY() + 27,
                22,
                3
            );

            attackShape.end();
        }
    }

    @Override
    public void dispose() {
        kitchen.dispose();
        cat.dispose();
        rat.dispose();
        attackShape.dispose();
        batch.dispose();
    }
}
