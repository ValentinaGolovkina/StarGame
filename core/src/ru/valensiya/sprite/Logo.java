package ru.valensiya.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.valensiya.base.Sprite;

public class Logo extends Sprite {

    private final float V_LEN = 0.005f;
    private Vector2 tmp;
    private Vector2 touch;
    private Vector2 speed;

    public Logo(Texture texture){
        super(new TextureRegion(texture));
        tmp = new Vector2(0,0);
        speed = new Vector2(0,0);
        touch = new Vector2(0,0);
        this.pos.set(new Vector2(0,0));
        setSize(0.2f,0.2f);
    }

    @Override
    public void update(float delta) {
        tmp.set(pos);
        if(tmp.sub(touch).len()>speed.len())
            pos.add(speed);
        else pos.set(touch);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        this.touch.set(touch);
        speed = touch.cpy().sub(pos).setLength(V_LEN);
        return false;
    }
}
