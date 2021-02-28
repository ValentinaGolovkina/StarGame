package ru.valensiya.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.valensiya.base.Sprite;
import ru.valensiya.math.Rect;

public class GameOver extends Sprite {

    private  static final float HEIGHT =0.08f;
    private static final float TOP = 0.15f;

    public GameOver(TextureAtlas atlas){
        super(atlas.findRegion("message_game_over"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        setTop(TOP);
    }
}
