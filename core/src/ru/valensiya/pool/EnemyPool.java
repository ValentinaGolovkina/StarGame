package ru.valensiya.pool;

import com.badlogic.gdx.audio.Sound;

import ru.valensiya.math.Rect;
import ru.valensiya.sprite.EnemyShip;
import ru.valensiya.sprite.SpritesPool;

public class EnemyPool extends SpritesPool<EnemyShip> {

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private Rect worldBounds;
    private Sound sound;

    public EnemyPool(BulletPool bulletPool,ExplosionPool explosionPool, Rect worldBounds, Sound sound) {
        this.bulletPool = bulletPool;
        this.explosionPool=explosionPool;
        this.worldBounds = worldBounds;
        this.sound = sound;
    }

    protected EnemyShip newObject(){
        return new EnemyShip(bulletPool,explosionPool, worldBounds,sound);
    }
}
