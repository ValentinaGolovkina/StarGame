package ru.valensiya.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.valensiya.base.BaseScreen;
import ru.valensiya.math.Rect;
import ru.valensiya.sprite.Background;
import ru.valensiya.sprite.Logo;

public class MenuScreen extends BaseScreen {

    private Texture bg;
    private Background background;
    private Texture lg;
    private Logo logo;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/background_cosmos.jpg");
        background = new Background(bg);
        lg = new Texture("textures/badlogic.jpg");
        logo = new Logo(lg);
    }

    @Override
    public void render(float delta)  {
        super.render(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        logo.draw(batch);
        batch.end();
        logo.update(delta);
    }

    @Override
    public void dispose() {
        bg.dispose();
        lg.dispose();
        super.dispose();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        logo.touchDown(touch,pointer,button);
        return false;
    }
}
