package ru.valensiya.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.valensiya.base.Sprite;
import ru.valensiya.math.Rect;
import ru.valensiya.pool.BulletPool;

public class MainShip extends Sprite {

    private static final float HEIGHT = 0.15f;
    private static final float PADDING = 0.02f;
    private static final int INVALID_POINTER =-1;

    private float reloadInterval;
    private float reloadTimer;

    private boolean pressedLeft;
    private boolean pressedRight;
    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;
    private final Vector2 v0 = new Vector2(0.5f,0);
    private final Vector2 v = new Vector2();
    private Rect worldBounds;
    private BulletPool bulletPool;
    private TextureRegion bulletRegion;
    private Vector2 bulletV;
    private Vector2 bulletPos;
    private Sound soundShot;

    public MainShip(TextureAtlas atlas, BulletPool bulletPool, Sound soundShot) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletPool = bulletPool;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.soundShot = soundShot;
        bulletV = new Vector2(0, 0.5f);
        bulletPos = new Vector2();
        reloadInterval = 0.15f;
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
        pos.mulAdd(v,delta);
        reloadTimer+=delta;
        if(reloadTimer>=reloadInterval){
            reloadTimer=0;
            shoot();
        }
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
            case Input.Keys.UP:
                shoot();
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

    private void moveRight(){
        v.set(v0);
    }
    private void moveLeft(){
        v.set(v0).rotate(180);
    }
    private void stop(){
        v.setZero();
    }
    private void shoot(){
        Bullet bullet = bulletPool.obtain();
        bulletPos.set(pos.x, getTop());
        bullet.set(this, bulletRegion,bulletPos,bulletV,0.01f,worldBounds,1);
        soundShot.play(1.0f);
    }
}
