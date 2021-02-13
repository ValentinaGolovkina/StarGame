package ru.valensiya.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.valensiya.base.Sprite;
import ru.valensiya.math.Rect;
import ru.valensiya.math.Rnd;

public class Star extends Sprite {

    private final Vector2 speed;
    private Rect worldBounds;

    public  Star(TextureAtlas atlas){
        super(atlas.findRegion("star"));
        setHeightProportion( Rnd.nextFloat(0.005f, 0.015f));
        speed = new Vector2(Rnd.nextFloat(-0.005f, 0.005f),getHeight()*-15);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(speed,delta);
        if(getRight()<worldBounds.getLeft()){
            setLeft(worldBounds.getRight());
        }
        if(getLeft()>worldBounds.getRight()){
            setRight(worldBounds.getLeft());
        }
        if(getTop()<worldBounds.getBottom()){
            setBottom(worldBounds.getTop());
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        float x = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float y = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        pos.set(x,y);
    }
}
