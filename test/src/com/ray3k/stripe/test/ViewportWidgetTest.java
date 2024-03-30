package com.ray3k.stripe.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ray3k.stripe.ResizeWidget;
import com.ray3k.stripe.ViewportWidget;

public class ViewportWidgetTest extends ApplicationAdapter {
    private Skin skin;
    private Stage stage;
    private Viewport gameViewport;
    private SpriteBatch spriteBatch;
    private ViewportWidget viewportWidget;
    
    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("resize-widget/skin.json"));
        spriteBatch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    
        gameViewport = new StretchViewport(800, 800);
        viewportWidget = new ViewportWidget(gameViewport);
        
        ResizeWidget resizeWidget = new ResizeWidget(viewportWidget, skin, "default");
        resizeWidget.setResizingFromCenter(true);
        resizeWidget.setMinWidth(100);
        resizeWidget.setMinHeight(100);
        resizeWidget.setFillParent(true);
        stage.addActor(resizeWidget);
    }
    
    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.act();
        viewportWidget.updateViewport(true);
        spriteBatch.setProjectionMatrix(gameViewport.getCamera().combined);
        TextureRegion textureRegion = skin.getRegion("island");
        spriteBatch.begin();
        spriteBatch.draw(textureRegion, 0, 0);
        spriteBatch.end();
        
        stage.getViewport().apply();
        stage.draw();
    }
    
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    
    @Override
    public void dispose() {
        skin.dispose();
    }
    
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(800, 800);
        new Lwjgl3Application(new ViewportWidgetTest(), config);
    }
}
