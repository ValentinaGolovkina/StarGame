package ru.valensiya.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.valensiya.base.BaseScreen;
import ru.valensiya.math.Rect;
import ru.valensiya.sprite.Background;
import ru.valensiya.sprite.Spaceship;
import ru.valensiya.sprite.Star;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;

    private Texture bg;
    private TextureAtlas atlas;

    private Background background;
    private Star[] stars;
    private Spaceship spaceship;

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
        spaceship = new Spaceship(atlas.findRegion("main_ship"));
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for(Star star:stars)
            star.resize(worldBounds);
        spaceship.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        spaceship.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        spaceship.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        spaceship.touchDown(touch,pointer,button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        spaceship.touchUp(touch,pointer,button);
        return false;
    }

    public void update(float delta){
        for(Star star:stars)
            star.update(delta);
        spaceship.update(delta);
    }
    public void draw(){
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for(Star star:stars)
            star.draw(batch);
        spaceship.draw(batch);
        batch.end();
    }
}
