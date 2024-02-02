package com.ray3k.stripe.filechooser;

public final class DummyFileChooser implements FileChooser {
    @Override
    public void chooseFile(FileCallback callback, FileChooserConfig config) {
        callback.onError(new UnsupportedOperationException("File chooser is not supported"));
    }
}
