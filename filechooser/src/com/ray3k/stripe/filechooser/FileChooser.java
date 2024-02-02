package com.ray3k.stripe.filechooser;

/**
 * FileChooser is an interface that provides a method to choose a file with the given configuration and callback.
 */
public interface FileChooser {
    /**
     * Allows the user to choose a file based on the provided configuration and callback.
     *
     * @param callback the callback interface that handles the file selection events.
     * @param config   the configuration options for the file chooser dialog.
     */
    void chooseFile(FileCallback callback, FileChooserConfig config);
}
