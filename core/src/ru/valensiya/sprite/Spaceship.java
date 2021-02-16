package ru.valensiya.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.valensiya.base.Sprite;
import ru.valensiya.math.Rect;

public class Spaceship extends Sprite {
    float touchX;
    boolean pressedLeft;
    boolean pressedRight;
    float speed = 0.01f;
    Rect worldBounds;

    public Spaceship(TextureRegion textureRegion) {
        super(new TextureRegion(textureRegion, 0, 0, textureRegion.getRegionWidth()/2, textureRegion.getRegionHeight()));
        setHeightProportion( 0.17f);
        touchX =0;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        pos.set(0f,worldBounds.getBottom()+getHalfHeight()+0.02f);
    }

    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if(touch.x<0) pressedLeft=true;
        else pressedRight=true;
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if(touch.x<0) pressedLeft=false;
        else pressedRight=false;
        return false;
    }

    @Override
    public void update(float delta) {
        if(pressedLeft&&(getLeft()>worldBounds.getLeft()))
            pos.x-=speed;
        if(pressedRight&&(getRight()<worldBounds.getRight()))
            pos.x+=speed;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode==21)  pressedLeft=true;
        if(keycode==22)  pressedRight=true;
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode==21) pressedLeft=false;
        if(keycode==22) pressedRight = false;
        return false;
    }
}
