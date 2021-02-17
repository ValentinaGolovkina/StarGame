package ru.valensiya.pool;

import ru.valensiya.sprite.Bullet;
import ru.valensiya.sprite.SpritesPool;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
