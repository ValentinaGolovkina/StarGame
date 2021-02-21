package ru.valensiya.pool;

import com.badlogic.gdx.audio.Sound;

import ru.valensiya.math.Rect;
import ru.valensiya.sprite.EnemyShip;
import ru.valensiya.sprite.SpritesPool;

public class EnemyPool extends SpritesPool<EnemyShip> {

    private BulletPool bulletPool;
    private Rect worldBounds;
    private Sound sound;

    public EnemyPool(BulletPool bulletPool, Rect worldBounds, Sound sound) {
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        this.sound = sound;
    }

    protected EnemyShip newObject(){
        return new EnemyShip(bulletPool,worldBounds,sound);
    }
}
