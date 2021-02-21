package ru.valensiya.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.valensiya.base.BaseScreen;
import ru.valensiya.math.Rect;
import ru.valensiya.pool.BulletPool;
import ru.valensiya.pool.EnemyPool;
import ru.valensiya.sprite.Background;
import ru.valensiya.sprite.MainShip;
import ru.valensiya.sprite.Star;
import ru.valensiya.utils.EnemyEnitter;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;

    private Texture bg;
    private TextureAtlas atlas;

    private Background background;
    private Star[] stars;
    private MainShip mainShip;
    private BulletPool bulletPool;
    private EnemyPool enemyPool;

    private Music music;
    private Sound enemyBulletSound;

    private EnemyEnitter enemyEnitter;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/background_cosmos.jpg");
        atlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        background = new Background(bg);
        stars = new Star[STAR_COUNT];
        for(int i=0;i<STAR_COUNT;i++){
            stars[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        enemyBulletSound=Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        enemyPool = new EnemyPool(bulletPool, worldBounds,enemyBulletSound);
        mainShip = new MainShip(atlas,bulletPool);

        enemyEnitter = new EnemyEnitter(atlas,worldBounds,enemyPool);

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
    }

    @Override
    public void render(float delta) {
        update(delta);
        freeAllDestroyed();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for(Star star:stars)
            star.resize(worldBounds);
        mainShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        music.dispose();
        enemyBulletSound.dispose();
        enemyPool.dispose();
        mainShip.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        mainShip.touchDown(touch,pointer,button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        mainShip.touchUp(touch,pointer,button);
        return false;
    }

    public void update(float delta){
        for(Star star:stars)
            star.update(delta);
        mainShip.update(delta);
        bulletPool.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        enemyEnitter.generate(delta);
    }

    private void freeAllDestroyed(){
        bulletPool.freeDestroyedActiveSprites();
        enemyPool.freeDestroyedActiveSprites();
    }

    public void draw(){
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for(Star star:stars)
            star.draw(batch);
        mainShip.draw(batch);
        bulletPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        batch.end();
    }
}
