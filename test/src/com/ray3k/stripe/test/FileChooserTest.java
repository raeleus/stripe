package com.ray3k.stripe.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ray3k.stripe.filechooser.*;

import java.util.List;

public class FileChooserTest extends ApplicationAdapter {
    private Skin skin;
    private Stage stage;

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(800, 800);
        new Lwjgl3Application(new FileChooserTest(), config);
    }

    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("metal-ui.json"));
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        var showLabel = new Label("Path", skin);
        table.add(showLabel);
        table.row();
        FileChooserConfig config;
        var callback = new FileCallback() {
            @Override
            public void onFileChosen(List<FileHandle> fileHandleList) {
                var s = "";
                for (FileHandle fileHandle : fileHandleList) {
                    s += fileHandle.path() + "\n";
                }
                showLabel.setText(s);

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(Exception e) {
                showLabel.setText(e.getMessage());
            }
        };
        var textButton = new TextButton("Open File", skin);
        config = new FileChooserConfig();
        config.dialogType = FileDialogType.Open;
        textButton.addListener(new FileChooserListener(new DesktopFileChooser(), config, callback));
        table.add(textButton);


        textButton = new TextButton("Open Multiple Files", skin);
        config = new FileChooserConfig();
        config.dialogType = FileDialogType.OpenMulti;
        textButton.addListener(new FileChooserListener(new DesktopFileChooser(), config, callback));
        table.add(textButton);


        textButton = new TextButton("Save File", skin);
        config = new FileChooserConfig();
        config.dialogType = FileDialogType.Save;
        textButton.addListener(new FileChooserListener(new DesktopFileChooser(), config, callback));
        table.add(textButton);


        textButton = new TextButton("Open Folder", skin);
        config = new FileChooserConfig();
        config.dialogType = FileDialogType.Folder;
        textButton.addListener(new FileChooserListener(new DesktopFileChooser(), config, callback));
        table.add(textButton);


        //test creating a ResizeWidget with a null style
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        skin.dispose();
    }
}
