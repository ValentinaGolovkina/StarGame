package ru.valensiya.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.valensiya.base.Sprite;
import ru.valensiya.math.Rect;
import ru.valensiya.math.Rnd;

public class Enemy extends Sprite {
    private static final float HEIGHT = 0.15f;

    private final Vector2 v;
    private Rect worldBounds;

    public Enemy(TextureAtlas atlas){
        super(atlas.findRegion("enemy1"), 1, 2, 2);
        this.v = new Vector2(0,-0.02f);
        setHeightProportion(HEIGHT);
    }

    public void set(Rect worldBounds){
        this.worldBounds=worldBounds;
        float x = Rnd.nextFloat(worldBounds.getLeft()+getHalfWidth(), worldBounds.getRight()-getHalfWidth());
        pos.set(x,0);
        setTop(worldBounds.getTop());
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds=worldBounds;
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v,delta);
        if(isOutside(worldBounds)){
            destroy();
        }
    }
}
