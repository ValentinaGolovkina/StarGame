package ru.valensiya.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.valensiya.base.BaseButton;
import ru.valensiya.math.Rect;
import ru.valensiya.screen.GameScreen;

public class NewGame extends BaseButton {

    private static final float HEIGHT = 0.05f;
    private GameScreen gameScreen;

    public NewGame(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        this.gameScreen=gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        setBottom(-0.1f);
    }

    @Override
    public void action() {
        gameScreen.startNewGame();
    }
}
