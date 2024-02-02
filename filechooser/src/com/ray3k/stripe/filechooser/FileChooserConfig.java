package com.ray3k.stripe.filechooser;

import com.badlogic.gdx.files.FileHandle;

import java.util.List;

/**
 * FileChooserConfig represents the configuration options for a file chooser dialog. It contains properties such as the
 * type of dialog, the file name filter, the default directory, the default file name, and the title of the dialog.
 */
public final class FileChooserConfig {
    public FileDialogType dialogType = FileDialogType.Open;
    public List<String> fileNameFilter;
    public FileHandle defaultDir;
    public String defaultName;
    public String title;
}
