package ru.valensiya.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.valensiya.math.Rect;
import ru.valensiya.pool.BulletPool;
import ru.valensiya.sprite.Bullet;

public class Ship extends Sprite{
    protected Vector2 v0;
    protected Vector2 v;

    protected Rect worldBounds;
    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV;
    protected Vector2 bulletPos;
    protected  float bulletHeight;
    protected int damage;

    protected float reloadInterval;
    protected float reloadTimer;

    protected int hp;

    protected Sound sound;

    public Ship(TextureRegion region, int rows, int cols, int frames){
        super(region,rows,cols,frames);
        v0=new Vector2();
        v=new Vector2();
    }
    public Ship(){

    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v,delta);
        reloadTimer+=delta;
        if(reloadTimer>=reloadInterval){
            reloadTimer=0;
            shoot();
        }
    }

    private void shoot(){
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion,bulletPos,bulletV,bulletHeight,worldBounds,damage);
        sound.play();
    }
}
