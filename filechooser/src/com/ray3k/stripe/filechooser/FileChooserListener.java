package com.ray3k.stripe.filechooser;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * FileChooserListener is a ChangeListener implementation that allows the user to choose a file based on the provided
 * configuration using a file chooser dialog. It invokes the chooseFile method of the provided fileChooser with the
 * provided callback and configuration options when the change event occurs.
 */
public final class FileChooserListener extends ChangeListener {
    private final FileChooser fileChooser;
    private final FileCallback callback;
    private final FileChooserConfig config;

    /**
     * Initializes a new instance of the FileChooserListener class.
     *
     * @param fileChooser the file chooser instance used to choose a file
     * @param config      the configuration options for the file chooser dialog
     * @param callback    the callback interface that handles the file selection events
     */
    public FileChooserListener(FileChooser fileChooser, FileChooserConfig config, FileCallback callback) {
        this.fileChooser = fileChooser;
        this.config = config;
        this.callback = callback;
    }


    @Override
    public void changed(ChangeEvent event, Actor actor) {
        fileChooser.chooseFile(callback, config);
    }
}
