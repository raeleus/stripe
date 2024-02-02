package com.ray3k.stripe.filechooser;

import com.badlogic.gdx.files.FileHandle;

import java.util.List;

public final class FileChooserConfig {
    public FileDialogType dialogType = FileDialogType.Open;
    public List<String> fileNameFilter;
    public FileHandle defaultDir;
    public String defaultName;
    public String title;
}
