package ru.valensiya.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.valensiya.base.BaseScreen;
import ru.valensiya.math.Rect;
import ru.valensiya.pool.BulletPool;
import ru.valensiya.pool.EnemyPool;
import ru.valensiya.pool.ExplosionPool;
import ru.valensiya.sprite.Background;
import ru.valensiya.sprite.Bullet;
import ru.valensiya.sprite.EnemyShip;
import ru.valensiya.sprite.GameOver;
import ru.valensiya.sprite.MainShip;
import ru.valensiya.sprite.NewGame;
import ru.valensiya.sprite.TrackStar;
import ru.valensiya.utils.EnemyEnitter;
import ru.valensiya.utils.Font;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;
    private static final float FONT =0.02f;
    private static final float PADDING =0.01f;

    private static final String FRAGS = "Frags: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";

    private enum State {PLAYING,GAME_OVER}

    private Texture bg;
    private TextureAtlas atlas;

    private Background background;
    private TrackStar[] stars;
    private MainShip mainShip;
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;

    private Music music;
    private Sound enemyBulletSound;
    private Sound explosionSound;

    private EnemyEnitter enemyEnitter;
    private State state;

    private GameOver gameOver;
    private NewGame newGame;

    private StringBuilder sbFrags;
    private StringBuilder sbHp;
    private StringBuilder sbLevel;
    private Font font;
    private int frags;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/background_cosmos.jpg");
        atlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        background = new Background(bg);
        bulletPool = new BulletPool();
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        explosionPool = new ExplosionPool(atlas,explosionSound);
        enemyBulletSound=Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        enemyPool = new EnemyPool(bulletPool,explosionPool, worldBounds,enemyBulletSound);
        mainShip = new MainShip(atlas,bulletPool,explosionPool);
        gameOver=new GameOver(atlas);
        newGame = new NewGame(atlas,this);
        font=new Font("font/font.fnt","font/font.png");
        font.setSize(FONT);
        sbFrags=new StringBuilder();
        sbHp=new StringBuilder();
        sbLevel=new StringBuilder();

        stars = new TrackStar[STAR_COUNT];
        for(int i=0;i<STAR_COUNT;i++){
            stars[i] = new TrackStar(atlas,mainShip.getV());
        }

        enemyEnitter = new EnemyEnitter(atlas,worldBounds,enemyPool);

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
        state = State.PLAYING;
    }

    public void startNewGame(){
        state = State.PLAYING;
        mainShip.startNewGame();
        frags=0;
        bulletPool.freeAllActiveSprites();
        enemyPool.freeAllActiveSprites();
        explosionPool.freeAllActiveSprites();
    }

    @Override
    public void render(float delta) {
        update(delta);
        checkCollisions();
        freeAllDestroyed();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for(TrackStar star:stars)
            star.resize(worldBounds);
        mainShip.resize(worldBounds);
        gameOver.resize(worldBounds);
        newGame.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        explosionPool.dispose();
        music.dispose();
        enemyBulletSound.dispose();
        enemyPool.dispose();
        explosionSound.dispose();
        mainShip.dispose();
        font.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(state==State.PLAYING) {
            mainShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(state==State.PLAYING) {
            mainShip.keyUp(keycode);
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if(state==State.PLAYING){
            mainShip.touchDown(touch,pointer,button);
        } else if(state==State.GAME_OVER) {
            newGame.touchDown(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if(state==State.PLAYING){
            mainShip.touchUp(touch,pointer,button);
        }else if(state==State.GAME_OVER) {
            newGame.touchUp(touch, pointer, button);
        }
        return false;
    }

    public void update(float delta){
        for(TrackStar star:stars)
            star.update(delta,mainShip.getV());
        explosionPool.updateActiveSprites(delta);
        if(state==State.PLAYING) {
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemyEnitter.generate(delta,frags);
        }
    }

    private void checkCollisions() {
        if(state==State.GAME_OVER){
            return;
        }
        List<EnemyShip> enemyShipList = enemyPool.getActiveObjects();
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for(EnemyShip enemyShip : enemyShipList){
            if(enemyShip.isDestroyed()){
                continue;
            }
            float minDist = enemyShip.getHalfWidth()+mainShip.getHalfWidth();

            if(enemyShip.pos.dst(mainShip.pos)<minDist){
                enemyShip.destroy();
                mainShip.damage(enemyShip.getDamage());
            }
        }
        for(Bullet bullet:bulletList){
            if(bullet.isDestroyed()){
                continue;
            }
            if(bullet.getOwner()!=mainShip){
                if(mainShip.isBulletColision(bullet)){
                    mainShip.damage(bullet.getDamage());
                    bullet.destroy();
                }
                continue;
            }
            for(EnemyShip enemyShip:enemyShipList){
                if(enemyShip.isDestroyed()){
                    continue;
                }
                if(enemyShip.isBulletColision(bullet)){
                    enemyShip.damage(bullet.getDamage());
                    bullet.destroy();
                    if(enemyShip.isDestroyed()){
                        frags++;
                    }
                }
            }
        }
        if(mainShip.isDestroyed()){
            state= State.GAME_OVER;
        }
    }

    private void freeAllDestroyed(){
        bulletPool.freeDestroyedActiveSprites();
        explosionPool.freeDestroyedActiveSprites();
        enemyPool.freeDestroyedActiveSprites();
    }

    public void draw(){
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for(TrackStar star:stars)
            star.draw(batch);
        if(state == State.PLAYING) {
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
        } else if (state==State.GAME_OVER){
            gameOver.draw(batch);
            newGame.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);
        printInfo();
        batch.end();
    }

    private void printInfo(){
        sbFrags.setLength(0);
        sbHp.setLength(0);
        sbLevel.setLength(0);
        font.draw(batch,
                sbFrags.append(FRAGS).append(frags),
                worldBounds.getLeft()+PADDING,
                worldBounds.getTop()-PADDING);
        font.draw(batch,
                sbHp.append(HP).append(mainShip.getHp()),
                worldBounds.pos.x,
                worldBounds.getTop()-PADDING,
                Align.center);
        font.draw(batch,
                sbLevel.append(LEVEL).append(enemyEnitter.getLevel()),
                worldBounds.getRight()-PADDING,
                worldBounds.getTop()-PADDING,
                Align.right);
    }
}
