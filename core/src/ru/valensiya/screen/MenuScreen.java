package ru.valensiya.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.valensiya.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    private Texture img;
    private Vector2 touch;
    private Vector2 location;
    private Vector2 speed;
    private Vector2 trend;

    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        touch = new Vector2(0,Gdx.graphics.getHeight());
        location = new Vector2(0,0);
        speed = new Vector2(0,0);
    }

    @Override
    public void render(float delta)  {
        super.render(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, location.x, location.y);
        batch.end();
        if(location.cpy().sub(touch).len()>1)
            location.add(speed);
    }

    @Override
    public void dispose() {
        img.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX,Gdx.graphics.getHeight()-screenY);
        trend = touch.cpy().sub(location).nor();
        speed.set(trend);
        return false;
    }
}
