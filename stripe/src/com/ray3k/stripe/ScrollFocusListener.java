package com.ray3k.stripe;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Add ScrollFocusListener to any scrollable widget that you want to set as the Stage's scrollFocus once the user mouses
 * over the widget.
 */
public class ScrollFocusListener extends InputListener {
    private Stage stage;
    
    public ScrollFocusListener(Stage stage) {
        this.stage = stage;
    }
    
    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        stage.setScrollFocus(event.getListenerActor());
    }
}