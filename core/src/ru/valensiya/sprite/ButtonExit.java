package ru.valensiya.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.valensiya.base.BaseButton;
import ru.valensiya.math.Rect;

public class ButtonExit extends BaseButton {

    private static final float HEIGHT = 0.21f;
    private static final float PADDING = 0.03f;

    public ButtonExit(TextureAtlas atlas){
        super(atlas.findRegion("btExit"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom()+PADDING);
        setRight(worldBounds.getRight()-PADDING);
    }

    @Override
    public void action() {
        Gdx.app.exit();
    }
}
