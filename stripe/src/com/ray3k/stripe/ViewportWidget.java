package com.ray3k.stripe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * A widget that allows you to embed an entire viewport within your UI. This is an excellent tool to integrate your
 * games into a UI instead of just overlaying the UI on top of it. Make sure to render your game assets after calling
 * stage.act() in the render method to prevent position lag.
 */
public class ViewportWidget extends Widget {
    public Viewport viewport;
    private static Vector2 temp = new Vector2();
    
    public ViewportWidget(Viewport viewport) {
        this.viewport = viewport;
    }
    
    @Override
    public void act(float delta) {
        temp.set(0, 0);
        localToScreenCoordinates(temp);
        viewport.setScreenPosition(MathUtils.round(temp.x), MathUtils.round(Gdx.graphics.getHeight() - temp.y));
    }
    
    @Override
    public void layout() {
        temp.set(0, 0);
        localToScreenCoordinates(temp);
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                viewport.update(MathUtils.round(getWidth()), MathUtils.round(getHeight()));
                viewport.setScreenPosition(MathUtils.round(temp.x), MathUtils.round(Gdx.graphics.getHeight() - temp.y));
            }
        });
        viewport.setScreenBounds(MathUtils.round(temp.x), MathUtils.round(Gdx.graphics.getHeight() - temp.y), MathUtils.round(getWidth()), MathUtils.round(getHeight()));
    }
}