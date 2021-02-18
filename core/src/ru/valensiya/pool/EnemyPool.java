package ru.valensiya.pool;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.valensiya.math.Rect;
import ru.valensiya.sprite.Enemy;
import ru.valensiya.sprite.SpritesPool;

public class EnemyPool extends SpritesPool<Enemy> {

    private TextureAtlas atlas;
    private float reloadInterval;
    private float reloadTimer;
    private Rect worldBounds;

    @Override
    protected Enemy newObject() {
        return new Enemy(atlas);
    }

    public EnemyPool(TextureAtlas atlas){
        this.atlas = atlas;
        reloadInterval = 10f;
    }

    @Override
    public void updateActiveSprites(float delta) {
        reloadTimer+=delta;
        if(reloadTimer>=reloadInterval){
            reloadTimer=0;
            Enemy enemy = obtain();
            enemy.set(worldBounds);
        }
        super.updateActiveSprites(delta);
    }

    public void resize(Rect worldBounds){
        this.worldBounds = worldBounds;
        for(Enemy e : getActiveObjects()){
            e.resize(worldBounds);
        }
    }
}
