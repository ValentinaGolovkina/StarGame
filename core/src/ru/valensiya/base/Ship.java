package ru.valensiya.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.valensiya.math.Rect;
import ru.valensiya.pool.BulletPool;
import ru.valensiya.pool.ExplosionPool;
import ru.valensiya.sprite.Bullet;
import ru.valensiya.sprite.Explosion;

public class Ship extends Sprite{
    private static final float DAMAGE_ANIMATE_INTERVAL=0.1f;

    protected Vector2 v0;
    protected Vector2 v;

    protected Rect worldBounds;
    protected BulletPool bulletPool;
    protected ExplosionPool explosionPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV;
    protected Vector2 bulletPos;
    protected  float bulletHeight;
    protected int damage;

    protected float reloadInterval;
    protected float reloadTimer;
    private float damageAnimateInterval = DAMAGE_ANIMATE_INTERVAL;

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
        damageAnimateInterval+=delta;
        if(damageAnimateInterval>=DAMAGE_ANIMATE_INTERVAL){
            frame=0;
        }
    }

    private void shoot(){
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion,bulletPos,bulletV,bulletHeight,worldBounds,damage);
        sound.play();
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
    }

    private void boom(){
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(),pos);
    }

    public void damage(int damage){
        hp-=damage;
        if(hp<=0){
            hp=0;
            destroy();
        }
        frame=1;
        damageAnimateInterval=0f;
    }

    public int getDamage() {
        return damage;
    }
}
