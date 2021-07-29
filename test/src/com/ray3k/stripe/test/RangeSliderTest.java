/*******************************************************************************
 * MIT License
 *
 * Copyright (c) 2021 Raymond Buckley
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package com.ray3k.stripe.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ray3k.stripe.RangeSlider;
import com.ray3k.stripe.RangeSlider.RangeSliderStyle;
import com.ray3k.stripe.scenecomposer.SceneComposerStageBuilder;

/**
 * A basic example that demonstrates the use of a RangeSlider.
 */
public class RangeSliderTest extends ApplicationAdapter {
    private Stage stage;
    private Skin skin;
    
    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("range-slider/range-slider.json"));
    
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
    
        RangeSliderStyle style = new RangeSliderStyle();
        style.background = skin.getDrawable("range-slider-bg-10");
        style.backgroundDisabled = skin.getDrawable("range-slider-bg-disabled-10");
        style.knobBeginUp = skin.getDrawable("range-slider-knob-semi");
        style.knobBeginOver = skin.getDrawable("range-slider-knob-over");
        style.knobBeginDisabled = skin.getDrawable("range-slider-knob-disabled");
        style.progressKnob = skin.getDrawable("range-slider-progress");
        style.progressKnobDisabled = skin.getDrawable("range-slider-progress-disabled");
        style.knobEndUp = skin.getDrawable("range-slider-knob-semi");
        style.knobEndOver = skin.getDrawable("range-slider-knob-over");
        style.knobEndDisabled = skin.getDrawable("range-slider-knob-disabled");
    
        final RangeSlider rangeSlider = new RangeSlider(style);
        rangeSlider.setMinimum(0);
        rangeSlider.setMaximum(100);
        rangeSlider.setIncrement(1);
        rangeSlider.setValueEnd(100);
        root.add(rangeSlider).growX().pad(20);
    
        root.row();
        Table table = new Table();
        root.add(table).growX();
        
        table.defaults().expand();
        final Label minimumLabel = new Label("", skin);
        table.add(minimumLabel);
        
        final Label maximumLabel = new Label("", skin);
        table.add(maximumLabel);
        rangeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                minimumLabel.setText(Math.round(rangeSlider.getValueBegin()));
                maximumLabel.setText(Math.round(rangeSlider.getValueEnd()));
            }
        });
    }
    
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    
    @Override
    public void render() {
        Gdx.gl.glClearColor(.5f, .5f, .5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.act();
        stage.draw();
    }
    
    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
    
    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.forceExit = false;
        config.width = 850;
        config.height = 450;
        new LwjglApplication(new RangeSliderTest(), config);
    }
}