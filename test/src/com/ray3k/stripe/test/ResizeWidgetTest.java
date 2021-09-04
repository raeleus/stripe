package com.ray3k.stripe.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ray3k.stripe.ResizeWidget;
import com.ray3k.stripe.ViewportWidget;

public class ResizeWidgetTest extends ApplicationAdapter {
    private Skin skin;
    private Stage stage;
    
    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("resize-widget/skin.json"));
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    
        Table table = new Table();
        table.setBackground(skin.getDrawable("rect"));
        table.setFillParent(true);
        stage.addActor(table);
        
        Image image = new Image(skin.getDrawable("island"));
        ResizeWidget resizeWidget = new ResizeWidget(image, skin, "default");
        resizeWidget.setResizingFromCenter(true);
        resizeWidget.setMinWidth(100);
        resizeWidget.setMinHeight(100);
        table.add(resizeWidget).grow();
    }
    
    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.act();
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
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.forceExit = false;
        config.width = 800;
        config.height = 800;
        new LwjglApplication(new ResizeWidgetTest(), config);
    }
}
