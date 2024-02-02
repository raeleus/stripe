package com.ray3k.stripe.filechooser;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public final class FileChooserListener extends ChangeListener {
    private final FileChooser fileChooser;
    private final FileCallback callback;
    private final FileChooserConfig config;

    public FileChooserListener(FileChooser fileChooser, FileChooserConfig config, FileCallback callback) {
        this.fileChooser = fileChooser;
        this.config = config;
        this.callback = callback;
    }

    @Override
    public final void changed(ChangeEvent event, Actor actor) {
        fileChooser.chooseFile(callback, config);
    }
}
