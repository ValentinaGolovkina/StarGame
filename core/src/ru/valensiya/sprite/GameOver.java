package ru.valensiya.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.valensiya.base.Sprite;
import ru.valensiya.math.Rnd;

public class GameOver extends Sprite {
    public GameOver(TextureAtlas atlas){
        super(atlas.findRegion("message_game_over"));
        setHeightProportion( 0.08f);
        pos.set(0,0);
    }
}
