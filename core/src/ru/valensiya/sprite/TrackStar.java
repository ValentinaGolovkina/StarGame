package ru.valensiya.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

public class TrackStar extends Star{

    private  final Vector2 trackingV;
    private final Vector2 sumV;

    public TrackStar(TextureAtlas atlas,Vector2 trackingV) {
        super(atlas);
        this.trackingV=trackingV;
        sumV=new Vector2();
    }

    public void update(float delta) {
        sumV.setZero().mulAdd(trackingV,0.2f).rotate(180).add(speed);
        pos.mulAdd(sumV,delta);
        checkBounds();
    }
}
