package com.ray3k.stripe.filechooser;

/**
 * DummyFileChooser is a class that implements the FileChooser interface.
 * It provides a dummy implementation of the chooseFile method that always throws an exception indicating that file chooser is not supported.
 */
public final class DummyFileChooser implements FileChooser {
    @Override
    public void chooseFile(FileCallback callback, FileChooserConfig config) {
        callback.onError(new UnsupportedOperationException("File chooser is not supported"));
    }
}
