package ru.valensiya.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.valensiya.base.Ship;
import ru.valensiya.math.Rect;
import ru.valensiya.pool.BulletPool;
import ru.valensiya.pool.ExplosionPool;

public class MainShip extends Ship {

    private static final float HEIGHT = 0.15f;
    private static final float PADDING = 0.02f;
    private static final int INVALID_POINTER =-1;

    private static final int HP =100;

    private boolean pressedLeft;
    private boolean pressedRight;
    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;


    public MainShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletPool = bulletPool;
        this.explosionPool=explosionPool;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        v = new Vector2();
        v0 = new Vector2(0.5f,0);
        bulletV = new Vector2(0, 0.5f);
        bulletPos = new Vector2();
        bulletHeight = 0.01f;
        damage=1;
        hp=HP;
        reloadInterval = 0.2f;
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
    }

    public void startNewGame(){
        hp=HP;
        pressedLeft=false;
        pressedRight =false;
        leftPointer=INVALID_POINTER;
        rightPointer=INVALID_POINTER;
        stop();
        pos.x=worldBounds.pos.x;
        flushDestroy();
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom() + PADDING);
        this.worldBounds = worldBounds;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if(touch.x<worldBounds.pos.x) {
            if(leftPointer!=INVALID_POINTER){
                return false;
            }
            leftPointer = pointer;
            moveLeft();
        } else {
            if(rightPointer!=INVALID_POINTER){
                return false;
            }
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if(pointer == leftPointer) {
            leftPointer =INVALID_POINTER;
            if(rightPointer!=INVALID_POINTER){
                moveRight();
            } else{
                stop();
            }
        } else if (pointer == rightPointer){
            rightPointer=INVALID_POINTER;
            if(leftPointer!=INVALID_POINTER){
                moveLeft();
            } else {
                stop();
            }
        }
        return false;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        bulletPos.set(pos.x, getTop());
        if(getRight()>worldBounds.getRight()){
            setRight(worldBounds.getRight());
            stop();
        }
        if(getLeft()<worldBounds.getLeft()){
            setLeft(worldBounds.getLeft());
            stop();
        }
    }

    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.A:
            case Input.Keys.LEFT:
                moveLeft();
                pressedLeft=true;
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                moveRight();
                pressedRight=true;
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft=false;
                if(pressedRight){
                    moveRight();
                } else {
                    stop();
                }
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight=false;
                if(pressedLeft){
                    moveLeft();
                } else {
                    stop();
                }
                break;
        }
        return false;
    }
    public void dispose(){
        sound.dispose();
    }

    public boolean isBulletColision(Rect bullet){
        return !(bullet.getRight()<getLeft()
                || bullet.getLeft()>getRight()
                || bullet.getBottom()>pos.y
                || bullet.getTop()<getBottom());
    }

    private void moveRight(){
        v.set(v0);
    }
    private void moveLeft(){
        v.set(v0).rotate(180);
    }
    private void stop(){
        v.setZero();
    }
}
