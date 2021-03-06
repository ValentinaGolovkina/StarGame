package ru.valensiya.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.valensiya.base.Ship;
import ru.valensiya.math.Rect;
import ru.valensiya.pool.BulletPool;
import ru.valensiya.pool.ExplosionPool;

public class EnemyShip extends Ship {

    private static final float START_V_Y = -0.3f;

    public EnemyShip(BulletPool bulletPool, ExplosionPool explosionPool,Rect worldBounds, Sound sound) {
        this.bulletPool = bulletPool;
        this.explosionPool=explosionPool;
        this.worldBounds=worldBounds;
        this.sound = sound;
        v = new Vector2();
        v0 = new Vector2();
        bulletPos = new Vector2();
        bulletV = new Vector2();
    }

    @Override
    public void update(float delta) {
        if(getTop()>worldBounds.getTop()){
            reloadTimer = reloadInterval*0.8f;
        } else if (!v.equals(v0)){
            v.set(v0);
        }
        super.update(delta);
        bulletPos.set(pos.x, getBottom());
        if(getBottom()<worldBounds.getBottom()){
            destroy();
        }
    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            Vector2 bulletV,
            int damange,
            float reloadInterval,
            float height,
            int hp
    ){
        this.regions=regions;
        this.v0.set(v0);
        this.bulletRegion=bulletRegion;
        this.bulletHeight=bulletHeight;
        this.bulletV.set(bulletV);
        this.damage=damange;
        this.reloadInterval=reloadInterval;
        setHeightProportion(height);
        this.hp=hp;
        this.v.set(0,START_V_Y);
    }

    public boolean isBulletColision(Rect bullet){
        return !(bullet.getRight()<getLeft()
        || bullet.getLeft()>getRight()
        || bullet.getBottom()>getTop()
        || bullet.getTop()<pos.y);
    }
}
