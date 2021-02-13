package ru.valensiya.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.valensiya.base.BaseButton;
import ru.valensiya.math.Rect;
import ru.valensiya.screen.GameScreen;

public class ButtonPlay extends BaseButton {

    private static final float HEIGHT = 0.2f;
    private static final float PADDING = 0.03f;

    private final Game game;

    public ButtonPlay(TextureAtlas atlas, Game game){
        super(atlas.findRegion("btPlay"));
        this.game = game;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom()+PADDING);
        setLeft(worldBounds.getLeft()+PADDING);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen());
    }
}
