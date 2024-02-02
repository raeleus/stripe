package com.ray3k.stripe.filechooser;

import com.badlogic.gdx.files.FileHandle;

import java.util.List;

/**
 * The FileCallback interface defines methods to handle file selection events.
 */
public interface FileCallback {
    /**
     * This method is called when a file is chosen. It takes a single parameter of type FileHandle.
     * It internally calls the onFileChosen method with a List of FileHandle, containing only the provided fileHandle.
     *
     * @param fileHandle the chosen file
     */
    default void onFileChosen(FileHandle fileHandle) {
        onFileChosen(List.of(fileHandle));
    }

    /**
     * This method is called when a file is chosen.
     *
     * @param fileHandleList the list of file handles representing the chosen files
     */
    void onFileChosen(List<FileHandle> fileHandleList);

    /**
     * Cancels the file selection process.
     */
    void onCancel();

    /**
     * This method is called when an error occurs during the file selection process.
     *
     * @param e the exception that occurred
     */
    void onError(Exception e);
}
