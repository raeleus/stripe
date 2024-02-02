package com.ray3k.stripe.filechooser;

import com.badlogic.gdx.files.FileHandle;

import java.util.List;

public interface FileCallback {

    default void onFileChosen(FileHandle fileHandle) {
        onFileChosen(List.of(fileHandle));
    }

    void onFileChosen(List<FileHandle> fileHandleList);

    void onCancel();

    void onError(Exception e);
}
